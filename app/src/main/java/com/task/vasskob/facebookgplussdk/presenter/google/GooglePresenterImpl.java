package com.task.vasskob.facebookgplussdk.presenter.google;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

public class GooglePresenterImpl implements GoogleSignInPresenter {


    private static final int RC_SIGN_IN_G = 1;
    private static final String TAG = GooglePresenterImpl.class.getSimpleName();
    private static final String CONNECTION_WARN = "Connection Failed";
    private static final String PERSON_INFORMATION_IS_NULL = "Person information is null";


    private GoogleApiClient mGoogleApiClient;
    private String uName;
    private String uPhotoUrl;
    private String uBirthday;
    private String uEmail;

    @Override
    public void initGoogleClient(final LoginFragment loginView) {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(loginView.getActivity())
                .enableAutoManage(loginView.getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        loginView.showToast(CONNECTION_WARN);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();
    }

    @Override
    public void signIn(LoginFragment loginView) {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        loginView.startActivityForResult(signInIntent, RC_SIGN_IN_G);
    }

    @Override
    public void onActivityResult(LoginFragment loginView, int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_G) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result, loginView);
        }
    }

    private void handleSignInResult(GoogleSignInResult result, LoginFragment loginView) {
        if (result.isSuccess()) {
            getUserProfileInformation(loginView);
            User user = new User(uName, uEmail, uBirthday, uPhotoUrl);
            loginView.switchToProfileFragment(user, mGoogleApiClient);
        } else {
            Log.d(TAG, " Login result is not success !!! ");
        }
    }

    private void getUserProfileInformation(LoginFragment loginView) {
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
            loginView.showToast(PERSON_INFORMATION_IS_NULL);
        }
    }
}
