package com.example.vmsv3.ui.refuel_history;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.RefuelDto;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefuelHistoryViewModel extends ViewModel {
    private final MutableLiveData<List<RefuelDto>> refuelsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;
    private final Context context;

    public RefuelHistoryViewModel() {
        this.apiService = null;
        this.context = null;
    }

    public RefuelHistoryViewModel(ApiService apiService, Context context) {
        Log.d("RefuelHistoryViewModel", "ApiService: " + apiService);
        this.apiService = apiService;
        this.context = context;
    }

    public LiveData<List<RefuelDto>> getRefuels(String authorizationHeader) {
        if (apiService == null) {
            Log.e("RefuelHistoryViewModel", "ApiService is null");
            showToast("ApiService is null");
            return new MutableLiveData<>();
        }

        if (context == null) {
            Log.e("RefuelHistoryViewModel", "Context is null");
            showToast("Context is null");
            return new MutableLiveData<>();
        }

        isLoading.setValue(true);

        Call<List<RefuelDto>> call = apiService.getRefuels(authorizationHeader);



        call.enqueue(new Callback<List<RefuelDto>>() {
            @Override
            public void onResponse(Call<List<RefuelDto>> call, Response<List<RefuelDto>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    List<RefuelDto> refuels = response.body();
                    if (refuels != null && !refuels.isEmpty()) {
                        refuelsLiveData.setValue(refuels);
                    } else {
                        Log.w("RefuelHistoryViewModel", "Empty response body");
                        showToast("No refuel history to show");
                    }
                } else {
                    Log.e("RefuelHistoryViewModel", "Failed to fetch refuels. Code: " + response.code());

                    try {
                        Log.e("RefuelHistoryViewModel", "Error Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    showToast("Failed to fetch refuels. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<RefuelDto>> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("RefuelHistoryViewModel", "Network error: " + t.getMessage(), t);
                showToast("Network error: " + t.getMessage());
            }
        });

        return refuelsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }



    private void showToast(String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("RefuelHistoryViewModel", "Context is null, unable to show Toast");
        }
    }
}
