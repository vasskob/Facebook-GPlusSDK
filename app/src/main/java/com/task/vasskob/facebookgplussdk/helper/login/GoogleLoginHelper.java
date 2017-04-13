package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.task.vasskob.facebookgplussdk.MainActivity;

public class GoogleLoginHelper extends LoginHelper {


    private static final int RC_SIGN_IN_G = 1;
    private final Context context;

    public GoogleLoginHelper(Context context) {
        this.context = context;
    }

    @Override
    void init() {

    }

    @Override
    void doLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(MainActivity.mGoogleApiClient);
       // loginView.startActivityForResult(signInIntent, RC_SIGN_IN_G);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_G) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
         //   handleSignInResult(result, loginView);
        }
    }

     /*
            abstract LoginHelper with methods init(), doLogin(), onActivityResult(), ...

        GoogleLoginHelper(Context) & FacebookLoginHelper(Context) implement this class

        LoginPresenter(LoginHelper google, LoginHelper facebook)

        set UserProfileFragment with social (Facebook, Google)

        in UserProfileFragment get user info from selected social use
        abstract UserProfileHelper with getUser(), logout()

        GoogleUserProfileHelper & FacebookUserProfileHelper extend UserProfileHelper
        and go on/

     */
}
