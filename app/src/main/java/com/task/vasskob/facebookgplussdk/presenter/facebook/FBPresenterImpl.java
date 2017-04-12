package com.task.vasskob.facebookgplussdk.presenter.facebook;

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
import com.task.vasskob.facebookgplussdk.presenter.google.GooglePresenterImpl;
import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class FBPresenterImpl implements FBSignInPresenter {

    private static final String TAG = GooglePresenterImpl.class.getSimpleName();
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

    private CallbackManager callbackManager;

    @Override
    public void initSignInFB(final LoginFragment loginFragment) {
        callbackManager = CallbackManager.Factory.create();
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
                                loginFragment.switchToProfileFragment(user, null);

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
                loginFragment.showToast(FB_LOGIN_CANCELED);
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "Callback onError: " + error);
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

    @Override
    public void logIn(final LoginFragment loginFragment) {
        List<String> permissions = Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_BIRTHDAY);
        LoginManager.getInstance().logInWithReadPermissions(loginFragment,
                permissions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (FacebookSdk.isFacebookRequestCode(requestCode)) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}
