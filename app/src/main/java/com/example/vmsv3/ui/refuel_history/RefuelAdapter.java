package com.example.vmsv3.ui.refuel_history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.transport.RefuelDto;

import java.util.ArrayList;
import java.util.List;

public class RefuelAdapter extends RecyclerView.Adapter<RefuelAdapter.RefuelViewHolder> {
    private List<RefuelDto> refuels = new ArrayList<>();

    @NonNull
    @Override
    public RefuelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refuel, parent, false);
        return new RefuelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefuelAdapter.RefuelViewHolder holder, int position) {
        if (refuels != null && position < refuels.size()) {
            RefuelDto refuel = refuels.get(position);
            holder.bind(refuel);
        } else {
            Log.e("CostAdapter", "Invalid data or position");
        }
    }

    public int getItemCount() {
        return refuels.size();
    }

    public void setRefuels(List<RefuelDto> refuels) {
        this.refuels = refuels;
        notifyDataSetChanged();
    }

    static class RefuelViewHolder extends RecyclerView.ViewHolder{
        TextView carBrand;
        TextView carModel;
        TextView carPlate;
        TextView refuelDate;
        TextView refuelAmount;
        TextView fuelPrice;
        public RefuelViewHolder(@NonNull View itemView) {
            super(itemView);
            carBrand = itemView.findViewById(R.id.historyCarBrandTextView);
            carModel = itemView.findViewById(R.id.historyCarModelTextView);
            carPlate = itemView.findViewById(R.id.historyCarPlatesTextView);
            refuelDate = itemView.findViewById(R.id.historyRefuelDateTextView);
            refuelAmount = itemView.findViewById(R.id.historyFuelAmountTextView);
            fuelPrice = itemView.findViewById(R.id.historyFuelPriceTextView);
        }

        void bind(RefuelDto refuel) {
            refuelDate.setText(refuel.getRefuelDate());
            refuelAmount.setText(String.valueOf(refuel.getFuelAmount()));
            fuelPrice.setText(String.valueOf(refuel.getPricePerLiter()));
        }
    }
}
