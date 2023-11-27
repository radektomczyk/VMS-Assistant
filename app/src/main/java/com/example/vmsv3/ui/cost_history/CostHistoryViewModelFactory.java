package com.example.vmsv3.ui.cost_history;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;


import com.example.vmsv3.api.ApiService;

public class CostHistoryViewModelFactory implements ViewModelProvider.Factory {
    private final ApiService apiService;
    private final Context context;

    public CostHistoryViewModelFactory(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CostHistoryViewModel.class)) {
            return (T) new CostHistoryViewModel(apiService, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}