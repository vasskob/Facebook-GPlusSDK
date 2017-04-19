package com.task.vasskob.facebookgplussdk.presenter.login;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.helper.login.FacebookLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.GoogleLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;

public class LoginPresenterImpl implements LoginPresenter, LoginHelper.OnLoginListener {
    // TODO: 18/04/17 implement login helper listener in presenter, and presenter connect with view

    private static final String TAG = LoginPresenterImpl.class.getSimpleName();

    LoginView mLoginView;
    LoginHelper mLoginHelper;

    public LoginPresenterImpl(LoginView view) {
        mLoginView = view;
    }

    @Override
    public void logIn(int loginType) {
        switch (loginType) {
            case GOOGLE:
                mLoginHelper = new GoogleLoginHelper(mLoginView);
                break;
            case FACEBOOK:
                mLoginHelper = new FacebookLoginHelper(mLoginView);
                break;
            default:
                throw new RuntimeException("Login Type is not supported" + loginType);
        }
        mLoginHelper.init(this);
        mLoginHelper.doLogin();
    }

    @Override
    public void onAttach(LoginView view) {
        mLoginView = view;
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
        }
    }

    @Override
    public void onLoginCancel() {
        if (mLoginView != null) {
            mLoginView.showLoginCancel();
        }
    }

    @Override
    public void onLoginSuccess(int loginType) {
        if (mLoginView != null) {
            mLoginView.postLoginScreen(loginType);
        }
    }
}
