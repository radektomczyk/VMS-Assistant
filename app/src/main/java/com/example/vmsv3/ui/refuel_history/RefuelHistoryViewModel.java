package com.example.vmsv3.ui.refuel_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RefuelHistoryViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public RefuelHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Historia tankowania");
    }

    public LiveData<String> getText() {
        return mText;
    }
}