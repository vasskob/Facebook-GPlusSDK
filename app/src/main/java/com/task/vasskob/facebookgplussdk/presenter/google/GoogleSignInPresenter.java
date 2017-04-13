package com.task.vasskob.facebookgplussdk.presenter.google;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

public interface GoogleSignInPresenter {
    void initGoogleClient(LoginFragment loginView);
    void signIn(LoginFragment LoginFragment);
    void onActivityResult(LoginFragment loginView, int requestCode, int resultCode, Intent data);
 //   void onDestroy();
    void signOutGPlus();
}
