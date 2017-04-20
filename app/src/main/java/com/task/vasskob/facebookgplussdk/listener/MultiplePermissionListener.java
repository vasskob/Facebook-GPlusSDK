package com.task.vasskob.facebookgplussdk.listener;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.task.vasskob.facebookgplussdk.MainActivity;

import java.util.List;

public class MultiplePermissionListener implements MultiplePermissionsListener {

    private final MainActivity activity;

    public MultiplePermissionListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            activity.showPermissionGranted();
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,
                                                   PermissionToken token) {

    }
}