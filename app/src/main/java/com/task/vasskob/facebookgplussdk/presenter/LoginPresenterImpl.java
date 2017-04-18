package com.task.vasskob.facebookgplussdk.presenter;

import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;

public class LoginPresenterImpl implements LoginPresenter {
    // TODO: 18/04/17 implement login helper listener in presenter, and presenter connect with view

    @Override
    public void logIn(LoginHelper loginHelper) {
        loginHelper.init();
        loginHelper.doLogin();
    }

}
