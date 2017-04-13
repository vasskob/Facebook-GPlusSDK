package com.task.vasskob.facebookgplussdk.presenter;

import com.task.vasskob.facebookgplussdk.helper.login.LoginHelper;

public class LoginPresenterImpl implements LoginPresenter {

    private final LoginHelper googleLoginHelper, facebookLoginHelper;

    public LoginPresenterImpl(LoginHelper google, LoginHelper facebook) {
        googleLoginHelper = google;
        facebookLoginHelper = facebook;
    }


     /*

        set UserProfileFragment with social (Facebook, Google)

        in UserProfileFragment get user info from selected social use

        abstract UserProfileHelper with getUser(), logout()

        GoogleUserProfileHelper & FacebookUserProfileHelper extend UserProfileHelper
        and go on/

     */
}
