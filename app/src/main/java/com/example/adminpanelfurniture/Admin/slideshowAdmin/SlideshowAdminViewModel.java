package com.example.adminpanelfurniture.Admin.slideshowAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}