package com.task.vasskob.facebookgplussdk.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.task.vasskob.facebookgplussdk.MainActivity;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.helper.profile.FacebookUserProfileHelper;
import com.task.vasskob.facebookgplussdk.helper.profile.GoogleUserProfileHelper;
import com.task.vasskob.facebookgplussdk.helper.profile.UserProfileHelper;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.presenter.userprofile.UserProfilePresenterImpl;
import com.task.vasskob.facebookgplussdk.view.UserProfileView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserProfileFragment extends Fragment implements UserProfileView {

    private static final String TAG = UserProfileFragment.class.getSimpleName();
    public static final String SOCIAL = "social";
    private static int loginWithSocial;

    @Bind(R.id.user_logo)
    RoundedImageView roundedImageView;

    @Bind(R.id.user_name)
    TextView tvUserName;

    @Bind(R.id.user_email)
    TextView tvUserEmail;

    @Bind(R.id.user_birthday)
    TextView tvUserBirthday;
    private UserProfilePresenterImpl mUserProfilePresenter;

    @OnClick(R.id.post_to_social)
    public void postMedia() {
        mUserProfilePresenter.uploadPhoto(this);
    }

    @OnClick(R.id.user_logout)
    public void onLogoutClick() {
        mUserProfilePresenter.logout();
    }

    public static UserProfileFragment newInstance(int social) {
        UserProfileFragment f = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt(SOCIAL, social);
        f.setArguments(args);
        Log.d(TAG, " Logged with 0=google, 1=facebook , = " + social);
        return f;
    }

    @Override
    public void onDestroy() {
        mUserProfilePresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginWithSocial = getArguments().getInt(SOCIAL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_profile_layout, container, false);
        ButterKnife.bind(this, rootView);

        mUserProfilePresenter = new UserProfilePresenterImpl(loginWithSocial, this);
        mUserProfilePresenter.showUserData();

        return rootView;
    }

    @Override
    public void postLogoutScreen() {
        ((MainActivity)getActivity()).showLoginFragment();
    }

    @Override
    public void showUserData(User user) {
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getEmail());
        tvUserBirthday.setText(user.getBirthday());

        Picasso.with(getContext())
                .load(user.getUserPhotoUri())
                .placeholder(R.drawable.user_logo)
                .into(roundedImageView);
    }

    @Override
    public Fragment getUserProfileFragment() {
        return this;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mUserProfilePresenter.onUserProfileResult(requestCode, resultCode, data);
    }
}

