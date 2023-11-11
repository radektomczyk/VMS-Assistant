package com.example.vmsv3.ui.add_ticket;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTicketFragment extends Fragment {
    private FragmentAddTicketBinding binding;
    private ApiService apiService;
    private EditText reasonEditText;
    private EditText penaltyPointsEditText;
    private EditText durationEditText;
    private EditText dateEditText;
    private EditText costEditText;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // retrofit init
        apiService = ApiClient.getApiClient().create(ApiService.class);

        reasonEditText = root.findViewById(R.id.ticketNameEditText);
        penaltyPointsEditText = root.findViewById(R.id.penaltyPointsEditText);
        durationEditText = root.findViewById(R.id.ticketDurationEditText);
        dateEditText = root.findViewById(R.id.ticketDateEditText);
        costEditText = root.findViewById(R.id.ticketAmountEditText);
        button = root.findViewById(R.id.saveTicketButton);

        // Initialize calendar view
        dateEditText.setOnClickListener(v -> showDatePicker());

        button.setOnClickListener(this::createTicket);

        return root;
    }

    private void createTicket(View view) {
        TicketDto ticket = new TicketDto();
        ticket.setReason(reasonEditText.getText().toString());
        ticket.setPenaltyPoints(parseEditTextToInt(penaltyPointsEditText));
        ticket.setValidityMonths(parseEditTextToInt(durationEditText));
        ticket.setReceiveDate(parseDateEditText().toString());
        ticket.setAmount((int) Double.parseDouble(costEditText.getText().toString()));

        // Make a Retrofit API call
        Call<Void> call = apiService.createTicket(ticket);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(getContext(), "Ticket created successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to create ticket", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Date parseDateEditText() {
        String dateString = dateEditText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Invalid date format", Toast.LENGTH_SHORT).show();
            return new Date();
        }
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
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (view, year1, month1, dayOfMonth) ->
                dateEditText.setText(String.format(Locale.getDefault(), "%d/%02d/%02d", year1, month1 + 1, dayOfMonth)), year, month, day);

        // Display DatePickerDialog
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
