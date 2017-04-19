package com.task.vasskob.facebookgplussdk.presenter.login;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.view.LoginView;

interface LoginPresenter {

    void logIn(int loginType);

    void onAttach(LoginView view);

    void onDestroy();

    void onLoginResult(int requestCode, int resultCode, Intent data);
}
