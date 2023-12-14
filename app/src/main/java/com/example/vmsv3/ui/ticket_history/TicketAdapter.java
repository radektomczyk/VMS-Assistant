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
        // Sort tickets by date
        Collections.sort(tickets, new Comparator<TicketDto>() {
            @Override
            public int compare(TicketDto ticket1, TicketDto ticket2) {
                // Assuming getReceiveDate() returns a comparable date format (e.g., String, Date, etc.)
                return ticket2.getReceiveDate().compareTo(ticket1.getReceiveDate());
            }
        });

        this.tickets = tickets;
        notifyDataSetChanged();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView ticketName;
        TextView ticketDate;

        private SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        private SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketName = itemView.findViewById(R.id.ticketHistoryNameTextView);
            ticketDate = itemView.findViewById(R.id.ticketHistoryDateTextView);
        }

        void bind(TicketDto ticket) {
            ticketName.setText(ticket.getReason());

            // Convert and format the date
            try {
                Date date = inputDateFormat.parse(ticket.getReceiveDate());
                String formattedDate = outputDateFormat.format(date);
                ticketDate.setText(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
                ticketDate.setText(ticket.getReceiveDate()); // Set the original date if parsing fails
            }
        }
    }
}