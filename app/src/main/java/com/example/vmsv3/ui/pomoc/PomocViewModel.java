package com.example.vmsv3.ui.pomoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PomocViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PomocViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pomoc");
    }

    public LiveData<String> getText() {
        return mText;
    }
}