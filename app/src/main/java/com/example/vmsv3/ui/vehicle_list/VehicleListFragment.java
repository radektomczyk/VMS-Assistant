package com.example.vmsv3.ui.vehicle_list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.databinding.FragmentVehicleListBinding;

public class VehicleListFragment extends Fragment {

    private FragmentVehicleListBinding binding;
    private VehicleListViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVehicleListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ApiService apiService;
        apiService = ApiClient.getApiClient().create(ApiService.class);

        ViewModelProvider.Factory factory = new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                if (modelClass.isAssignableFrom(VehicleListViewModel.class)) {
                    return (T) new VehicleListViewModel(apiService);
                }
                throw new IllegalArgumentException("Unknown ViewModel class");
            }
        };

        viewModel = new ViewModelProvider(this, factory).get(VehicleListViewModel.class);


        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewVehicleList);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        VehicleAdapter vehicleAdapter = new VehicleAdapter();
        recyclerView.setAdapter(vehicleAdapter);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null) {
            viewModel.getVehicleList("Bearer " + accessToken).observe(getViewLifecycleOwner(), vehicleList -> {
                vehicleAdapter.setVehicleList(vehicleList);
            });
        } else {
            Toast.makeText(requireContext(), "Access token not available", Toast.LENGTH_SHORT).show();
            Log.e("VehicleListFragment", "Access token is null. Redirect to login screen or handle accordingly.");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
