package com.task.vasskob.facebookgplussdk;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.task.vasskob.facebookgplussdk.aplication.Prefs;
import com.task.vasskob.facebookgplussdk.listener.ErrorListener;
import com.task.vasskob.facebookgplussdk.listener.MultiplePermissionListener;
import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;
import com.task.vasskob.facebookgplussdk.view.fragment.UserProfileFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static com.task.vasskob.facebookgplussdk.aplication.Application.FACEBOOK;
import static com.task.vasskob.facebookgplussdk.aplication.Application.GOOGLE;
import static com.task.vasskob.facebookgplussdk.aplication.Application.mGoogleApiClient;

public class MainActivity extends AppCompatActivity implements UserProfileFragment.OnLogoutClickListener, LoginFragment.OnLoginSuccessListener {

    private static final String LOGIN_FRAGMENT = "loginFragment";
    public LoginFragment loginFragment;
    private Bundle savedState;


    @Bind(R.id.fragment_container)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!checkAllPermissions()) {
            createPermissionListeners();
        }
        initFragment(savedInstanceState);
        savedState = savedInstanceState;
    }

    private void createPermissionListeners() {
        ErrorListener errorListener = new ErrorListener();
        MultiplePermissionsListener permissionListener = new MultiplePermissionListener(this);

        MultiplePermissionsListener allPermissionsListener = new CompositeMultiplePermissionsListener(permissionListener,
                SnackbarOnAnyDeniedMultiplePermissionsListener.Builder.with(frameLayout,
                        R.string.all_permissions_denied_feedback)
                        .withOpenSettingsButton(R.string.permission_rationale_settings_button_text)
                        .build());

        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.GET_ACCOUNTS,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(allPermissionsListener)
                .withErrorListener(errorListener)
                .check();
    }

    private void initFragment(Bundle savedInstanceState) {
        if (checkAllPermissions()) {

            if (savedInstanceState != null) {
                loginFragment = (LoginFragment) getSupportFragmentManager().
                        getFragment(savedInstanceState, LOGIN_FRAGMENT);
            } else {
                loginFragment = new LoginFragment();
            }


            if (!Prefs.with(this).getLogged()) {
                replaceFragmentWith(loginFragment);
            } else {
                if (Prefs.with(this).getSocial()) {
                    showUserProfileFragment(FACEBOOK);
                } else {
                   showUserProfileFragment(GOOGLE);
                }
            }
        }
    }

    private void replaceFragmentWith(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect(GoogleApiClient.SIGN_IN_MODE_OPTIONAL);
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, LOGIN_FRAGMENT, loginFragment);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (!Prefs.with(this).getLogged()) {
            initFragment(savedState);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void showUserProfileFragment(int loginWithSocial) {
        UserProfileFragment upf = UserProfileFragment.newInstance(loginWithSocial);
        replaceFragmentWith(upf);
    }

    @Override
    public void showLoginFragment() {
        replaceFragmentWith(loginFragment);
    }


    public void showPermissionGranted() {
        Snackbar.make(frameLayout, R.string.permission_granted, Snackbar.LENGTH_LONG).show();
        initFragment(savedState);
    }

    private boolean checkAllPermissions() {
        int resultAccount = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);
        int resultReadData = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return resultAccount == PackageManager.PERMISSION_GRANTED && resultReadData == PackageManager.PERMISSION_GRANTED;
    }

}
