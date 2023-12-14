package com.example.vmsv3.ui.calendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class HighlightDecorator implements DayViewDecorator {

    private final CalendarDay date;

    public HighlightDecorator(CalendarDay date) {
        this.date = date;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return date != null &&
                day.getYear() == date.getYear() &&
                day.getMonth() == date.getMonth() &&
                day.getDay() == date.getDay();
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(Color.RED));
    }
}
