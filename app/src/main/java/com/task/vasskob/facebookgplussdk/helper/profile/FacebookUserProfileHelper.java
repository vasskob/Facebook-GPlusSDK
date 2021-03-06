package com.task.vasskob.facebookgplussdk.helper.profile;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.task.vasskob.facebookgplussdk.model.User;
import com.task.vasskob.facebookgplussdk.view.fragment.UserProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FacebookUserProfileHelper extends UserProfileHelper implements GraphRequest.GraphJSONObjectCallback {

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

    private final Fragment fragment;
    private OnUserLoadedListener mListener;


    public FacebookUserProfileHelper(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void loadUserProfile(OnUserLoadedListener loadedListener) {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this);
        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, NAME_EMAIL_BIRTHDAY);
        request.setParameters(parameters);
        request.executeAsync();
        mListener = loadedListener;
    }


    @Override
    public void onCompleted(JSONObject object, GraphResponse response) {
        final Profile profile = Profile.getCurrentProfile();
        parseFBResponse(object, profile);
        User user = new User(uName, uEmail, uBirthday, uPhotoUrl);
        Log.d(TAG, "user =" + user.getName() + " email = " + user.getEmail());
        mListener.onLoadSuccess(user);
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
    public void logout(OnLogoutListener listener) {
        LoginManager.getInstance().logOut();
        listener.onLogoutSuccess();
    }

    @Override
    public void postMedia(Uri media) {

        LoginManager.getInstance().logInWithPublishPermissions(fragment, Arrays.asList(PUBLISH_ACTIONS));

        String path = getURIPath(media);

        Bitmap image = BitmapFactory.decodeFile(path);
        SharePhoto sharePhoto = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption(POST_TITLE)
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


}
