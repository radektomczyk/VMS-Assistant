package com.example.vmsv3.ui.historiakosztow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vmsv3.databinding.FragmentHistoriakosztowBinding;

public class HistoriakosztowFragment extends Fragment {

    private FragmentHistoriakosztowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        HistoriakosztowViewModel historiakosztowViewModel =
                new ViewModelProvider(this).get(HistoriakosztowViewModel.class);
        binding = FragmentHistoriakosztowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textHistoriakosztow;
        historiakosztowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return  root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}