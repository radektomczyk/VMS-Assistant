package com.example.vmsv3.ui.stronaglowna;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StronaglownaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public StronaglownaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Strona glowna");
    }

    public LiveData<String> getText() {
        return mText;
    }
}