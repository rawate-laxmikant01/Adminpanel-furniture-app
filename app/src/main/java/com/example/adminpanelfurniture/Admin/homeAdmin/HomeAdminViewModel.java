package com.example.adminpanelfurniture.Admin.homeAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}