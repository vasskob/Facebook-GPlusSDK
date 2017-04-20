package com.task.vasskob.facebookgplussdk.presenter.login;

import android.content.Intent;
import android.util.Log;

import com.task.vasskob.facebookgplussdk.helper.login.FacebookLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.GoogleLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import java.util.List;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;

public class LoginPresenterImpl implements LoginPresenter, LoginHelper.OnLoginListener {

    private static final String TAG = LoginPresenterImpl.class.getSimpleName();
    private static final String LOGIN_TYPE_WARN = "Login Type is not supported";

    private LoginView mLoginView;
    private LoginHelper mLoginHelper;

    public LoginPresenterImpl(LoginView view) {
        mLoginView = view;
    }

    @Override
    public void logIn(int loginType) {
        switch (loginType) {
            case GOOGLE:
                mLoginHelper = new GoogleLoginHelper();
                break;
            case FACEBOOK:
                mLoginHelper = new FacebookLoginHelper();
                break;
            default:
                throw new RuntimeException(LOGIN_TYPE_WARN + loginType);
        }
        mLoginHelper.init(this);
        mLoginHelper.doLogin(new LoginHelper.OnStartLoginDialogListener() {
            @Override
            public void onGoogleLogin(Intent intent) {
                mLoginView.onGoogleLogin(intent);
            }

            @Override
            public void onFacebookLogin(List<String> permissions) {
                mLoginView.onFacebookLogin(permissions);
            }
        });
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
        mLoginHelper = null;
    }

    @Override
    public void onLoginResult(int requestCode, int resultCode, Intent data) {
        if (mLoginHelper != null) {
            mLoginHelper.onLoginResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onLoginError(RuntimeException error) {
        if (mLoginView != null) {
            mLoginView.showLoginError(error);
            Log.d(TAG, " onLoginError ");
        }
    }

    @Override
    public void onLoginCancel() {
        if (mLoginView != null) {
            mLoginView.showLoginCancel();
            Log.d(TAG, " onLoginCancel ");
        }
    }

    @Override
    public void onLoginSuccess(int loginType) {
        if (mLoginView != null) {
            mLoginView.onLoginSuccess(loginType);
        }
    }
}
