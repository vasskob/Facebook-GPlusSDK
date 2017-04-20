package com.task.vasskob.facebookgplussdk.presenter.userprofile;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.UserProfileView;

public interface UserProfilePresenter {

    void logout();

    void uploadPhoto(Fragment fragment);

    void showUserData();

    void onDestroy();

    void onUserProfileResult(int requestCode, int resultCode, Intent data);
}
