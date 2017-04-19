package com.task.vasskob.facebookgplussdk.presenter;

import android.support.v4.app.Fragment;
import android.util.Log;

import com.task.vasskob.facebookgplussdk.helper.profile.UserProfileHelper;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.UserProfileView;

public class UserProfilePresenterImpl implements UserProfilePresenter {

    private static final String TAG = UserProfilePresenterImpl.class.getSimpleName();

    private final UserProfileHelper uph;

    public UserProfilePresenterImpl(UserProfileHelper uph) {
        this.uph = uph;
    }

    @Override
    public void logout() {
        uph.logout();
    }

    @Override
    public void uploadPhoto(Fragment fragment) {
        uph.uploadPhoto(fragment);
    }

    @Override
    public void showUserData(UserProfileView view) {
        User user = uph.getUser();
        if (user != null) {
            view.showUserData(uph.getUser());
        } else {
            Log.e(TAG, "onCreateView, user == null");
        }
    }
}
