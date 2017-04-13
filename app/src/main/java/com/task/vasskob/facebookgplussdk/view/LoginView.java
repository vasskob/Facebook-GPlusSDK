package com.task.vasskob.facebookgplussdk.view;

import com.task.vasskob.facebookgplussdk.model.User;

public interface LoginView {

    void onLoginSuccess(User user);
    void showToast(String message);
}
