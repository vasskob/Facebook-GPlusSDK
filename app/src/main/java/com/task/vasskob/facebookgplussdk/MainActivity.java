package com.task.vasskob.facebookgplussdk;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.task.vasskob.facebookgplussdk.view.fragment.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOGIN_FRAGMENT = "loginFragment";
private LoginFragment loginFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
