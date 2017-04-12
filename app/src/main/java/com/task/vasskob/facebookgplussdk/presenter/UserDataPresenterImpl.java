package com.task.vasskob.facebookgplussdk.presenter;

import android.app.Activity;
import android.content.Intent;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.task.vasskob.facebookgplussdk.MainActivity;

public class UserDataPresenterImpl implements UserDataPresenter {

    private static final int RC_SIGN_IN = 1;
    private final Activity mContext;
    private final GoogleApiClient mGoogleApiClient;

    public UserDataPresenterImpl(Activity activity, GoogleApiClient googleApiClient) {
        mContext = activity;
        mGoogleApiClient = googleApiClient;
    }

    @Override
    public void onLogoutClick() {
        LoginManager.getInstance().logOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                }
        );
    }

    @Override
    public void getInfoFromSocialNetwork(int id) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
          //  handleSignInResult(result);
        } else {
            // Facebook log in callback
          //  mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }
}
