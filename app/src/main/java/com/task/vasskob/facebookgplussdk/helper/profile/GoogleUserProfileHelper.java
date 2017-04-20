package com.task.vasskob.facebookgplussdk.helper.profile;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

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
    private static final String PERSON_WARN = "User can`t be loaded";
    private final Fragment mFragment;

    public GoogleUserProfileHelper(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void loadUserProfile(OnUserLoadedListener loadedListener) {
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            String uName = currentPerson.getDisplayName();
            String uPhotoUrl = currentPerson.getImage().getUrl();
            String uBirthday = currentPerson.getBirthday();
            String uEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

            Log.d(TAG, "loadUserProfile: userName = " + uName + " uEmail=" + uEmail +
                    " uBirthday=" + currentPerson.getBirthday() + " uPhotoUrl =" + uPhotoUrl);

            User user = new User(uName, uEmail, uBirthday, uPhotoUrl);
            loadedListener.onLoadSuccess(user);
        } else {
            Log.e(TAG, "getUser  user = null");
            Toast.makeText(mFragment.getActivity(), PERSON_WARN, Toast.LENGTH_LONG).show();
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
