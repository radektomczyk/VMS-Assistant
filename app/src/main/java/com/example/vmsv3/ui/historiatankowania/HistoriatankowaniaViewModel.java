package com.example.vmsv3.ui.historiatankowania;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoriatankowaniaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public HistoriatankowaniaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Historia tankowania");
    }

    public LiveData<String> getText() {
        return mText;
    }
}