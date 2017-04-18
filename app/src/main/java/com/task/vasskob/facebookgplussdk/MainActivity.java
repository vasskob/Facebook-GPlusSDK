package com.task.vasskob.facebookgplussdk;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.Manifest.permission.GET_ACCOUNTS;

public class MainActivity extends AppCompatActivity {

    private static final String LOGIN_FRAGMENT = "loginFragment";
    private static final int PERMISSION_REQUEST_CODE = 200;
    public LoginFragment loginFragment;
    private Bundle savedState;
    // TODO: 18/04/17 if GoogleApiClient is global for entire application put it into application class
    public static GoogleApiClient mGoogleApiClient;
    @Bind(R.id.fragment_container)
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        savedState = savedInstanceState;

        Log.d("d", " permission granted" + checkPermission());
        if (checkPermission()) {
            requestPermission();
        } else {
            initFragment(savedInstanceState);
        }
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

    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction fm = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            loginFragment = (LoginFragment) getSupportFragmentManager().
                    getFragment(savedInstanceState, LOGIN_FRAGMENT);
        } else {
            loginFragment = new LoginFragment();
        }
        fm.replace(R.id.fragment_container, loginFragment);
        fm.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, LOGIN_FRAGMENT, loginFragment);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean dataReadAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (dataReadAccepted) {
                        Snackbar.make(frameLayout, R.string.permission_granted, Snackbar.LENGTH_LONG).show();
                        initFragment(savedState);
                    } else {
                        Snackbar.make(frameLayout, R.string.permission_denied, Snackbar.LENGTH_LONG).show();
                        if (checkPermission()) {
                            requestPermission();
                        }
                    }
                }
                break;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);
        return result != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{GET_ACCOUNTS}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
