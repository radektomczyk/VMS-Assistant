package com.example.vmsv3.ui.dodajwydarzenie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DodajwydarzenieViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public DodajwydarzenieViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj wydarzenie");
    }
    public LiveData<String> getText() {return mText;}
}