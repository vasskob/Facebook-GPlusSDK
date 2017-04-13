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

import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.presenter.facebook.FBPresenterImpl;
import com.task.vasskob.facebookgplussdk.presenter.google.GooglePresenterImpl;
import com.task.vasskob.facebookgplussdk.presenter.google.GoogleSignInPresenter;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginView {

    private GoogleSignInPresenter googlePresenter;
    private FBPresenterImpl facebookPresenter;

    @OnClick(R.id.g_plus_sign_in)
    public void onGPlusSignInClick() {
        googlePresenter.signIn(this);
    }

    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        facebookPresenter.logIn(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        googlePresenter = new GooglePresenterImpl();
        googlePresenter.initGoogleClient(this);

        facebookPresenter = new FBPresenterImpl();
        facebookPresenter.initSignInFB(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onLoginSuccess(User user) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        UserProfileFragment userProfileFragment = UserProfileFragment.newInstance(
                user.getName(), user.getEmail(), user.getBirthday(), user.getUserPhotoUri());
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
        googlePresenter.onActivityResult(this, requestCode, resultCode, data);
        facebookPresenter.onActivityResult(requestCode, resultCode, data);
    }

    // TODO: 12/04/17
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







