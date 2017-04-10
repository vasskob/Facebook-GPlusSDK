package com.task.vasskob.facebookgplussdk.presenter;


import android.content.Intent;

import com.facebook.CallbackManager;

public interface LoginPresenter {

    void onGoogleLoginClick();
 //   void initGPlusSignIn();

  //  void initFacebookSignIn();
    void onActivityResult(int requestCode, int resultCode, Intent data, CallbackManager callbackManager);

}
