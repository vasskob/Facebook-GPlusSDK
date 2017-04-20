package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;

import java.util.List;

public abstract class LoginHelper {

    OnLoginListener mListener;

    public abstract void init(OnLoginListener listener);

    public abstract void doLogin(OnStartLoginDialogListener callback);

    public abstract void onLoginResult(int requestCode, int resultCode, Intent data);

    public interface OnLoginListener {

        void onLoginError(RuntimeException error);
        void onLoginCancel();
        void onLoginSuccess(int loginWithSocial);
    }

    public interface OnStartLoginDialogListener {

        void onGoogleLogin(Intent intent);
        void onFacebookLogin(List<String> list);
    }
}
