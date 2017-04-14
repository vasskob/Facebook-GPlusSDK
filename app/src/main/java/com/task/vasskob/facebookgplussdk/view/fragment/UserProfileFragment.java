package com.task.vasskob.facebookgplussdk.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginResult;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.helper.profile.FacebookUserProfileHelper;
import com.task.vasskob.facebookgplussdk.helper.profile.GoogleUserProfileHelper;
import com.task.vasskob.facebookgplussdk.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment {

    private static final int GOOGLE = 0;
    private static final int FACEBOOK = 1;

    private static final String TAG = UserProfileFragment.class.getSimpleName();
    public static final String NEW_PHOTO = "New photo";
    private static int loginWithSocial;
    private static LoginResult loginResult;
    private FacebookUserProfileHelper facebookUserProfileHelper;
    private GoogleUserProfileHelper googleUserProfileHelper;
    private User user;

    @Bind(R.id.user_logo)
    RoundedImageView roundedImageView;

    @Bind(R.id.user_name)
    TextView tvUserName;

    @Bind(R.id.user_email)
    TextView tvUserEmail;

    @Bind(R.id.user_birthday)
    TextView tvUserBirthday;

    @OnClick(R.id.user_birthday)
    public void pickMedia(){
        if (loginWithSocial == GOOGLE) {
            //googleUserProfileHelper.;
        } else if (loginWithSocial == FACEBOOK) {
            facebookUserProfileHelper.onUploadPhoto();
        }
    }

    @OnClick(R.id.user_logo)
    public void postMedia(){
        if (loginWithSocial == GOOGLE) {
            googleUserProfileHelper.postMedia(NEW_PHOTO);
        } else if (loginWithSocial == FACEBOOK) {
            facebookUserProfileHelper.postMedia(NEW_PHOTO);
        }
    }

    @OnClick(R.id.user_logout)
    public void onLogoutClick() {

        if (loginWithSocial == GOOGLE) {
            googleUserProfileHelper.logout();
        } else if (loginWithSocial == FACEBOOK) {
            facebookUserProfileHelper.logout();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, new LoginFragment());
        ft.commit();
    }

    public static UserProfileFragment newInstance(int social, LoginResult result) {
        UserProfileFragment f = new UserProfileFragment();
        loginWithSocial = social;
        loginResult=result;
        Log.d(TAG, " Logged with 0=google, 1=facebook , = " + social);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_profile_layout, container, false);
        ButterKnife.bind(this, rootView);


        if (loginWithSocial == GOOGLE) {
            googleUserProfileHelper = new GoogleUserProfileHelper();
            user = googleUserProfileHelper.getUser();
        } else if (loginWithSocial == FACEBOOK) {
            facebookUserProfileHelper = new FacebookUserProfileHelper(this, loginResult,
                    new FacebookUserProfileHelper.OnFacebookDataLoadListener() {
                @Override
                public void onCompleted(User user) {
                showUserData(user);
                }
            });

             user = facebookUserProfileHelper.getUser();
        }
        if (user != null) {
            showUserData(user);
        } else {
            Log.e(TAG, " user=null");
        }

        return rootView;
    }

    private void showUserData(User user) {
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
        tvUserBirthday.setText(user.getBirthday());

        Picasso.with(getContext())
                .load(user.getUserPhotoUri())
                .placeholder(R.drawable.user_logo)
                .into(roundedImageView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookUserProfileHelper.onActivityResult(requestCode, resultCode, data);
    }

}
