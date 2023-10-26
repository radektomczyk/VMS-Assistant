package com.example.vmsv3.ui.kalendarz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KalendarzViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public KalendarzViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Kalendarz");
    }

    public LiveData<String> getText() {
        return mText;
    }
}