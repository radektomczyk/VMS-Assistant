package com.example.vmsv3.ui.dodajtankowanie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vmsv3.R;
import com.example.vmsv3.databinding.FragmentDodajtankowanieBinding;

public class DodajtankowanieFragment extends Fragment {
    private FragmentDodajtankowanieBinding binding;
    private Spinner typyPaliwaSpinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DodajtankowanieViewModel dodajtankowanieViewModel = new ViewModelProvider(this).get(DodajtankowanieViewModel.class);
        binding = FragmentDodajtankowanieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // init fuel types dropdown list
        typyPaliwaSpinner = root.findViewById(R.id.typPaliwaSpinner);
        String [] typyPaliwaArray = getResources().getStringArray(R.array.typ_paliwa_array);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, typyPaliwaArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typyPaliwaSpinner.setAdapter(spinnerAdapter);

//        final TextView textView = binding.textDodajtankowanie;
//        dodajtankowanieViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}