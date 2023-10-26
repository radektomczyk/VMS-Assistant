package com.example.vmsv3.ui.pomoc;

import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vmsv3.R;
import com.example.vmsv3.databinding.FragmentPomocBinding;

public class PomocFragment extends Fragment {

    private FragmentPomocBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        PomocViewModel pomocViewModel = new ViewModelProvider(this).get(PomocViewModel.class);

        binding = FragmentPomocBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textPomoc;
//        pomocViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}