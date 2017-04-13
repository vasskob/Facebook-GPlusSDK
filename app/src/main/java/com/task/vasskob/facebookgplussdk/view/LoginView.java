package com.task.vasskob.facebookgplussdk.view;

import com.task.vasskob.facebookgplussdk.model.User;

public interface LoginView {

    // TODO: 12/04/17 betteronLogInSuccess, why mGoogleApiClient needed?
    void switchToProfileFragment(User user);

    void showToast(String message);
}
