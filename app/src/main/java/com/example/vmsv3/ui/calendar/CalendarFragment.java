package com.example.vmsv3.ui.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vmsv3.R;
import com.example.vmsv3.api.ApiClient;
import com.example.vmsv3.api.ApiService;
import com.example.vmsv3.databinding.FragmentCalendarBinding;
import com.example.vmsv3.transport.EventDto;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private MaterialCalendarView materialCalendarView;
    private TextView eventListTextView;

    private List<EventDto> eventList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        materialCalendarView = root.findViewById(R.id.materialCalendarView);
        eventListTextView = root.findViewById(R.id.eventList);

        // Fetch events from the API
        fetchEvents();

        return root;
    }

    private void fetchEvents() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("ACCESS_TOKEN", null);

        if (accessToken == null) {
            return;
        }

        ApiService apiService = ApiClient.getApiClient().create(ApiService.class);
        Call<List<EventDto>> call = apiService.getEventsList("Bearer " + accessToken);

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                if (response.isSuccessful()) {
                    eventList = response.body();

                    displayEvents();
                    highlightCalendarDates();
                }
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                // Handle network request failure
            }
        });
    }

    private void displayEvents() {
        if (eventList != null && eventList.size() > 0) {
            StringBuilder eventStringBuilder = new StringBuilder("Events for the month:\n");
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd-MM-yyyy | HH:mm:ss");

            for (EventDto event : eventList) {
                try {
                    Date eventDate = inputDateFormat.parse(event.getDate());
                    String formattedDate = outputDateFormat.format(eventDate);

                    eventStringBuilder.append(event.getName()).append(" - ").append(formattedDate).append("\n");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            eventListTextView.setText(eventStringBuilder.toString());
        }
    }

    private void highlightDateInCalendar(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date date = dateFormat.parse(dateString);

            // Convert the date to CalendarDay using the user's device timezone
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            CalendarDay highlightedDate = CalendarDay.from(calendar);

            Log.d("HighlightDate", "Highlighting date: " + dateString);

            if (materialCalendarView != null) {
                materialCalendarView.addDecorator(new EventDecorator(Color.RED, highlightedDate.getDate()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void highlightCalendarDates() {
        if (eventList != null && eventList.size() > 0) {
            for (EventDto event : eventList) {
                highlightDateInCalendar(event.getDate());
            }
            materialCalendarView.setSelectionColor(Color.RED);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private class EventDecorator implements DayViewDecorator {
        private int color;
        private Date date;

        public EventDecorator(int color, Date date) {
            this.color = color;
            this.date = date;
        }

        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return CalendarDay.from(date).equals(day);
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
}
