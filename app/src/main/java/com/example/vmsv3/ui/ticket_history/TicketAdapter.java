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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
        Collections.sort(tickets, new Comparator<TicketDto>() {
            @Override
            public int compare(TicketDto ticket1, TicketDto ticket2) {
                return ticket2.getReceiveDate().compareTo(ticket1.getReceiveDate());
            }
        });

        this.tickets = tickets;
        notifyDataSetChanged();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        private final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private final SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");
        TextView ticketName;
        TextView ticketPenaltyPoints;
        TextView ticketAmount;
        TextView ticketDate;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketName = itemView.findViewById(R.id.ticketHistoryNameTextView);
            ticketPenaltyPoints = itemView.findViewById(R.id.ticketHistoryPenaltyPointsTextView);
            ticketAmount = itemView.findViewById(R.id.ticketHistoryAmountTextView);
            ticketDate = itemView.findViewById(R.id.ticketHistoryDateTextView);
        }

        void bind(TicketDto ticket) {
            ticketName.setText(ticket.getReason());
            ticketPenaltyPoints.setText("Punkty karne: " + String.valueOf(ticket.getPenaltyPoints()));
            ticketAmount.setText( "Kwota: " + String.valueOf(ticket.getAmount() / 100) + " zł");
            try {
                Date date = inputDateFormat.parse(ticket.getReceiveDate());
                String formattedDate = outputDateFormat.format(date);
                ticketDate.setText("Data: " + formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                ticketDate.setText(ticket.getReceiveDate());
            }
        }
    }
}