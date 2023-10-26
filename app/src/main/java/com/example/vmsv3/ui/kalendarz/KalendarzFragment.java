package com.example.vmsv3.ui.kalendarz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vmsv3.databinding.FragmentKalendarzBinding;

public class KalendarzFragment extends Fragment {
    private FragmentKalendarzBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        KalendarzViewModel kalendarzViewModel = new ViewModelProvider(this).get(KalendarzViewModel.class);
        binding = FragmentKalendarzBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textKalendarz;
//        kalendarzViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}