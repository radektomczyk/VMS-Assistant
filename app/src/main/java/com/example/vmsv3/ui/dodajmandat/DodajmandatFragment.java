package com.example.vmsv3.ui.dodajmandat;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vmsv3.R;
import com.example.vmsv3.databinding.FragmentDodajmandatBinding;

import java.util.Calendar;

public class DodajmandatFragment extends Fragment {
    private FragmentDodajmandatBinding binding;
    private EditText mandatEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DodajmandatViewModel dodajmandatViewModel = new ViewModelProvider(this).get(DodajmandatViewModel.class);
        binding = FragmentDodajmandatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // init calendar view
        mandatEditText = root.findViewById(R.id.dataWystawieniaMandatuEditText);
        mandatEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
//        final TextView textView = binding.textDodajmandat;
//        dodajmandatViewModel.getText().observe(getViewLifecycleOwner(),textView::setText);
        return root;
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
                // Ustaw wybraną datę w polu tekstowym
                mandatEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        // Wyświetl DatePickerDialog
        datePickerDialog.show();
    }
}