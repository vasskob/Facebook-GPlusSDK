package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;
import static com.task.vasskob.facebookgplussdk.aplication.Application.RC_SIGN_IN_G;
import static com.task.vasskob.facebookgplussdk.aplication.Application.mGoogleApiClient;

public class GoogleLoginHelper extends LoginHelper {



    private static final String TAG = GoogleLoginHelper.class.getSimpleName();
    private static final String ACCESS_CANCELED = "Login Canceled";

    @Override
    public void init(OnLoginListener listener) {
        mListener = listener;
    }


    @Override
    public void doLogin(OnStartLoginDialogListener callback) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        callback.onGoogleLogin(signInIntent);
    }

    @Override
    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_G) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.d(TAG, "Access granted");
                mListener.onLoginSuccess(GOOGLE);
            } else {
                Log.d(TAG, "Access denied");
                mListener.onLoginError(new RuntimeException(ACCESS_CANCELED));
            }
        }
    }
}
