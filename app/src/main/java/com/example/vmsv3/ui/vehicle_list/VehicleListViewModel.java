package com.example.vmsv3.ui.vehicle_list;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.VehicleDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleListViewModel extends ViewModel {
    private final MutableLiveData<List<VehicleDto>> vehicleListLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;

    public VehicleListViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<VehicleDto>> getVehicleList(String authorizationHeader) {
        isLoading.setValue(true);

        Call<List<VehicleDto>> call = apiService.getVehicleList(authorizationHeader);

        call.enqueue(new Callback<List<VehicleDto>>() {
            @Override
            public void onResponse(Call<List<VehicleDto>> call, Response<List<VehicleDto>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    vehicleListLiveData.setValue(response.body());
                } else {
                    Log.e("VehicleListViewModel", "Failed to fetch vehicle list. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<VehicleDto>> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("VehicleListViewModel", "Network error: " + t.getMessage(), t);
            }
        });

        return vehicleListLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }
}
