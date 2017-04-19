package com.task.vasskob.facebookgplussdk.helper.profile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.task.vasskob.facebookgplussdk.model.User;

import static com.task.vasskob.facebookgplussdk.aplication.Application.mGoogleApiClient;

public class GoogleUserProfileHelper extends UserProfileHelper {

    private final static String TAG = GoogleUserProfileHelper.class.getSimpleName();
    private static final int SHARE_PHOTO_TO_GOOGLE_PLUS = 0;
    private final Fragment mFragment;

    public GoogleUserProfileHelper(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public User getUser() {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            String uName = currentPerson.getDisplayName();
            String uPhotoUrl = currentPerson.getImage().getUrl();
            String uBirthday = currentPerson.getBirthday();
            String uEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

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
    public void logout(OnLogoutListener listener) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Log.d(TAG, "signOutGPlus  onResult:" + status);
                    }
                });
        listener.onLogoutSuccess();
    }

    @Override
    public void postMedia(Uri media) {
        if (media != null) {
            Intent share = new PlusShare.Builder(mFragment.getActivity())
                    .setText(POST_TITLE)
                    .addStream(media)
                    .setContentDeepLinkId("Hello!") //does not work without this
                    .getIntent();
            mFragment.startActivityForResult(share, SHARE_PHOTO_TO_GOOGLE_PLUS);
        }
    }
}
