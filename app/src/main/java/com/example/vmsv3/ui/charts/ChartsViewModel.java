package com.example.vmsv3.ui.charts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChartsViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ChartsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wykresy");
    }

    public LiveData<String> getText() {
        return mText;
    }
}