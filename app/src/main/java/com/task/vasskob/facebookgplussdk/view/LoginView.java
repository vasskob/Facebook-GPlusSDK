package com.task.vasskob.facebookgplussdk.view;

import android.content.Intent;

import java.util.List;

public interface LoginView {

    void showLoginCancel();

    void showLoginError(RuntimeException error);

    void onLoginSuccess(int loginType);

    void onGoogleLogin(Intent intent);

    void onFacebookLogin(List<String> list);

}
