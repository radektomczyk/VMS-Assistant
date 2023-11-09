package com.example.vmsv3.ui.cost_history;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CostHistoryViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public CostHistoryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Historia kosztow");
    }

    public LiveData<String> getText() {
        return mText;
    }
}