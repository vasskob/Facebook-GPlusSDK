package com.task.vasskob.facebookgplussdk.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.presenter.login.LoginPresenterImpl;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;
import static com.task.vasskob.facebookgplussdk.aplication.Application.RC_SIGN_IN_G;

public class LoginFragment extends Fragment implements LoginView {

    private static final String LOGIN_CANCELED = "Log In canceled";

    private LoginPresenterImpl mLoginPresenter;
    private OnLoginSuccessListener mCallback;

    @OnClick(R.id.g_plus_sign_in)
    public void onGPlusSignInClick() {
        mLoginPresenter.logIn(GOOGLE);
    }

    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        mLoginPresenter.logIn(FACEBOOK);
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
    public void onLoginSuccess(int loginWithSocial) {
        mCallback.showUserProfileFragment(loginWithSocial);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mLoginPresenter.onLoginResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoginCancel() {
        showToast(LOGIN_CANCELED);
    }

    @Override
    public void showLoginError(RuntimeException error) {
        showToast(error.getMessage());
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGoogleLogin(Intent intent) {
        startActivityForResult(intent, RC_SIGN_IN_G);
    }

    @Override
    public void onFacebookLogin(List<String> permissions) {
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnLoginSuccessListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                mCallback = (OnLoginSuccessListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnHeadlineSelectedListener");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }


    public interface OnLoginSuccessListener {
        void showUserProfileFragment(int loginWithSocial);
    }

}







