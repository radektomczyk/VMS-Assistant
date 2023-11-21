package com.example.vmsv3.ui.refuel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vmsv3.R;
import com.example.vmsv3.databinding.FragmentAddRefuelBinding;

public class AddRefuelFragment extends Fragment {
    private FragmentAddRefuelBinding binding;
    private Spinner fuelTypeSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddRefuelBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // init fuel types dropdown list
        fuelTypeSpinner = root.findViewById(R.id.fuelTypeSpinner);
        String [] typyPaliwaArray = getResources().getStringArray(R.array.fuelTypeArray);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                typyPaliwaArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuelTypeSpinner.setAdapter(spinnerAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}