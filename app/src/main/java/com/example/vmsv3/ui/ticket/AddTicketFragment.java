package com.example.vmsv3.ui.ticket;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.databinding.FragmentAddTicketBinding;
import com.example.vmsv3.transport.TicketDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTicketFragment extends Fragment {
    private FragmentAddTicketBinding binding;
    private ApiService apiService;
    private EditText reasonEditText;
    private EditText penaltyPointsEditText;
    private Spinner ticketDurationSpinner;
    private EditText dateEditText;
    private EditText costEditText;
    private Button button;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // retrofit init
        apiService = ApiClient.getApiClient().create(ApiService.class);

        reasonEditText = root.findViewById(R.id.ticketNameEditText);
        penaltyPointsEditText = root.findViewById(R.id.penaltyPointsEditText);
        ticketDurationSpinner = root.findViewById(R.id.ticketDurationSpinner);
        String[] ticketDurationArray = getResources().getStringArray(R.array.ticketDurationArray);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ticketDurationArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketDurationSpinner.setAdapter(spinnerAdapter);        dateEditText = root.findViewById(R.id.ticketDateEditText);
        costEditText = root.findViewById(R.id.ticketAmountEditText);
        button = root.findViewById(R.id.saveTicketButton);

        // init calendar
        dateEditText.setOnClickListener(v -> showDatePicker());


        button.setOnClickListener(this::createTicket);


        return root;
    }

    private void createTicket(View view) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null && !accessToken.isEmpty()) {
            apiService = ApiClient.getApiClient().create(ApiService.class);

            if (validateFields()) {
                TicketDto ticket = new TicketDto();
                ticket.setReason(reasonEditText.getText().toString());
                ticket.setPenaltyPoints(parseEditTextToInt(penaltyPointsEditText));
                ticket.setValidityMonths(parseSpinnerSelectionToValue(ticketDurationSpinner));
                ticket.setReceiveDate(parseDateEditText(dateEditText));
                ticket.setAmount((int) Double.parseDouble(costEditText.getText().toString()));

                String authorizationHeader = "Bearer " + accessToken;

                Call<Void> call = apiService.createTicket(authorizationHeader, ticket);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Ticket created successfully", Toast.LENGTH_SHORT).show();
                            emptyFields();
                            NavController navController = Navigation.findNavController(requireView());
                            navController.navigateUp();
                        } else {
                            Toast.makeText(getContext(), "Failed to create ticket. Server returned " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to create ticket", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Access token is not available", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateFields() {
        return !reasonEditText.getText().toString().isEmpty() &&
                !penaltyPointsEditText.getText().toString().isEmpty() &&
                isValidSpinnerSelection(ticketDurationSpinner) &&
                !dateEditText.getText().toString().isEmpty() &&
                !costEditText.getText().toString().isEmpty();
    }


    private String parseDateEditText(EditText editText) {
        String dateString = editText.getText().toString();
        Log.d("Ticket", "Input date string: " + dateString);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = inputDateFormat.parse(dateString);

            if (date != null && isValidDate(date)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date = calendar.getTime();

                Log.d("Ticket", "Parsed date to string: " + date);
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

    private boolean isValidSpinnerSelection(Spinner spinner) {
        return spinner.getSelectedItem() != null && !spinner.getSelectedItem().toString().isEmpty();
    }

    private int parseSpinnerSelectionToValue(Spinner spinner) {
        String selectedOption = spinner.getSelectedItem().toString();

        switch (selectedOption) {
            case "12 months":
                return 12;
            case "24 months":
                return 24;
            default:
                return 0;
        }
    }

    private boolean isValidDate(Date inputDate) {
        Date currentDate = new Date();
        return inputDate != null && !inputDate.after(currentDate);
    }

    private int parseEditTextToInt(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid number format", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            dateEditText.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month1 + 1, year1));
        }, year, month, day);

        datePickerDialog.show();
    }

    public void emptyFields(){
        reasonEditText.setText(null);
        penaltyPointsEditText.setText(null);
        ticketDurationSpinner.setSelection(0);
        dateEditText.setText(null);
        costEditText.setText(null);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
