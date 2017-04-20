package com.task.vasskob.facebookgplussdk.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.aplication.Prefs;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.presenter.userprofile.UserProfilePresenterImpl;
import com.task.vasskob.facebookgplussdk.view.UserProfileView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;


public class UserProfileFragment extends Fragment implements UserProfileView {

    private static final String TAG = UserProfileFragment.class.getSimpleName();
    public static final String SOCIAL = "social";
    private static int loginWithSocial;

    OnLogoutClickListener mCallback;

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
        Prefs.with(getActivity()).setLogged(false);
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
        if (loginWithSocial == GOOGLE) {
            Prefs.with(getActivity()).setSocial(false);
        } else if (loginWithSocial == FACEBOOK) {
            Prefs.with(getActivity()).setSocial(true);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.user_profile_layout, container, false);
        ButterKnife.bind(this, rootView);
        Prefs.with(getActivity()).setLogged(true);

        mUserProfilePresenter = new UserProfilePresenterImpl(loginWithSocial, this);
        mUserProfilePresenter.showUserData();

        return rootView;
    }

    @Override
    public void onLogoutSuccess() {
        mCallback.showLoginFragment();
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

    public interface OnLogoutClickListener {
        void showLoginFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (UserProfileFragment.OnLogoutClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLogoutClickListener");
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                mCallback = (UserProfileFragment.OnLogoutClickListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnLogoutClickListener");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

}

