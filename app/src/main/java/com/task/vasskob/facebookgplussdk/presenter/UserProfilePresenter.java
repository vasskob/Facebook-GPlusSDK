package com.task.vasskob.facebookgplussdk.presenter;

import android.support.v4.app.Fragment;

import com.task.vasskob.facebookgplussdk.view.UserProfileView;

public interface UserProfilePresenter {


    void logout();

    void uploadPhoto(Fragment fragment);

    void showUserData(UserProfileView view);

}
