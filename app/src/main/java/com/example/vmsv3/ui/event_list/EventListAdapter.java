package com.example.vmsv3.ui.event_list;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.EventDto;
import com.example.vmsv3.transport.VehicleDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {
    private List<EventDto> events = new ArrayList<>();
    private ApiService apiService;
    private String accessToken;

    public EventListAdapter(ApiService apiService, String accessToken) {
        this.apiService = apiService;
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new EventViewHolder(view, apiService, accessToken);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        if (events != null && position < events.size()) {
            EventDto event = events.get(position);
            holder.bind(event);
        } else {
            Log.e("EventListAdapter", "Invalid data or position");
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setEventList(List<EventDto> events) {
        Collections.sort(events, new Comparator<EventDto>() {
            @Override
            public int compare(EventDto event1, EventDto event2) {
                return event2.getDate().compareTo(event1.getDate());
            }
        });

        this.events = events;
        notifyDataSetChanged();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName;
        TextView eventDescription;
        TextView eventDate;
        TextView carBrand;
        TextView carModel;
        TextView carPlate;

        private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

        private ApiService apiService;
        private String accessToken;

        public EventViewHolder(@NonNull View itemView, ApiService apiService, String accessToken) {
            super(itemView);
            this.apiService = apiService;
            this.accessToken = accessToken;
            eventName = itemView.findViewById(R.id.eventNameTextView);
            eventDate = itemView.findViewById(R.id.eventDateTextView);
            eventDescription = itemView.findViewById(R.id.eventDescriptionTextView);
            carBrand = itemView.findViewById(R.id.eventCarBrandTextView);
            carModel = itemView.findViewById(R.id.eventCarModelTextView);
            carPlate = itemView.findViewById(R.id.eventCarPlatesTextView);
        }

        void bind(EventDto event) {
            Log.d("EventDto", "Event Name: " + event.getName() +
                    ", Event Description: " + event.getDescription() +
                    ", Event Date: " + event.getDate() +
                    ", Event Cost: " + event.getKoszt() +
                    ", Event Remind: " + event.getRemind() +
                    ", Event Repeatable: " + event.getRepeatable() +
                    ", Event Interval: " + event.getInterval() +
                    ", Vehicle ID: " + event.getVehicleId());

            eventName.setText(event.getName());
            eventDescription.setText("Opis: " + event.getDescription());

            try {
                Date date = inputDateFormat.parse(event.getDate());
                String formattedDate = outputDateFormat.format(date);
                eventDate.setText("Data wydarzenia: " + formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                eventDate.setText(event.getDate());
            }
            fetchAndDisplayVehicleDetails(event.getVehicleId(), this);
        }

        void fetchAndDisplayVehicleDetails(long vehicleId, EventListAdapter.EventViewHolder holder) {
            Call<VehicleDto> call = apiService.getVehicleDetails("Bearer " + accessToken, vehicleId);

            call.enqueue(new Callback<VehicleDto>() {
                @Override
                public void onResponse(Call<VehicleDto> call, Response<VehicleDto> response) {
                    if (response.isSuccessful()) {
                        VehicleDto vehicle = response.body();
                        if (vehicle != null) {
                            holder.bindVehicleDetails(vehicle);
                        } else {
                            Log.e("EventListAdapter", "Vehicle response body is null");
                        }
                    } else {
                        Log.e("EventListAdapter", "Failed to fetch vehicle details. Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<VehicleDto> call, Throwable t) {
                    Log.e("EventListAdapter", "Failed to fetch vehicle details: " + t.getMessage(), t);
                }
            });
        }

        void bindVehicleDetails(VehicleDto vehicle) {
            carBrand.setText("Marka: " + vehicle.getCarBrand());
            carModel.setText("Model: " + vehicle.getCarModel());
            carPlate.setText("Numer rejestracyjny: " + vehicle.getCarPlate());
        }
    }
}
