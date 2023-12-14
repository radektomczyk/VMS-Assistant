package com.example.vmsv3.ui.refuel_history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.transport.RefuelDto;
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

public class RefuelAdapter extends RecyclerView.Adapter<RefuelAdapter.RefuelViewHolder> {
    private List<RefuelDto> refuels = new ArrayList<>();
    private ApiService apiService;
    private String accessToken;

    public RefuelAdapter(ApiService apiService, String accessToken) {
        this.apiService = apiService;
        this.accessToken = accessToken;
    }

    @NonNull
    @Override
    public RefuelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refuel, parent, false);
        return new RefuelViewHolder(view, apiService, accessToken);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelAdapter.RefuelViewHolder holder, int position) {
        if (refuels != null && position < refuels.size()) {
            RefuelDto refuel = refuels.get(position);
            holder.bind(refuel);
        } else {
            Log.e("RefuelAdapter", "Invalid data or position");
        }
    }

    @Override
    public int getItemCount() {
        return refuels.size();
    }

    public void setRefuels(List<RefuelDto> refuels) {
        Collections.sort(refuels, new Comparator<RefuelDto>() {
            @Override
            public int compare(RefuelDto refuel1, RefuelDto refuel2) {
                return refuel2.getRefuelDate().compareTo(refuel1.getRefuelDate());
            }
        });

        this.refuels = refuels;
        notifyDataSetChanged();
    }


    static class RefuelViewHolder extends RecyclerView.ViewHolder {
        TextView carBrand;
        TextView carModel;
        TextView carPlate;
        TextView refuelDate;
        TextView refuelAmount;
        TextView fuelPrice;

        private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

        private ApiService apiService;
        private String accessToken;

        public RefuelViewHolder(@NonNull View itemView, ApiService apiService, String accessToken) {
            super(itemView);
            this.apiService = apiService;
            this.accessToken = accessToken;
            carBrand = itemView.findViewById(R.id.historyCarBrandTextView);
            carModel = itemView.findViewById(R.id.historyCarModelTextView);
            carPlate = itemView.findViewById(R.id.historyCarPlatesTextView);
            refuelDate = itemView.findViewById(R.id.historyRefuelDateTextView);
            refuelAmount = itemView.findViewById(R.id.historyFuelAmountTextView);
            fuelPrice = itemView.findViewById(R.id.historyFuelPriceTextView);
        }

        void bind(RefuelDto refuel) {
            Log.d("RefuelDto", "Fuel Amount: " + refuel.getFuelAmount() +
                    ", Fuel Type: " + refuel.getFuelType() +
                    ", Price Per Liter: " + refuel.getPricePerLiter() +
                    ", Total Price: " + refuel.getTotalPrice() +
                    ", Refuel Date: " + refuel.getRefuelDate() +
                    ", Blockade: " + refuel.getBlockade() +
                    ", Vehicle ID: " + refuel.getVehicleId());

            // Convert and format the date
            try {
                Date date = inputDateFormat.parse(refuel.getRefuelDate());
                String formattedDate = outputDateFormat.format(date);
                refuelDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                refuelDate.setText(refuel.getRefuelDate()); // Set the original date if parsing fails
            }

            refuelAmount.setText(String.valueOf(refuel.getFuelAmount()));
            fuelPrice.setText(String.valueOf(refuel.getPricePerLiter()));

            fetchAndDisplayVehicleDetails(refuel.getVehicleId(), this);
        }


        void fetchAndDisplayVehicleDetails(long vehicleId, RefuelViewHolder holder) {
            Call<VehicleDto> call = apiService.getVehicleDetails("Bearer " + accessToken, vehicleId);

            call.enqueue(new Callback<VehicleDto>() {
                @Override
                public void onResponse(Call<VehicleDto> call, Response<VehicleDto> response) {
                    if (response.isSuccessful()) {
                        VehicleDto vehicle = response.body();
                        if (vehicle != null) {
                            holder.bindVehicleDetails(vehicle);
                        } else {
                            Log.e("RefuelAdapter", "Vehicle response body is null");
                        }
                    } else {
                        Log.e("RefuelAdapter", "Failed to fetch vehicle details. Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<VehicleDto> call, Throwable t) {
                    Log.e("RefuelAdapter", "Failed to fetch vehicle details: " + t.getMessage(), t);
                }
            });
        }

        void bindVehicleDetails(VehicleDto vehicle) {
            carBrand.setText(vehicle.getCarBrand());
            carModel.setText(vehicle.getCarModel());
            carPlate.setText(vehicle.getCarPlate());
        }
    }
}