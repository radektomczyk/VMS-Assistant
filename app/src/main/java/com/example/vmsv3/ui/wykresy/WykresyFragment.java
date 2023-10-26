package com.example.vmsv3.ui.wykresy;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vmsv3.databinding.FragmentWykresyBinding;

public class WykresyFragment extends Fragment {
    private FragmentWykresyBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        WykresyViewModel wykresyViewModel = new ViewModelProvider(this).get(WykresyViewModel.class);
        binding = FragmentWykresyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        final TextView textView = binding.textWykresy;
//        wykresyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}