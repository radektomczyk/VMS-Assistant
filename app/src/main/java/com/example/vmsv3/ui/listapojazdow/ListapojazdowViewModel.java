package com.example.vmsv3.ui.listapojazdow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListapojazdowViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ListapojazdowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Lista pojazdow");
    }

    public LiveData<String> getText() {
        return mText;
    }
}