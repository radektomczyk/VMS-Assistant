package com.example.vmsv3.ui.vehicle_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.transport.VehicleDto;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<com.example.vmsv3.ui.vehicle_list.VehicleAdapter.VehicleViewHolder> {

    private List<VehicleDto> vehicleList = new ArrayList<>();

    @NonNull
    @Override
    public com.example.vmsv3.ui.vehicle_list.VehicleAdapter.VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vehicle, parent, false);
        return new com.example.vmsv3.ui.vehicle_list.VehicleAdapter.VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.vmsv3.ui.vehicle_list.VehicleAdapter.VehicleViewHolder holder, int position) {
        VehicleDto vehicle = vehicleList.get(position);
        holder.bind(vehicle);
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }

    public void setVehicleList(List<VehicleDto> vehicleList) {
        this.vehicleList = vehicleList;
        notifyDataSetChanged();
    }

    static class VehicleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBrand;
        TextView textViewModel;
        TextView textViewPlate;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBrand = itemView.findViewById(R.id.carBrandTextView);
            textViewModel = itemView.findViewById(R.id.carModelTextView);
            textViewPlate = itemView.findViewById(R.id.carPlatesTextView);
        }

        void bind(VehicleDto vehicle) {
            textViewBrand.setText(vehicle.getCarBrand());
            textViewModel.setText(String.valueOf(vehicle.getCarModel()));
            textViewPlate.setText(vehicle.getCarPlate());
        }
    }
}