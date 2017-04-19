package com.task.vasskob.facebookgplussdk.presenter.userprofile;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.task.vasskob.facebookgplussdk.helper.profile.FacebookUserProfileHelper;
import com.task.vasskob.facebookgplussdk.helper.profile.GoogleUserProfileHelper;
import com.task.vasskob.facebookgplussdk.helper.profile.UserProfileHelper;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.UserProfileView;

import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;

public class UserProfilePresenterImpl implements UserProfilePresenter, UserProfileHelper.OnLogoutListener {

    private static final String TAG = UserProfilePresenterImpl.class.getSimpleName();

    private UserProfileHelper mProfileHelper;
    private UserProfileView mProfileView;

    public UserProfilePresenterImpl(int loginType, UserProfileView view) {
        mProfileView = view;
        switch (loginType) {
            case GOOGLE:
                mProfileHelper = new GoogleUserProfileHelper(mProfileView.getUserProfileFragment());
                break;
            case FACEBOOK:
                mProfileHelper = new FacebookUserProfileHelper(mProfileView.getUserProfileFragment(),
                        new FacebookUserProfileHelper.OnFacebookDataLoadListener() {
                            @Override
                            public void onCompleted(User user) {
                                mProfileView.showUserData(user);
                            }
                        });
                break;
            default:
                throw new RuntimeException("Login Type is not supported" + loginType);
        }
    }

    @Override
    public void logout() {
        if (mProfileHelper != null){
            mProfileHelper.logout(this);
        }
    }

    @Override
    public void uploadPhoto(Fragment fragment) {
        if (mProfileHelper != null) {
            mProfileHelper.uploadPhoto(fragment);
        }
    }

    @Override
    public void showUserData() {
            User user = mProfileHelper.getUser();
            if (user != null && mProfileView !=null) {
                mProfileView.showUserData(mProfileHelper.getUser());
            } else {
                Log.e(TAG, "onCreateView, user == null");
            }
    }

    @Override
    public void onDestroy() {
        mProfileHelper = null;
        mProfileView = null;
    }

    @Override
    public void onUserProfileResult(int requestCode, int resultCode, Intent data) {
        if(mProfileHelper != null){
            mProfileHelper.onUserProfileResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onLogoutSuccess() {
        if (mProfileView != null){
            mProfileView.postLogoutScreen();
        }
    }
}
