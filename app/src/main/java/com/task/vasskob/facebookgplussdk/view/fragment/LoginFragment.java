package com.task.vasskob.facebookgplussdk.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.LoginResult;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.helper.login.FacebookLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.GoogleLoginHelper;
import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;
import com.task.vasskob.facebookgplussdk.presenter.LoginPresenterImpl;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginView {

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
    public void onLoginSuccess(int loginWithSocial, LoginResult loginResult) {
        // TODO: 18/04/17 fragment-fragment communication only through activity
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        UserProfileFragment userProfileFragment = UserProfileFragment.newInstance(loginWithSocial, loginResult);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, userProfileFragment);
        ft.commit();
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
}







