package com.example.vmsv3.ui.historiatankowania;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vmsv3.databinding.FragmentHistoriatankowaniaBinding;

public class HistoriatankowaniaFragment extends Fragment {
    private FragmentHistoriatankowaniaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        HistoriatankowaniaViewModel historiatankowaniaViewModel = new ViewModelProvider(this).get(HistoriatankowaniaViewModel.class);
        binding = FragmentHistoriatankowaniaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textHistoriatankowania;
//        historiatankowaniaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}