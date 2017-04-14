package com.task.vasskob.facebookgplussdk.helper.profile;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.task.vasskob.facebookgplussdk.MainActivity;
import com.task.vasskob.facebookgplussdk.model.User;

public class GoogleUserProfileHelper extends UserProfileHelper {

    private final static String TAG = GoogleUserProfileHelper.class.getSimpleName();

    @Override
    public User getUser() {
        if (Plus.PeopleApi.getCurrentPerson(MainActivity.mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(MainActivity.mGoogleApiClient);
            String uName = currentPerson.getDisplayName();
            String uPhotoUrl = currentPerson.getImage().getUrl();
            String uBirthday = currentPerson.getBirthday();
            String uEmail = Plus.AccountApi.getAccountName(MainActivity.mGoogleApiClient);

            Log.d(TAG, "getUserProfileInformation: personN= " + uName + " uEmail=" + uEmail +
                    " uBirthday=" + currentPerson.getBirthday() + " uPhotoUrl =" + uPhotoUrl);
            return new User(uName, uEmail, uBirthday, uPhotoUrl);

        } else {
          //  loginView.showToast(PERSON_INFORMATION_IS_NULL);
            Log.e(TAG, "getUser  user = null");
            return null;
        }
      }

    @Override
    public void logout() {
        Auth.GoogleSignInApi.signOut(MainActivity.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d(TAG, "signOutGPlus  onResult:" + status);
                    }
                });
    }
}
