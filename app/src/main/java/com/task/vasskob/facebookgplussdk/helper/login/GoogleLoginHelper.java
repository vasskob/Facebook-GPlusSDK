package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;
import static com.task.vasskob.facebookgplussdk.aplication.Application.mGoogleApiClient;

public class GoogleLoginHelper extends LoginHelper {


    private static final int RC_SIGN_IN_G = 1;
    private static final String TAG = GoogleLoginHelper.class.getSimpleName();

    public GoogleLoginHelper(Fragment view) {
        super(view);
    }

    @Override
    public void init(OnLoginListener listener) {
        mListener = listener;
    }

    ;

    @Override
    public void doLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mFragment.startActivityForResult(signInIntent, RC_SIGN_IN_G);
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
                mListener.onLoginError(new RuntimeException("Access Denied"));
            }
        }
    }
}
