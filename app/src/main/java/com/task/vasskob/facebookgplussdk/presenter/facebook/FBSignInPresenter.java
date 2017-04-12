package com.task.vasskob.facebookgplussdk.presenter.facebook;

import android.content.Intent;

public interface FBSignInPresenter {

    void initSignInFB();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void logIn();

    void getUserProfileRequest();



//    void logIn (FBSignIn.GO go);
//    void onDestroy();

}
