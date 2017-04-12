package com.task.vasskob.facebookgplussdk.view;

import com.google.android.gms.common.api.GoogleApiClient;
import com.task.vasskob.facebookgplussdk.model.User;

public interface LoginView {

    // TODO: 12/04/17 betteronLogInSuccess, why mGoogleApiClient needed?
    void switchToProfileFragment(User user, GoogleApiClient mGoogleApiClient);

    void showToast(String message);
}
