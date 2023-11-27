package com.example.vmsv3.ui.ticket_history;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.TicketDto;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketHistoryViewModel extends ViewModel {
    private final MutableLiveData<List<TicketDto>> ticketsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;
    private final Context context;

    public TicketHistoryViewModel() {
        this.apiService = null;
        this.context = null;
    }

    public TicketHistoryViewModel(ApiService apiService, Context context) {
        Log.d("TicketHistoryViewModel", "ApiService: " + apiService);
        this.apiService = apiService;
        this.context = context;
    }

    public LiveData<List<TicketDto>> getTickets(String authorizationHeader) {
        if (apiService == null) {
            Log.e("TicketHistoryViewModel", "ApiService is null");
            showToast("ApiService is null");
            return new MutableLiveData<>();
        }

        if (context == null) {
            Log.e("TicketHistoryViewModel", "Context is null");
            showToast("Context is null");
            return new MutableLiveData<>();
        }

        isLoading.setValue(true);

        Call<List<TicketDto>> call = apiService.getTickets(authorizationHeader);

        call.enqueue(new Callback<List<TicketDto>>() {
            @Override
            public void onResponse(Call<List<TicketDto>> call, Response<List<TicketDto>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    List<TicketDto> tickets = response.body();
                    if (tickets != null && !tickets.isEmpty()) {
                        ticketsLiveData.setValue(tickets);
                    } else {
                        Log.w("TicketHistoryViewModel", "Empty response body");
                        showToast("No ticket history to show");
                    }
                } else {
                    Log.e("TicketHistoryViewModel", "Failed to fetch tickets. Code: " + response.code());

                    try {
                        Log.e("TicketHistoryViewModel", "Error Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    showToast("Failed to fetch tickets. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<TicketDto>> call, Throwable t) {
                isLoading.setValue(false);
                Log.e("TicketHistoryViewModel", "Network error: " + t.getMessage(), t);
                showToast("Network error: " + t.getMessage());
            }
        });
        return ticketsLiveData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    private void showToast(String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("TicketHistoryViewModel", "Context is null, unable to show Toast");
        }
    }
}
