package com.example.vmsv3.ui.add_ticket;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.vmsv3.R;
import com.example.vmsv3.databinding.FragmentAddTicketBinding;

import java.util.Calendar;

public class AddTicketFragment extends Fragment {
    private FragmentAddTicketBinding binding;
    private EditText ticketDate;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTicketBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // init calendar view
        ticketDate = root.findViewById(R.id.ticketDateEditText);
        ticketDate.setOnClickListener(v -> showDatePicker());
        Button saveButton = root.findViewById(R.id.saveTicketButton);
        saveButton.setOnClickListener(this::dupaBiskupa);

        return root;
    }

    private void dupaBiskupa(View view) {
        String authToken = "dupabiskupa";
//        MandatDto mandatDto = new MandatDto(" ", 2, 12);
//        SamochodioClient.getInstance().createTicket(mandatDto, authToken);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showDatePicker() {
        // Pobierz aktualną datę
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Utwórz DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ticketDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        // Wyświetl DatePickerDialog
        datePickerDialog.show();
    }
}