package com.example.vmsv3.ui.refuel_history;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.RefuelDto;

import java.util.List;

public class RefuelHistoryViewModel extends ViewModel {
    private final MutableLiveData<List<RefuelDto>> refuelsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;
    private final Context context;

    public RefuelHistoryViewModel() {
        apiService = null;
        context = null;
    }

    public RefuelHistoryViewModel(ApiService apiService, Context context) {
        Log.d("RefuelHistoryViewModel", "ApiService: " + apiService);
        this.apiService = apiService;
        this.context = context;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }
}
