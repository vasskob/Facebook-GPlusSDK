package com.task.vasskob.facebookgplussdk.helper.profile;

import com.task.vasskob.facebookgplussdk.model.User;

public abstract class UserProfileHelper {
         /*
        LoginPresenter(LoginHelper google, LoginHelper facebook)

        set UserProfileFragment with social (Facebook, Google)

        in UserProfileFragment get user info from selected social use
        abstract UserProfileHelper with getUser(), logout()



     */

    abstract User getUser();

    abstract void logout();

}
