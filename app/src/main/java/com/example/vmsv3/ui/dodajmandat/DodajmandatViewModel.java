package com.example.vmsv3.ui.dodajmandat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DodajmandatViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DodajmandatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj mandat");
    }
    public LiveData<String> getText() {return mText;}
}