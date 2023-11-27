package com.example.vmsv3.ui.ticket_history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.transport.TicketDto;

import java.util.ArrayList;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private List<TicketDto> tickets = new ArrayList<>();

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        if (tickets != null && position < tickets.size()) {
            TicketDto ticket = tickets.get(position);
            holder.bind(ticket);
        } else {
            Log.e("TicketAdapter", "Invalid data or position");
        }
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public void setTickets(List<TicketDto> tickets) {
        this.tickets = tickets;
        notifyDataSetChanged();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView ticketName;
        TextView ticketDate;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketName = itemView.findViewById(R.id.ticketHistoryNameTextView);
            ticketDate = itemView.findViewById(R.id.ticketHistoryDateTextView);
        }

        void bind(TicketDto ticket) {
            ticketName.setText(ticket.getReason());
            ticketDate.setText(ticket.getReceiveDate());
        }
    }
}