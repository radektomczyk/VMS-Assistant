package com.example.vmsv3.ui.main_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainPageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MainPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Strona glowna");
    }

    public LiveData<String> getText() {
        return mText;
    }
}