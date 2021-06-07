package com.example.adminpanelfurniture.Admin.galleryAdmin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryAdminViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryAdminViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}