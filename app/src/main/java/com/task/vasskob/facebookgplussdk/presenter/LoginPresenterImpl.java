package com.task.vasskob.facebookgplussdk.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.task.vasskob.facebookgplussdk.view.LoginView;

public class LoginPresenterImpl implements LoginPresenter, FacebookCallback<LoginResult> {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName();
    private LoginView mLoginView;
    private Context mContext;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;

    public LoginPresenterImpl(Activity context, LoginView loginView) {
        FacebookSdk.sdkInitialize(context);
        mLoginView = loginView;
        mContext = context;
    }


    // Facebook section

    @Override
    public void onSuccess(LoginResult loginResult) {

    }


    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }





    @Override
    public void onGoogleLoginClick() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data, CallbackManager callbackManager) {

    }
}

