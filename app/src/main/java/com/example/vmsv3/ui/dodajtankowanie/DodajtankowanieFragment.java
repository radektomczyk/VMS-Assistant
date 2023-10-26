package com.example.vmsv3.ui.dodajtankowanie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.vmsv3.databinding.FragmentDodajtankowanieBinding;

public class DodajtankowanieFragment extends Fragment {
    private FragmentDodajtankowanieBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        DodajtankowanieViewModel dodajtankowanieViewModel =
                new ViewModelProvider(this).get(DodajtankowanieViewModel.class);
        binding = FragmentDodajtankowanieBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextView textView = binding.textDodajtankowanie;
        dodajtankowanieViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}