package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.view.LoginView;

public abstract class LoginHelper {

    // TODO: 18/04/17 don't use direct fragment instance, implement own callback
    protected LoginView mLoginView;
    protected OnLoginListener mListener;

    LoginHelper(LoginView view) {
        mLoginView = view;
    }

    public abstract void init(OnLoginListener listener);

    public abstract void doLogin();

    public abstract void onLoginResult(int requestCode, int resultCode, Intent data);

    public interface OnLoginListener {

        void onLoginError(RuntimeException error);

        void onLoginCancel();

        void onLoginSuccess(int loginWithSocial);
    }
}
