package com.example.vmsv3.ui.cost_history;

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
import com.example.vmsv3.databinding.FragmentCostHistoryBinding;

public class CostHistoryFragment extends Fragment {

    private FragmentCostHistoryBinding binding;
    private CostHistoryViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCostHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        viewModel = new ViewModelProvider(this, new CostHistoryViewModelFactory(apiService, requireContext())).get(CostHistoryViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewCosts);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        CostAdapter costAdapter = new CostAdapter();
        recyclerView.setAdapter(costAdapter);

        TextView textViewNoCostHistory = root.findViewById(R.id.textViewNoCostHistory);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null) {
            viewModel.getCosts("Bearer " + accessToken).observe(getViewLifecycleOwner(), costs -> {
                if (costs != null && !costs.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoCostHistory.setVisibility(View.GONE);
                    costAdapter.setCosts(costs);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoCostHistory.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(requireContext(), "Access token not available", Toast.LENGTH_SHORT).show();
            Log.e("CostHistoryFragment", "Access token is null. Redirect to login screen or handle accordingly.");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
