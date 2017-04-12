package com.task.vasskob.facebookgplussdk.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.Arrays;
import java.util.List;

public class FbAuthenticatorImpl implements Authenticator {

    private static final String TAG = FbAuthenticatorImpl.class.getSimpleName();
    private Fragment fragment;
    private CallbackManager mCallbackManager;
    private static final String PUBLIC_PROFILE = "public_profile";
    private static final String EMAIL = "email";
    private static final String USER_BIRTHDAY = "user_birthday";
    private static final String BIRTHDAY = "birthday";
    private static final String FIELDS = "fields";
    private static final String NAME_EMAIL_BIRTHDAY = "name,email,birthday";
    private String uEmail;
    private String uBirthday;
    private String uName;
    private String uPhotoUrl;

    public FbAuthenticatorImpl(Fragment fragment) {

        this.fragment = fragment;
        initFbAuthentication();
    }

    @Override
    public void doLogin() {
        List<String> permissions = Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_BIRTHDAY);
        LoginManager.getInstance().logInWithReadPermissions(fragment, permissions);
    }

    @Override
    public User getUser() {
        return new User(uName, uEmail, uBirthday, uPhotoUrl);
    }

    @Override
    public void handleResult() {


    }

    private void initFbAuthentication() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
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
                Log.d(TAG, "LoginResult onCancel:  Login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "LoginResult onError:" + error);
            }
        });
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



}
