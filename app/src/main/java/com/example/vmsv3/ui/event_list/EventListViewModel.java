package com.example.vmsv3.ui.event_list;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.EventDto;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListViewModel extends ViewModel {

    private final MutableLiveData<List<EventDto>> eventsLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final ApiService apiService;
    private final Context context;

    public EventListViewModel() {
        this.apiService = null;
        this.context = null;
    }

    public EventListViewModel(ApiService apiService, Context context) {
        Log.d("EventListViewModel", "ApiService: " + apiService);
        this.apiService = apiService;
        this.context = context;
    }

    public LiveData<List<EventDto>> getEventsList(String authorizationHeader) {
        if (apiService == null) {
            Log.e("EventListViewModel", "ApiService is null");
            showToast("ApiService is null");
            return new MutableLiveData<>();
        }

        if (context == null) {
            Log.e("EventListViewModel", "Context is null");
            showToast("Context is null");
            return new MutableLiveData<>();
        }

        isLoading.setValue(true);

        Call<List<EventDto>> call = apiService.getEventsList(authorizationHeader);

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(@NonNull Call<List<EventDto>> call, @NonNull Response<List<EventDto>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    List<EventDto> events = response.body();
                    if (events != null && !events.isEmpty()) {
                        eventsLiveData.setValue(events);
                    } else {
                        Log.w("EventListViewModel", "Empty response body");
                        showToast("No events to show");
                    }
                } else {
                    Log.e("EventListViewModel", "Failed to fetch events. Code: " + response.code());

                    try {
                        Log.e("EventListViewModel", "Error Response: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    showToast("Failed to fetch events. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<EventDto>> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                Log.e("EventListViewModel", "Network error: " + t.getMessage(), t);
                showToast("Network error: " + t.getMessage());
            }
        });

        return eventsLiveData;
    }

    private void showToast(String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Log.e("EventListViewModel", "Context is null, unable to show Toast");
        }
    }
}
