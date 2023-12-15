package com.example.vmsv3.ui.event_list;

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
import com.example.vmsv3.databinding.FragmentEventListBinding;

public class EventListFragment extends Fragment {

    private FragmentEventListBinding binding;
    private EventListViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        viewModel = new ViewModelProvider(this, new EventListViewModelFactory(apiService, requireContext())).get(EventListViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.recyclerViewEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        EventListAdapter eventListAdapter = new EventListAdapter(apiService, accessToken);
        recyclerView.setAdapter(eventListAdapter);

        TextView textViewNoEvent = root.findViewById(R.id.textViewNoEvent);

        if (accessToken != null) {
            viewModel.getEventsList("Bearer " + accessToken).observe(getViewLifecycleOwner(), events -> {
                if (events != null && !events.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    textViewNoEvent.setVisibility(View.GONE);
                    eventListAdapter.setEventList(events);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    textViewNoEvent.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(requireContext(), "Access token not available", Toast.LENGTH_SHORT).show();
            Log.e("EventListFragment", "Access token is null. Redirect to login screen or handle accordingly.");
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
