package com.task.vasskob.facebookgplussdk.helper.profile;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.task.vasskob.facebookgplussdk.MainActivity;
import com.task.vasskob.facebookgplussdk.model.User;

public class GoogleUserProfileHelper extends UserProfileHelper {

    private final static String TAG = GoogleUserProfileHelper.class.getSimpleName();

    @Override
    User getUser() {

        return null;
    }

    @Override
    void logout() {
        Auth.GoogleSignInApi.signOut(MainActivity.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d(TAG, "signOutGPlus  onResult:" + status);
                    }
                });
    }
}
