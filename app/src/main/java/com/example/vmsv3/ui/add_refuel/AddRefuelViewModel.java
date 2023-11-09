package com.example.vmsv3.ui.add_refuel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddRefuelViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddRefuelViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj tankowanie");
    }

    public LiveData<String> getText() {
        return mText;
    }
}