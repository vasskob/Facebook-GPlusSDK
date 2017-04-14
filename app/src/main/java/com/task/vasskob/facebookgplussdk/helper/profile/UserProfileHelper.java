package com.task.vasskob.facebookgplussdk.helper.profile;

import android.content.Intent;

import com.task.vasskob.facebookgplussdk.model.User;

public abstract class UserProfileHelper {


    public abstract User getUser();

    public abstract void logout();

    public abstract void postMedia(String msg);

    public abstract void onActivityResult(int requestCode, int resultCode, Intent data);
}
