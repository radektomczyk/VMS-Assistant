package com.example.vmsv3.ui.dodajtankowanie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DodajtankowanieViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DodajtankowanieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj tankowanie");
    }

    public LiveData<String> getText() {
        return mText;
    }
}