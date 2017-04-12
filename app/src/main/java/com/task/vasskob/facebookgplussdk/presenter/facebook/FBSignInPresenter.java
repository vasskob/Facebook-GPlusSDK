package com.task.vasskob.facebookgplussdk.presenter.facebook;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

public interface FBSignInPresenter {

    void initSignInFB(LoginFragment fragment);
    void logIn(LoginFragment fragment);
    void onActivityResult(int requestCode, int resultCode, Intent data);
}
