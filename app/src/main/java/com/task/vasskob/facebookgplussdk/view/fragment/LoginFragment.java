package com.task.vasskob.facebookgplussdk.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.FbAuthenticatorImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final String GOOGLE_PLAY_SERVICES_ERROR = "Google Play Services error.";
    private static final int RC_SIGN_IN = 1;

    public static final String USER_BIRTHDAY = "user_birthday";

    public static final String PUBLIC_PROFILE = "public_profile";
    public static final String FIELDS = "fields";
    public static final String NAME_EMAIL_BIRTHDAY = "name,email,birthday";
    public static final String EMAIL = "email";
    public static final String BIRTHDAY = "birthday";
    public static final String PERSON_INFORMATION_IS_NULL = "Person information is null";

    private GoogleApiClient mGoogleApiClient;
    private String uName;
    private String uPhotoUrl;
    private String uBirthday;
    private String uEmail;
    private CallbackManager mCallbackManager;
    private FbAuthenticatorImpl fbAuthenticator;


    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        fbAuthenticator.doLogin();
        User user = fbAuthenticator.getUser();
        openFBUserProfile(user);
        Log.d(TAG, "onFBookSignInClick user name = " + user.getName() + " email= " + user.getEmail());

        // singInFacebook();
    }

    private void openFBUserProfile(User user) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        UserProfileFragment userProfileFragment = UserProfileFragment.newInstance(
                user.getName(), user.getEmail(), user.getBirthday(), user.getUserPhotoUri(), mGoogleApiClient);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, userProfileFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.g_plus_sign_in)
    public void onGPlusSignInClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGPlusSignIn();
        //  initFacebookSignIn();

        fbAuthenticator = new FbAuthenticatorImpl(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }


    // G +
    public void initGPlusSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            // Facebook log in callback
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            getUserProfileInformation();
            openUserProfile();

        } else {
            // Signed out, show unauthenticated UI.
            Log.d(TAG, "handleSignInResult: result is false");
        }
    }

    private void openUserProfile() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        UserProfileFragment userProfileFragment = UserProfileFragment.newInstance(
                uName, uEmail, uBirthday, uPhotoUrl, mGoogleApiClient);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, userProfileFragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    private void getUserProfileInformation() {

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            uName = currentPerson.getDisplayName();
            uPhotoUrl = currentPerson.getImage().getUrl();
            uBirthday = currentPerson.getBirthday();
            uEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

            Log.d(TAG, "getUserProfileInformation: personN= " + uName + " uEmail=" + uEmail +
                    " uBirthday=" + currentPerson.getBirthday() + " uPhotoUrl =" + uPhotoUrl);

        } else {
            Toast.makeText(getActivity(),
                    PERSON_INFORMATION_IS_NULL, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getActivity(), GOOGLE_PLAY_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
    }

    // Facebook
    private void initFacebookSignIn() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    private void singInFacebook() {
        List<String> permissions = Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_BIRTHDAY);
        LoginManager.getInstance().logInWithReadPermissions(this, permissions);
    }

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
                        openUserProfile();
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
    }

    @Override
    public void onError(FacebookException e) {
        Log.e(TAG, "onError:" + e);
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







