package com.task.vasskob.facebookgplussdk.view;

import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.task.vasskob.facebookgplussdk.model.User;

public interface LoginView {


    void switchToProfileFragment(User user, GoogleApiClient mGoogleApiClient);
    void showToast(String message);
    Context getContext();
}
