package com.task.vasskob.facebookgplussdk.view;

import android.support.v4.app.Fragment;

import com.task.vasskob.facebookgplussdk.model.User;

public interface UserProfileView {

    void onLogoutSuccess();
    void showUserData(User user);
    Fragment getUserProfileFragment();
}
