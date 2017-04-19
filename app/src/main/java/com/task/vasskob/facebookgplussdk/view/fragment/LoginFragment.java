package com.task.vasskob.facebookgplussdk.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.task.vasskob.facebookgplussdk.MainActivity;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.presenter.login.LoginPresenterImpl;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;

public class LoginFragment extends Fragment implements LoginView {

    private static final String FB_LOGIN_CANCELED = "LogIn canceled";

    private LoginPresenterImpl mLoginPresenter;

    @OnClick(R.id.g_plus_sign_in)
    public void onGPlusSignInClick() {
        mLoginPresenter.logIn(GOOGLE);
    }

    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        mLoginPresenter.logIn(FACEBOOK);
    }

    @Override
    public void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void showLoginCancel() {
        showToast(FB_LOGIN_CANCELED);
    }

    @Override
    public void showLoginError(RuntimeException error) {
        showToast(error.getMessage());
    }

    @Override
    public void postLoginScreen(int loginType) {
        ((MainActivity) getActivity()).showUserProfileFragment(loginType);
    }

    @Override
    public Fragment getLoginFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginPresenter.onLoginResult(requestCode, resultCode, data);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }
}







