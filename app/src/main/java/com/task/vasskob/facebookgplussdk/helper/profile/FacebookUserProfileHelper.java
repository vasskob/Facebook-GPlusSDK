package com.task.vasskob.facebookgplussdk.helper.profile;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.fragment.UserProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookUserProfileHelper extends UserProfileHelper {

    private static final String TAG = FacebookUserProfileHelper.class.getSimpleName();
    private static final String PUBLISH_ACTIONS = "publish_actions";
    private static final String NAME_EMAIL_BIRTHDAY = "name,email,birthday";
    private static final String BIRTHDAY = "birthday";
    private static final String EMAIL = "email";
    private static final String FIELDS = "fields";
    private String uName;
    private String uEmail;
    private String uBirthday;
    private String uPhotoUrl;
    private User user;
    private final LoginResult loginResult;
    private final UserProfileFragment fragment;
    private OnFacebookDataLoadListener listener;


    public FacebookUserProfileHelper(UserProfileFragment fragment, LoginResult loginResult, OnFacebookDataLoadListener listener) {
        this.loginResult = loginResult;
        this.fragment = fragment;
        this.listener = listener;
    }

    @Override
    public User getUser() {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        final Profile profile = Profile.getCurrentProfile();
                        parseFBResponse(object, profile);
                        user = new User(uName, uEmail, uBirthday, uPhotoUrl);
                        listener.onCompleted(user);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, NAME_EMAIL_BIRTHDAY);
        request.setParameters(parameters);
        request.executeAsync();
        return user;
    }


    private void parseFBResponse(JSONObject jsonObject, Profile profile) {

        try {
            if (jsonObject != null) {

                uName = profile.getName();
                uEmail = jsonObject.getString(EMAIL);
                uBirthday = jsonObject.getString(BIRTHDAY);
                uPhotoUrl = profile.getProfilePictureUri(200, 200).toString();
                Log.d(TAG, "parseFBResponse: personN= " + uName + " uEmail=" + uEmail +
                        " uBirthday=" + uBirthday + " uPhotoUrl =" + uPhotoUrl);
            }
        } catch (JSONException joe) {
            Log.e(TAG, "parseFBResponse: " + joe);
        }
    }

    @Override
    public void logout() {
        LoginManager.getInstance().logOut();
    }

    @Override
    public void postMedia(String message) {

        LoginManager.getInstance().logInWithPublishPermissions(fragment, Arrays.asList(PUBLISH_ACTIONS));

        String path = getURIPath(mSelectedImage);

        Bitmap image = BitmapFactory.decodeFile(path);
        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption(message)
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(sharePhoto)
                .build();
        ShareApi.share(content, null);

    }

    private String getURIPath(Uri uriValue) {
        String[] mediaStoreProjection = {MediaStore.Images.Media.DATA};
        Cursor cursor = fragment.getActivity().getContentResolver().query(uriValue, mediaStoreProjection, null, null, null);
        if (cursor != null) {
            int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String colIndexString = cursor.getString(colIndex);
            cursor.close();
            return colIndexString;
        }
        return null;
    }

    public interface OnFacebookDataLoadListener {
        void onCompleted(User user);
    }
}
