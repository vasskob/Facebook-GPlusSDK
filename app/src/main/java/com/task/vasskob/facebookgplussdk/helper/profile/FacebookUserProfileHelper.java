package com.task.vasskob.facebookgplussdk.helper.profile;

import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.task.vasskob.facebookgplussdk.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookUserProfileHelper extends UserProfileHelper {

    private OnFacebookDataLoadListener listener;
    private static final String TAG = FacebookUserProfileHelper.class.getSimpleName();
    private static final String NAME_EMAIL_BIRTHDAY = "name,email,birthday";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String USER_BIRTHDAY = "user_birthday";
    private static final String BIRTHDAY = "birthday";
    private static final String EMAIL = "email";
    private static final String FIELDS = "fields";
    private static final String FB_LOGIN_CANCELED = "LogIn canceled";
    private final LoginResult loginResult;
    private String uName;
    private String uEmail;
    private String uBirthday;
    private String uPhotoUrl;
    private User user;


    public FacebookUserProfileHelper(LoginResult loginResult) {
        this.loginResult = loginResult;
    }

    @Override
    public User getUser() {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        final Profile profile = Profile.getCurrentProfile();
                        parseFBResponse(object, profile);
                     //   user = new User(uName, uEmail, uBirthday, uPhotoUrl);
                      //  listener.onCompleted(user);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, NAME_EMAIL_BIRTHDAY);
        request.setParameters(parameters);
        request.executeAsync();

        return user;
    }


    private void parseFBResponse(JSONObject jsonObject, Profile profile) {

        try {
            if (jsonObject != null) {

                uName = profile.getName();
                uEmail = jsonObject.getString(EMAIL);
                uBirthday = jsonObject.getString(BIRTHDAY);
                uPhotoUrl = profile.getProfilePictureUri(200, 200).toString();
                user = new User(uName, uEmail, uBirthday, uPhotoUrl);

                Log.d(TAG, "parseFBResponse: personN= " + uName + " uEmail=" + uEmail +
                        " uBirthday=" + uBirthday + " uPhotoUrl =" + uPhotoUrl);
            }
        } catch (JSONException joe) {
            Log.e(TAG, "parseFBResponse: " + joe);
        }
    }

    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
    }

    public interface OnFacebookDataLoadListener {
        void onCompleted(User user);
    }
}
