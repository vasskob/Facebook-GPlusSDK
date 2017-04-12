package com.task.vasskob.facebookgplussdk.presenter;

import android.content.Intent;

public interface UserDataPresenter {
    void onLogoutClick();

    void getInfoFromSocialNetwork(int id);

    void onActivityResult(int requestCode, int resultCode, Intent data);
}
