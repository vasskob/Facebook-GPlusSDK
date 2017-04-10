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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.task.vasskob.facebookgplussdk.R;
import com.task.vasskob.facebookgplussdk.view.LoginView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends Fragment implements LoginView, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = LoginFragment.class.getSimpleName();
    private static final String GOOGLE_PLAY_SERVICES_ERROR = "Google Play Services error.";
    private static final int RC_SIGN_IN = 1;
    private static final String OBJECT_KEY = "object";
    private GoogleApiClient mGoogleApiClient;
    private String personName;
    private String personPhotoUrl;
    private String birth;
    private String email;


    @OnClick(R.id.facebook_sign_in)
    public void onFBookSignInClick() {
        openProfile();
    }

    @OnClick(R.id.g_plus_sign_in)
    public void onGPlusSignInClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

        //    openProfile();
    }

    private void openProfile() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        UserProfileFragment userProfileFragment = new UserProfileFragment();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, userProfileFragment);
        ft.addToBackStack("Me");
        ft.commit();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initGPlusSignIn();

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

    public void initGPlusSignIn() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            getUserProfileInformation();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            UserProfileFragment userProfileFragment = UserProfileFragment.newInstance(
                    personName, email, birth, personPhotoUrl);
            ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            ft.replace(R.id.fragment_container, userProfileFragment);
            ft.addToBackStack("Me");
            ft.commit();


        } else {
            // Signed out, show unauthenticated UI.

        }
    }


    private void getUserProfileInformation() {

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            personName = currentPerson.getDisplayName();
            personPhotoUrl = currentPerson.getImage().getUrl();
            birth = currentPerson.getBirthday();
            email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            // by default the profile url gives 50x50 px image only
            // we can replace the value with whatever dimension we want by
            // replacing sz=X
//                personPhotoUrl = personPhotoUrl.substring(0,
//                        personPhotoUrl.length() - 2)
//                        + PROFILE_PIC_SIZE;

            //new LoadProfileImage().execute(personPhotoUrl);


        } else {
            Toast.makeText(getActivity(),
                    "Person information is null", Toast.LENGTH_LONG).show();

        }

    }

//    public void initFacebookSignIn() {
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginManager.getInstance().registerCallback(mCallbackManager, getActivity());
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(getActivity(), GOOGLE_PLAY_SERVICES_ERROR, Toast.LENGTH_SHORT).show();
    }


}
