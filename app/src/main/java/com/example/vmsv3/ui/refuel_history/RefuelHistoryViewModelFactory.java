package com.example.vmsv3.ui.refuel_history;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vmsv3.api.ApiService;

public class RefuelHistoryViewModelFactory implements ViewModelProvider.Factory {
    private final ApiService apiService;
    private final Context context;

    public RefuelHistoryViewModelFactory(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RefuelHistoryViewModel.class)) {
            return (T) new RefuelHistoryViewModel(apiService, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
