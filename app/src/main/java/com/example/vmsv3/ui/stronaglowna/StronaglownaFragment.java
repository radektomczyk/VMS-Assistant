package com.example.vmsv3.ui.stronaglowna;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vmsv3.databinding.FragmentStronaglownaBinding;

public class StronaglownaFragment extends Fragment {

    private FragmentStronaglownaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        StronaglownaViewModel stronaglownaViewModel =
                new ViewModelProvider(this).get(StronaglownaViewModel.class);

        binding = FragmentStronaglownaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textStronaglowna;
        stronaglownaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}