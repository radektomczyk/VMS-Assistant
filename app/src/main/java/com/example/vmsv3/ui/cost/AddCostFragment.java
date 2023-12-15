package com.example.vmsv3.ui.cost;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.databinding.FragmentAddCostBinding;
import com.example.vmsv3.transport.CostDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCostFragment extends Fragment {
    private FragmentAddCostBinding binding;
    private ApiService apiService;
    private EditText costNameEditText;
    private EditText costDescriptionEditText;
    private EditText costAmountEditText;
    private EditText costDateEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddCostBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        apiService = ApiClient.getApiClient().create(ApiService.class);
        costNameEditText = root.findViewById(R.id.costNameEditText);
        costDescriptionEditText = root.findViewById(R.id.costDescriptionEditText);
        costAmountEditText = root.findViewById(R.id.costAmountEditText);
        costDateEditText = root.findViewById(R.id.costDateEditText);
        Button button = root.findViewById(R.id.saveCostButton);
        costDateEditText.setOnClickListener(v -> showDatePicker());
        button.setOnClickListener(this::createCost);
        return root;
    }

    private void createCost(View view) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null && !accessToken.isEmpty()) {
            apiService = ApiClient.getApiClient().create(ApiService.class);
            if (validateFields()) {
                CostDto cost = new CostDto();
                cost.setCostName(costNameEditText.getText().toString());
                cost.setCostDescription(costDescriptionEditText.getText().toString());

                double doubleAmount = parseEditTextToDouble(costAmountEditText);
                int intAmount = (int) (doubleAmount * 100);
                cost.setCostAmount(intAmount);

                String parsedDate = parseDateEditText(costDateEditText);

                if (parsedDate != null) {
                    cost.setCostDate(parsedDate);

                    String authorizationHeader = "Bearer " + accessToken;

                    Call<Void> call = apiService.createCost(authorizationHeader, cost);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Cost created successfully", Toast.LENGTH_SHORT).show();
                                emptyFields();
                                NavController navController = Navigation.findNavController(requireView());
                                navController.navigateUp();
                            } else {
                                Toast.makeText(getContext(), "Failed to create cost. Server returned " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            Toast.makeText(getContext(), "Failed to create cost", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Invalid date or date is further from today", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Access token is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private String parseDateEditText(EditText editText) {
        String dateString = editText.getText().toString();
        Log.d("AddCostFragment", "Input date string: " + dateString);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());

        try {
            Date date = inputDateFormat.parse(dateString);

            if (isValidDate(date)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);

                TimeZone timeZone = TimeZone.getDefault();
                calendar.setTimeZone(timeZone);

                date = calendar.getTime();

                Log.d("AddCostFragment", "Parsed date to string: " + date);
                return outputDateFormat.format(date);
            } else {
                Toast.makeText(getContext(), "Invalid date or date is further from today", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    private boolean isValidDate(Date inputDate) {
        Date currentDate = new Date();
        return inputDate != null && !inputDate.after(currentDate);
    }

    private double parseEditTextToDouble(EditText editText) {
        try {
            return Double.parseDouble(editText.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid number format", Toast.LENGTH_SHORT).show();
            return 0.0;
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            costDateEditText.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month1 + 1, year1));
        }, year, month, day);

        datePickerDialog.show();
    }

    private boolean validateFields() {
        return !costNameEditText.getText().toString().isEmpty() &&
                !costDescriptionEditText.getText().toString().isEmpty() &&
                !costAmountEditText.getText().toString().isEmpty() &&
                !costDateEditText.getText().toString().isEmpty();
    }

    public void emptyFields() {
        costNameEditText.setText(null);
        costDescriptionEditText.setText(null);
        costAmountEditText.setText(null);
        costDateEditText.setText(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}