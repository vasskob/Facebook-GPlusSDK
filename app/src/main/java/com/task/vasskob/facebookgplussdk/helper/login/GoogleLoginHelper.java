package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.task.vasskob.facebookgplussdk.MainActivity;
import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

public class GoogleLoginHelper extends LoginHelper {


    private static final int RC_SIGN_IN_G = 1;
    private static final String TAG = GoogleLoginHelper.class.getSimpleName();

    public GoogleLoginHelper(LoginFragment loginFragment) {
        super(loginFragment);
    }

    @Override
    public void init() {
          // in Application.onCreate
    }

    @Override
    public void doLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(MainActivity.mGoogleApiClient);
        loginFragment.startActivityForResult(signInIntent, RC_SIGN_IN_G);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_G) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                loginFragment.onLoginSuccess(0, null);
                Log.d(TAG, "doLogin");
            } else {
                Log.d(TAG, " Login result is not success !!! ");
            }
        }
    }
}
