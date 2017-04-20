package com.task.vasskob.facebookgplussdk.aplication;

import android.content.Context;
import android.content.SharedPreferences;

import static com.task.vasskob.facebookgplussdk.view.fragment.UserProfileFragment.SOCIAL;

public class Prefs {

    private static final String LOGGED = "logged";
    private static final String PREFS_NAME = "prefs";
    private static Prefs instance;
    private final SharedPreferences sharedPreferences;

    public Prefs(Context context) {

        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs with(Context context) {

        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public void setLogged(boolean logged) {

        sharedPreferences
                .edit()
                .putBoolean(LOGGED, logged)
                .apply();
    }
    public boolean getLogged(){
        return sharedPreferences.getBoolean(LOGGED, false);
    }

    public void setSocial(boolean social) {
        sharedPreferences
                .edit()
                .putBoolean(SOCIAL, social)
                .apply();
    }
    public boolean getSocial(){
        return sharedPreferences.getBoolean(SOCIAL, false);
    }

}