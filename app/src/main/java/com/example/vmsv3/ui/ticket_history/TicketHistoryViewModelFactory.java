package com.example.vmsv3.ui.ticket_history;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.vmsv3.api.ApiService;

public class TicketHistoryViewModelFactory implements ViewModelProvider.Factory {
    private final ApiService apiService;
    private final Context context;

    public TicketHistoryViewModelFactory(ApiService apiService, Context context) {
        this.apiService = apiService;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TicketHistoryViewModel.class)) {
            return (T) new TicketHistoryViewModel(apiService, context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}