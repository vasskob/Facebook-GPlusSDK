package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

public abstract class LoginHelper {


    final LoginFragment loginFragment;

    LoginHelper(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public abstract void init();

    public abstract void doLogin();

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
