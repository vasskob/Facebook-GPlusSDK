package com.task.vasskob.facebookgplussdk.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.presenter.google.GooglePresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserProfileFragment extends Fragment {

    private static String userName;
    private static String userEmail;
    private static String userBirthday;
    private static String userLogo;

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

        GooglePresenterImpl googlePresenter = new GooglePresenterImpl();
        googlePresenter.signOutGPlus();
        signOutFacebook();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, new LoginFragment());
        ft.commit();
    }

    public static UserProfileFragment newInstance(String uName, String uEmail, String uBirthday, String uLogo) {
        UserProfileFragment f = new UserProfileFragment();
        userName = uName;
        userEmail = uEmail;
        userBirthday = uBirthday;
        userLogo = uLogo;
        return f;
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

    private void signOutFacebook() {
        LoginManager.getInstance().logOut();
    }

}
