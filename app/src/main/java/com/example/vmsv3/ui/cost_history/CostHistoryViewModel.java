package com.example.vmsv3.ui.cost_history;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.CostDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostHistoryViewModel extends ViewModel {
    private final MutableLiveData<List<CostDto>> costsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;

    public CostHistoryViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<CostDto>> getCosts(String authorizationHeader) {
        isLoading.setValue(true);

        Call<List<CostDto>> call = apiService.getCosts(authorizationHeader);

        call.enqueue(new Callback<List<CostDto>>() {
            @Override
            public void onResponse(Call<List<CostDto>> call, Response<List<CostDto>> response) {
                isLoading.setValue(false); // Reset loading state
                if (response.isSuccessful()) {
                    costsLiveData.setValue(response.body());
                } else {
                    Log.e("CostHistoryViewModel", "Failed to fetch costs. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CostDto>> call, Throwable t) {
                isLoading.setValue(false); // Reset loading state
                Log.e("CostHistoryViewModel", "Network error: " + t.getMessage(), t);
            }
        });

        return costsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }
}
