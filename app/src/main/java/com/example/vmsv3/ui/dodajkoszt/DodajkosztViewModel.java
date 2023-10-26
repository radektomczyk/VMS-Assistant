package com.example.vmsv3.ui.dodajkoszt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DodajkosztViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DodajkosztViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj koszt");
    }
    public LiveData<String> getText() {
        return mText;
    }
}