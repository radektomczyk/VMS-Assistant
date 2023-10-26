package com.example.vmsv3.ui.listapojazdow;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vmsv3.databinding.FragmentListapojazdowBinding;

public class ListapojazdowFragment extends Fragment {
    private FragmentListapojazdowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        ListapojazdowViewModel listapojazdowViewModel =
                new ViewModelProvider(this).get(ListapojazdowViewModel.class);

        binding = FragmentListapojazdowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textListapojazdow;
        listapojazdowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}