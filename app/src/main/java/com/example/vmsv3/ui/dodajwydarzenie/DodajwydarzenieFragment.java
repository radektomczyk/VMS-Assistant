package com.example.vmsv3.ui.dodajwydarzenie;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vmsv3.databinding.FragmentDodajwydarzenieBinding;

public class DodajwydarzenieFragment extends Fragment {
    private FragmentDodajwydarzenieBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DodajwydarzenieViewModel dodajwydarzenieViewModel =
                new ViewModelProvider(this).get(DodajwydarzenieViewModel.class);
        binding = FragmentDodajwydarzenieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textDodajwydarzenie;
        dodajwydarzenieViewModel.getText().observe(getViewLifecycleOwner(),textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}