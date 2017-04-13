package com.task.vasskob.facebookgplussdk;

public abstract class LoginHelper {

    /*
        abstract LoginHelper with methods init(), doLogin(), onActivityResult(), ...
        GoogleLoginHelper(Context) & FacebookLoginHelper(Context) implement this class
        LoginPresenter(LoginHelper google, LoginHelper facebook)

        set UserProfileFragment with social (Facebook, Google)

        in UserProfileFragment get user info from selected social use
        abstract UserProfileHelper with getUser(), logout()

        GoogleUserProfileHelper & FacebookUserProfileHelper extend UserProfileHelper
        and go on/

     */

    abstract void init();
    abstract void doLogin();
    abstract void onActivityResult();

}
