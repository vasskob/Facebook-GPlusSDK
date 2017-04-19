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

import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.helper.login.FacebookLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.GoogleLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;
import com.task.vasskob.facebookgplussdk.presenter.LoginPresenterImpl;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginView {
    OnLoginSuccessListener mCallback;
    private LoginHelper googleLoginHelper;
    private LoginHelper facebookLoginHelper;
    private LoginPresenterImpl loginPresenter;

    @OnClick(R.id.g_plus_sign_in)
    public void onGPlusSignInClick() {
        loginPresenter.logIn(googleLoginHelper);
    }

    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        loginPresenter.logIn(facebookLoginHelper);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleLoginHelper = new GoogleLoginHelper(this);
        facebookLoginHelper = new FacebookLoginHelper(this);
        loginPresenter = new LoginPresenterImpl();
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
        // TODO: 18/04/17 fragment-fragment communication only through activity
        mCallback.showUserProfile(loginWithSocial);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        googleLoginHelper.onActivityResult(requestCode, resultCode, data);
        facebookLoginHelper.onActivityResult(requestCode, resultCode, data);
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
        mCallback=null;
    }

    public interface OnLoginSuccessListener {
        void showUserProfile(int loginWithSocial);
    }
}







