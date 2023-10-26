package com.example.vmsv3.ui.dodajmandat;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vmsv3.databinding.FragmentDodajmandatBinding;

public class DodajmandatFragment extends Fragment {
    private FragmentDodajmandatBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DodajmandatViewModel dodajmandatViewModel = new ViewModelProvider(this).get(DodajmandatViewModel.class);
        binding = FragmentDodajmandatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textDodajmandat;
//        dodajmandatViewModel.getText().observe(getViewLifecycleOwner(),textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}