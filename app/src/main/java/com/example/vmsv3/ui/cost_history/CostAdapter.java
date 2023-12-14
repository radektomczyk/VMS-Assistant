package com.example.vmsv3.ui.cost_history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.transport.CostDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class CostAdapter extends RecyclerView.Adapter<CostAdapter.CostViewHolder> {
    private List<CostDto> costs = new ArrayList<>();

    @NonNull
    @Override
    public CostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cost, parent, false);
        return new CostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CostViewHolder holder, int position) {
        if (costs != null && position < costs.size()) {
            CostDto cost = costs.get(position);
            holder.bind(cost);
        } else {
            Log.e("CostAdapter", "Invalid data or position");
        }
    }

    @Override
    public int getItemCount() {
        return costs.size();
    }

    public void setCosts(List<CostDto> costs) {
        Collections.sort(costs, new Comparator<CostDto>() {
            @Override
            public int compare(CostDto cost1, CostDto cost2) {
                return cost2.getCostDate().compareTo(cost1.getCostDate());
            }
        });

        this.costs = costs;
        notifyDataSetChanged();
    }

    static class CostViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCostName;
        TextView textViewCostAmount;
        TextView textViewCostDate;

        private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

        public CostViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCostName = itemView.findViewById(R.id.textViewCostName);
            textViewCostAmount = itemView.findViewById(R.id.textViewCostAmount);
            textViewCostDate = itemView.findViewById(R.id.textViewCostDate);
        }

        void bind(CostDto cost) {
            textViewCostName.setText(cost.getCostName());
            textViewCostAmount.setText(String.valueOf(cost.getCostAmount()));

            // Convert and format the date
            try {
                Date date = inputDateFormat.parse(cost.getCostDate());
                String formattedDate = outputDateFormat.format(date);
                textViewCostDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                textViewCostDate.setText(cost.getCostDate()); // Set the original date if parsing fails
            }
        }
    }
}