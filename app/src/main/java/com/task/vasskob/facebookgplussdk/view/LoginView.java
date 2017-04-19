package com.task.vasskob.facebookgplussdk.view;

import android.support.v4.app.Fragment;

public interface LoginView {

    void showLoginCancel();

    void showLoginError(RuntimeException error);

    void postLoginScreen(int loginType);

    Fragment getFragment();
}
