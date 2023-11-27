package com.example.vmsv3.ui.cost_history;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.CostDto;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CostHistoryViewModel extends ViewModel {
    private final MutableLiveData<List<CostDto>> costsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;
    private final Context context;

    public CostHistoryViewModel() {
        this.apiService = null;
        this.context = null;
    }

    public CostHistoryViewModel(ApiService apiService, Context context) {
        Log.d("CostHistoryViewModel", "ApiService: " + apiService);
        this.apiService = apiService;
        this.context = context;
    }

    public LiveData<List<CostDto>> getCosts(String authorizationHeader) {
        if (apiService == null) {
            Log.e("CostHistoryViewModel", "ApiService is null");
            showToast("ApiService is null");
            return new MutableLiveData<>();
        }

        if (context == null) {
            Log.e("CostHistoryViewModel", "Context is null");
            showToast("Context is null");
            return new MutableLiveData<>();
        }

        isLoading.setValue(true);

        Call<List<CostDto>> call = apiService.getCosts(authorizationHeader);

        call.enqueue(new Callback<List<CostDto>>() {
            @Override
            public void onResponse(Call<List<CostDto>> call, Response<List<CostDto>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    List<CostDto> costs = response.body();
                    if (costs != null && !costs.isEmpty()) {
                        costsLiveData.setValue(costs);
                    } else {
                        Log.w("CostHistoryViewModel", "Empty response body");
                        showToast("No cost history to show");
                    }
                } else {
                    Log.e("CostHistoryViewModel", "Failed to fetch costs. Code: " + response.code());

                    try {
                        Log.e("CostHistoryViewModel", "Error Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    showToast("Failed to fetch costs. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CostDto>> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("CostHistoryViewModel", "Network error: " + t.getMessage(), t);
                showToast("Network error: " + t.getMessage());
            }
        });


        return costsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    private void showToast(String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("CostHistoryViewModel", "Context is null, unable to show Toast");
        }
    }
}
