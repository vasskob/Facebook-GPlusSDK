package com.task.vasskob.facebookgplussdk.view;

import com.facebook.login.LoginResult;

public interface LoginView {

    void onLoginSuccess(int loginWithSocial, LoginResult loginResult);
    void showToast(String message);
}
