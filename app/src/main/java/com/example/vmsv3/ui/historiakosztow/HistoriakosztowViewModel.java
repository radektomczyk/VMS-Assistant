package com.example.vmsv3.ui.historiakosztow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoriakosztowViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HistoriakosztowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Historia kosztow");
    }

    public LiveData<String> getText() {
        return mText;
    }
}