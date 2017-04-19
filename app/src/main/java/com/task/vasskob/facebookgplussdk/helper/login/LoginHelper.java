package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;
import android.support.v4.app.Fragment;

public abstract class LoginHelper {

    // TODO: 18/04/17 don't use direct fragment instance, implement own callback
    protected Fragment mFragment;
    protected OnLoginListener mListener;

    LoginHelper(Fragment view) {
        mFragment = view;
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
