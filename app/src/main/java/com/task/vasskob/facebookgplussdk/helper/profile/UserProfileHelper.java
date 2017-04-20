package com.task.vasskob.facebookgplussdk.helper.profile;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.task.vasskob.facebookgplussdk.model.User;

import static android.app.Activity.RESULT_OK;

public abstract class UserProfileHelper {

    private static final int PICK_FROM_STORAGE = 4;
    static final String POST_TITLE = "New photo";

    public abstract void loadUserProfile(OnUserLoadedListener listener);

    public abstract void logout(OnLogoutListener listener);

    public abstract void postMedia(Uri media);

    public void onUserProfileResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FROM_STORAGE:
                if (resultCode == RESULT_OK)
                    postMedia(data.getData());
                break;
            default:
                break;
        }
    }

    public void uploadPhoto(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, PICK_FROM_STORAGE);
    }

    public interface OnLogoutListener {
        void onLogoutSuccess();
    }

    public interface OnUserLoadedListener {
        void onLoadSuccess(User user);
    }
}
