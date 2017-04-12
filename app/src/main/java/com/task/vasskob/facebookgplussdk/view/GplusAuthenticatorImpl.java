package com.task.vasskob.facebookgplussdk.view;

import android.content.Context;

import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.Authenticator;

public class GplusAuthenticatorImpl implements Authenticator {

    private Context context;

    public GplusAuthenticatorImpl(Context context) {
        this.context = context;
    }

    @Override
    public void doLogin() {

    }

    @Override
    public User getUser() {
        return null;
    }

    @Override
    public void handleResult() {

    }
}
