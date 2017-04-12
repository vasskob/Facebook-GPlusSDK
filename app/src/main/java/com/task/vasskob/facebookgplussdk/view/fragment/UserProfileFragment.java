package com.task.vasskob.facebookgplussdk.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.task.vasskob.facebookgplussdk.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment {

    private static String userName;
    private static String userEmail;
    private static String userBirthday;
    private static String userLogo;
    private static GoogleApiClient mGoogleApiClient;

    private static final String TAG = UserProfileFragment.class.getSimpleName();

    @Bind(R.id.user_logo)
    RoundedImageView roundedImageView;

    @Bind(R.id.user_name)
    TextView tvUserName;

    @Bind(R.id.user_email)
    TextView tvUserEmail;

    @Bind(R.id.user_birthday)
    TextView tvUserBirthday;

    @OnClick(R.id.user_logout)
    public void onLogoutClick() {
        if (mGoogleApiClient != null) {
            signOutGPlus();
        }
        signOutFacebook();
        getFragmentManager().popBackStack();
    }

    private void signOutFacebook() {
        LoginManager.getInstance().logOut();
    }

    private void signOutGPlus() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d(TAG, "signOutGPlus  onResult:" + status);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_profile_layout, container, false);
        ButterKnife.bind(this, rootView);

        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);
        tvUserBirthday.setText(userBirthday);

        Picasso.with(getContext())
                .load(userLogo)
                .placeholder(R.drawable.user_logo)
                .into(roundedImageView);

        return rootView;
    }

    // TODO: 12/04/17 newInstance is on the same abstraction level as constructor, should be on the top of the page
    public static UserProfileFragment newInstance(String uName, String uEmail, String uBirthday, String uLogo, GoogleApiClient googleApiClient) {
        UserProfileFragment f = new UserProfileFragment();
        userName = uName;
        userEmail = uEmail;
        userBirthday = uBirthday;
        userLogo = uLogo;
        mGoogleApiClient = googleApiClient;
        return f;
    }
}
