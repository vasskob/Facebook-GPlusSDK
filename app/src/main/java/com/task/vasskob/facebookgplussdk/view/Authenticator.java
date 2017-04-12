package com.task.vasskob.facebookgplussdk.view;

import com.task.vasskob.facebookgplussdk.model.User;

public interface Authenticator {

    void doLogin();

    User getUser();

    void handleResult();

}
