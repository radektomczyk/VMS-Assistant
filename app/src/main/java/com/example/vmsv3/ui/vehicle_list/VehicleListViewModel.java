package com.example.vmsv3.ui.vehicle_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehicleListViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public VehicleListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lista pojazdow");
    }

    public LiveData<String> getText() {
        return mText;
    }
}