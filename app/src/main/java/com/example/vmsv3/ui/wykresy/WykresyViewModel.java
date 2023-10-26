package com.example.vmsv3.ui.wykresy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WykresyViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public WykresyViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Wykresy");
    }

    public LiveData<String> getText() {
        return mText;
    }
}