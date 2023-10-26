package com.example.vmsv3.ui.dodajkoszt;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vmsv3.databinding.FragmentDodajkosztBinding;

public class DodajkosztFragment extends Fragment {
    private FragmentDodajkosztBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DodajkosztViewModel dodajkosztViewModel =
                new ViewModelProvider(this).get(DodajkosztViewModel.class);
        binding = FragmentDodajkosztBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textDodajkoszt;
        dodajkosztViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}