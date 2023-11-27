package com.example.vmsv3.ui.ticket_history;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.databinding.FragmentTicketHistoryBinding;


public class TicketHistoryFragment extends Fragment{
    private FragmentTicketHistoryBinding binding;
    private TicketHistoryViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTicketHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);

        viewModel = new ViewModelProvider(this, new TicketHistoryViewModelFactory(apiService, requireContext())).get(TicketHistoryViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewTickets);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        TicketAdapter ticketAdapter = new TicketAdapter();
        recyclerView.setAdapter(ticketAdapter);

        TextView textViewNoTicketHistory = root.findViewById(R.id.textViewNoTicketHistory);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken != null) {
            viewModel.getTickets("Bearer " + accessToken).observe(getViewLifecycleOwner(), tickets -> {
                if (tickets != null && !tickets.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoTicketHistory.setVisibility(View.GONE);
                    ticketAdapter.setTickets(tickets);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoTicketHistory.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(requireContext(), "Access token not available", Toast.LENGTH_SHORT).show();
            Log.e("TicketHistoryFragment", "Access token is null. Redirect to login screen or handle accordingly.");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
