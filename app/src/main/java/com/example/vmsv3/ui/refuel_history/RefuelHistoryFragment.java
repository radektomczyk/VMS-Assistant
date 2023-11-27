package com.example.vmsv3.ui.refuel_history;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.databinding.FragmentRefuelHistoryBinding;


public class RefuelHistoryFragment extends Fragment {

    private FragmentRefuelHistoryBinding binding;
    private RefuelHistoryViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRefuelHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        viewModel = new ViewModelProvider(this, new RefuelHistoryViewModelFactory(apiService, requireContext())).get(RefuelHistoryViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewRefuels);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        RefuelAdapter refuelAdapter = new RefuelAdapter();
        recyclerView.setAdapter(refuelAdapter);

        TextView textViewNoRefuelHistory = root.findViewById(R.id.textViewNoRefuelHistory);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null) {
            viewModel.getRefuels("Bearer " + accessToken).observe(getViewLifecycleOwner(), refuels -> {
                if (refuels != null && !refuels.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoRefuelHistory.setVisibility(View.GONE);
                    refuelAdapter.setRefuels(refuels);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoRefuelHistory.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(requireContext(), "Access token not available", Toast.LENGTH_SHORT).show();
            Log.e("RefuelHistoryFragment", "Access token is null. Redirect to login screen or handle accordingly.");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
