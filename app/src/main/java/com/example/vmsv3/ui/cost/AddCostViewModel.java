package com.example.vmsv3.ui.cost;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCostViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddCostViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj koszt");
    }
    public LiveData<String> getText() {
        return mText;
    }
}