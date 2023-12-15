package com.example.vmsv3.ui.refuel;

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
import com.example.vmsv3.databinding.FragmentAddRefuelBinding;
import com.example.vmsv3.transport.RefuelDto;
import com.example.vmsv3.transport.VehicleDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRefuelFragment extends Fragment {
    private FragmentAddRefuelBinding binding;
    private ApiService apiService;
    private List<VehicleDto> vehicleList;
    private Spinner vehiclesSpinner;
    private EditText fuelAmountEditText;
    private EditText priceEditText;
    private Spinner fuelTypeSpinner;
    private EditText totalCostEditText;
    private EditText refuelDateEditText;
    private Button button;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddRefuelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        apiService = ApiClient.getApiClient().create(ApiService.class);

        vehiclesSpinner = root.findViewById(R.id.vehiclesSpinner);
        fuelAmountEditText = root.findViewById(R.id.fuelAmountEditText);
        priceEditText = root.findViewById(R.id.priceEditText);
        fuelTypeSpinner = root.findViewById(R.id.fuelTypeSpinner);
        String[] fuelTypeArray = getResources().getStringArray(R.array.fuelTypeArray);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                fuelTypeArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelTypeSpinner.setAdapter(spinnerAdapter);
        totalCostEditText = root.findViewById(R.id.totalCostEditText);
        refuelDateEditText = root.findViewById(R.id.refuelDateEditText);
        refuelDateEditText.setOnClickListener(v -> showDatePicker());
        button = root.findViewById(R.id.saveRefuelButton);

        button.setOnClickListener(this::createRefuel);

        fetchVehicleList();

        return root;
    }

    private void fetchVehicleList() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null && !accessToken.isEmpty()) {
            apiService = ApiClient.getApiClient().create(ApiService.class);
            String authorizationHeader = "Bearer " + accessToken;

            Call<List<VehicleDto>> call = apiService.getVehicleList(authorizationHeader);

            Log.d("AddRefuelFragment", "Request URL: " + call.request().url());
            Log.d("AddRefuelFragment", "Request Headers: " + call.request().headers());

            call.enqueue(new Callback<List<VehicleDto>>() {
                @Override
                public void onResponse(@NonNull Call<List<VehicleDto>> call, @NonNull Response<List<VehicleDto>> response) {
                    if (response.isSuccessful()) {
                        vehicleList = response.body();
                        if (vehicleList != null && !vehicleList.isEmpty()) {
                            setupVehicleSpinner();
                            Log.d("AddRefuelFragment", "Vehicle list downloaded successfully");
                        } else {
                            Toast.makeText(getContext(), "No vehicles available", Toast.LENGTH_SHORT).show();
                            Log.d("AddRefuelFragment", "Empty vehicle list received");
                        }
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch vehicle list. Server returned " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d("AddRefuelFragment", "Failed to fetch vehicle list. Server returned " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<VehicleDto>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "Failed to fetch vehicle list", Toast.LENGTH_SHORT).show();
                    Log.e("AddRefuelFragment", "Failed to fetch vehicle list", t);
                }
            });
        } else {
            Toast.makeText(getContext(), "Access token is not available", Toast.LENGTH_SHORT).show();
            Log.d("AddRefuelFragment", "Access token is not available");
        }
    }

    private void setupVehicleSpinner() {
        List<String> vehicleNames = new ArrayList<>();
        for (VehicleDto vehicle : vehicleList) {
            String displayName = vehicle.getCarBrand() + " " + vehicle.getCarModel();
            vehicleNames.add(displayName);
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                vehicleNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehiclesSpinner.setAdapter(spinnerAdapter);
    }

    private void createRefuel(View view) {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null && !accessToken.isEmpty()) {
            apiService = ApiClient.getApiClient().create(ApiService.class);

            if (validateFields()) {
                RefuelDto refuel = new RefuelDto();

                String fuelAmountString = fuelAmountEditText.getText().toString();
                if (!fuelAmountString.isEmpty()) {
                    try {
                        double fuelAmount = Double.parseDouble(fuelAmountString);
                        refuel.setFuelAmount(fuelAmount);
                    } catch (NumberFormatException e) {
                        Log.e("AddRefuelFragment", "Invalid fuel amount format", e);
                        Toast.makeText(getContext(), "Invalid fuel amount format", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Log.d("AddRefuelFragment", "Fuel amount is empty");
                    Toast.makeText(getContext(), "Fuel amount cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                refuel.setPricePerLiter((int) (Double.parseDouble(priceEditText.getText().toString()) * 100));
                refuel.setFuelType(String.valueOf(fuelTypeSpinner.getSelectedItem()));
                refuel.setTotalPrice((int) (Double.parseDouble(totalCostEditText.getText().toString()) * 100));
                refuel.setRefuelDate(parseDateEditText(refuelDateEditText));
                refuel.setBlockade(0);

                Log.d("AddRefuelFragment", "Refuel values: " +
                        "FuelAmount = " + refuel.getFuelAmount() +
                        ", PricePerLiter = " + refuel.getPricePerLiter() +
                        ", FuelType = " + refuel.getFuelType() +
                        ", TotalPrice = " + refuel.getTotalPrice() +
                        ", RefuelDate = " + refuel.getRefuelDate());

                String authorizationHeader = "Bearer " + accessToken;

                long selectedVehicleId = parseCurrentVehicleSpinnerSelectionToValue();
                apiService.createRefuel(authorizationHeader, selectedVehicleId, refuel).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Refuel created successfully", Toast.LENGTH_SHORT).show();
                            emptyFields();
                            NavController navController = Navigation.findNavController(requireView());
                            navController.navigateUp();
                        } else {
                            Toast.makeText(getContext(), "Failed to create refuel. Server returned " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Failed to create refuel", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Access token is not available", Toast.LENGTH_SHORT).show();
        }
    }


    private long parseCurrentVehicleSpinnerSelectionToValue() {
        int selectedPosition = this.vehiclesSpinner.getSelectedItemPosition();
        if (selectedPosition >= 0 && selectedPosition < vehicleList.size()) {
            return vehicleList.get(selectedPosition).getVehicleId();
        } else {
            return 0;
        }
    }

    private String parseDateEditText(EditText editText) {
        String dateString = editText.getText().toString();
        Log.d("AddRefuelFragment", "Input date string: " + dateString);

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = inputDateFormat.parse(dateString);

            if (isValidDate(date)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                date = calendar.getTime();

                Log.d("AddRefuelFragment", "Parsed date to string: " + date);
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

    public void emptyFields() {
        vehiclesSpinner.setSelection(0);
        fuelAmountEditText.setText(null);
        priceEditText.setText(null);
        fuelTypeSpinner.setSelection(0);
        totalCostEditText.setText(null);
        refuelDateEditText.setText(null);
    }

    private boolean validateFields() {
        return isValidSpinnerSelection(vehiclesSpinner) &&
                !fuelAmountEditText.getText().toString().isEmpty() &&
                !priceEditText.getText().toString().isEmpty() &&
                isValidSpinnerSelection(fuelTypeSpinner) &&
                !totalCostEditText.getText().toString().isEmpty() &&
                !refuelDateEditText.getText().toString().isEmpty();
    }

    private boolean isValidSpinnerSelection(Spinner spinner) {
        return spinner.getSelectedItem() != null && !spinner.getSelectedItem().toString().isEmpty();
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) -> {
            refuelDateEditText.setText(String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month1 + 1, year1));
        }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
