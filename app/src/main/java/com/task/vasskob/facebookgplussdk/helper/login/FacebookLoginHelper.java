package com.task.vasskob.facebookgplussdk.helper.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.task.vasskob.facebookgplussdk.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookLoginHelper extends LoginHelper {

    private static final String TAG = FacebookLoginHelper.class.getSimpleName();
    private CallbackManager callbackManager;
    private final Context context;
    private static final String NAME_EMAIL_BIRTHDAY = "name,email,birthday";
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String USER_BIRTHDAY = "user_birthday";
    private static final String BIRTHDAY = "birthday";
    private static final String EMAIL = "email";
    private static final String FIELDS = "fields";
    private static final String FB_LOGIN_CANCELED = "LogIn canceled";
    private String uName;
    private String uPhotoUrl;
    private String uBirthday;
    private String uEmail;

    public FacebookLoginHelper(Context context) {
        this.context = context;
    }

    @Override
    void init() {

        callbackManager = CallbackManager.Factory.create();

    }

    private void parseFBResponse(JSONObject jsonObject, Profile profile) {

        try {
            if (jsonObject != null) {

                uEmail = jsonObject.getString(EMAIL);
                uBirthday = jsonObject.getString(BIRTHDAY);
                uName = profile.getName();
                uPhotoUrl = profile.getProfilePictureUri(200, 200).toString();

                Log.d(TAG, "parseFBResponse: personN= " + uName + " uEmail=" + uEmail +
                        " uBirthday=" + uBirthday + " uPhotoUrl =" + uPhotoUrl);
            }
        } catch (JSONException joe) {
            Log.e(TAG, "parseFBResponse: " + joe);
        }
    }

    @Override
    void doLogin() {

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess:" + loginResult.getAccessToken());

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                final Profile profile = Profile.getCurrentProfile();
                                parseFBResponse(object, profile);

                                User user = new User(uName, uEmail, uBirthday, uPhotoUrl);

                               // loginFragment.onLoginSuccess(user);

                                Log.d(TAG, "onCompleted:");
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString(FIELDS, NAME_EMAIL_BIRTHDAY);
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

                //loginFragment.showToast(FB_LOGIN_CANCELED);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "Callback onError: " + error);
            }
        });

    }

    @Override
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

}
