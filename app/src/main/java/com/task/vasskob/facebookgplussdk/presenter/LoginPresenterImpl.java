package com.task.vasskob.facebookgplussdk.presenter;

import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;

public class LoginPresenterImpl implements LoginPresenter {


    @Override
    public void logIn(LoginHelper loginHelper) {
        loginHelper.init();
        loginHelper.doLogin();
    }

}
