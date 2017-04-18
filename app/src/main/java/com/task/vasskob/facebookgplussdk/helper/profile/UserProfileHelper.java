package com.task.vasskob.facebookgplussdk.helper.profile;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.task.vasskob.facebookgplussdk.model.User;

import static android.app.Activity.RESULT_OK;

public abstract class UserProfileHelper {

    private static final int PICK_FROM_STORAGE = 4;

    protected static Uri mSelectedImage;

    // TODO: 18/04/17 clear unused methods
    public abstract User getUser();

    // TODO: 18/04/17 make async method to load user, use custom interface for this
//    abstract User getUser(OnUserLoadedListener listener);
    public abstract void logout();

    public abstract void postMedia(String msg);

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FROM_STORAGE:
                if (resultCode == RESULT_OK)
                    mSelectedImage = data.getData();
                break;
            default:
        }
    }

    public void onUploadPhoto(Fragment fragment) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, PICK_FROM_STORAGE);
    }
}
