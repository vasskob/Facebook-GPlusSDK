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
import com.task.vasskob.facebookgplussdk.view.LoginView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginView, GoogleApiClient.OnConnectionFailedListener, FacebookCallback<LoginResult> {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final String GOOGLE_PLAY_SERVICES_ERROR = "Google Play Services error.";
    private static final int RC_SIGN_IN = 1;

    public static final String USER_BIRTHDAY = "user_birthday";
    public static final String EMAIL = "email";
    public static final String PUBLIC_PROFILE = "public_profile";

    private GoogleApiClient mGoogleApiClient;
    private String personName;
    private String personPhotoUrl;
    private String birth;
    private String email;
    private CallbackManager mCallbackManager;


    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        singInFacebook();

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
        initFacebookSignIn();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.login_layout, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void loginPassed(int id) {

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
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else {
            // For facebook log in callback
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
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
                personName, email, birth, personPhotoUrl, mGoogleApiClient);
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, userProfileFragment);
        ft.addToBackStack("Me");
        ft.commit();
    }


    private void getUserProfileInformation() {

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            personName = currentPerson.getDisplayName();
            personPhotoUrl = currentPerson.getImage().getUrl();
            birth = currentPerson.getBirthday();
            email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            Log.d(TAG, "getUserProfileInformation: personN= " + personName + " email=" + email +
                    " birth=" + currentPerson.getBirthday() + " personPhotoUrl =" + personPhotoUrl);

        } else {
            Toast.makeText(getActivity(),
                    "Person information is null", Toast.LENGTH_LONG).show();

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
        List<String> permissionNeeds = Arrays.asList(PUBLIC_PROFILE, EMAIL, USER_BIRTHDAY);
        LoginManager.getInstance().logInWithReadPermissions(this, permissionNeeds);
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
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "name,email,birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
    }

    @Override
    public void onError(FacebookException e) {
        Log.d(TAG, "onError:" + e);
    }

    private void parseFBResponse(JSONObject jsonObject, Profile profile) {

        try {
            if (jsonObject != null) {

                email = jsonObject.getString("email");
                birth = jsonObject.getString("birthday");
                personName = profile.getName();
                personPhotoUrl = profile.getProfilePictureUri(200, 200).toString();

                Log.d(TAG, "parseFBResponse: personN= " + personName + " email=" + email +
                        " birth=" + birth + " personPhotoUrl =" + personPhotoUrl);
            }
        } catch (JSONException joe) {
            Log.e(TAG, "parseFBResponse: " + joe);
        }
    }
}







