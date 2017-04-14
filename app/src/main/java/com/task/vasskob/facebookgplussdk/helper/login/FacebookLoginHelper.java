package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

import java.util.Arrays;
import java.util.List;

public class FacebookLoginHelper extends LoginHelper {

    private static final String TAG = FacebookLoginHelper.class.getSimpleName();
    private static final int FACEBOOK = 1;
    private CallbackManager callbackManager;

    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String USER_BIRTHDAY = "user_birthday";
    private static final String EMAIL = "email";
    private static final String FB_LOGIN_CANCELED = "LogIn canceled";


    public FacebookLoginHelper(LoginFragment loginFragment) {
        super(loginFragment);
    }


    @Override
    public void init() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess:" + loginResult.getAccessToken());
                loginFragment.onLoginSuccess(FACEBOOK, loginResult);
            }

            @Override
            public void onCancel() {
                loginFragment.showToast(FB_LOGIN_CANCELED);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "Callback onError: " + error);
            }
        });
    }


    @Override
    public void doLogin() {
        List<String> permissions = Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_BIRTHDAY);
        LoginManager.getInstance().logInWithReadPermissions(loginFragment,
                permissions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
