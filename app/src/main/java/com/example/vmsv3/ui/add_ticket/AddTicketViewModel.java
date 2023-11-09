package com.example.vmsv3.ui.add_ticket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddTicketViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public AddTicketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Dodaj mandat");
    }
    public LiveData<String> getText() {return mText;}
}