package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Intent;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import java.util.Arrays;
import java.util.List;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;

public class FacebookLoginHelper extends LoginHelper {

    private static final String TAG = FacebookLoginHelper.class.getSimpleName();

    private CallbackManager callbackManager;

    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String USER_BIRTHDAY = "user_birthday";
    private static final String EMAIL = "email";
    private static final String FB_LOGIN_CANCELED = "LogIn canceled";


    public FacebookLoginHelper(LoginView view) {
        super(view);
    }

    @Override
    public void init(OnLoginListener listener) {
        callbackManager = CallbackManager.Factory.create();
        mListener = listener;
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess:" + loginResult.getAccessToken());
                mListener.onLoginSuccess(FACEBOOK);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel:" + FB_LOGIN_CANCELED);
                mListener.onLoginCancel();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "Callback onError: " + error);
                mListener.onLoginError(error);
            }
        });
    }

    @Override
    public void doLogin() {
        List<String> permissions = Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_BIRTHDAY);
        LoginManager.getInstance().logInWithReadPermissions(mLoginView.getLoginFragment(), permissions);
    }

    @Override
    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
