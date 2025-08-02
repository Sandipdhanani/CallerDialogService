package com.libraryofCall.CallFloatingDialog.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionClass {

    private final Activity activity;
    private final PermissionListener listener;
    private final int REQUEST_CODE = 123;

    // Static permissions
    private final String[] staticPermissions = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.MANAGE_OWN_CALLS
    };

    private String[] dynamicPermissions = new String[]{};

    public PermissionClass(Activity activity, PermissionListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void setDynamicPermissions(String[] dynamicPermissions) {
        this.dynamicPermissions = dynamicPermissions;
    }

    public boolean checkpermission() {
        for (String permission : staticPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        for (String permission : dynamicPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public void RequestPermissions() {
        List<String> permissionsNeeded = new ArrayList<>();

        for (String permission : staticPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        for (String permission : dynamicPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity,
                    permissionsNeeded.toArray(new String[0]),
                    REQUEST_CODE);
        } else {
            listener.onAllPermissionsGranted();
        }
    }

    // Call this inside onRequestPermissionsResult() of Activity
    public void handlePermissionsResult(int requestCode,
                                        @NonNull String[] permissions,
                                        @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            List<String> deniedPermissions = new ArrayList<>();

            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permissions[i]);
                }
            }

            if (deniedPermissions.isEmpty()) {
                listener.onAllPermissionsGranted();
            } else {
                listener.onPermissionsDenied(deniedPermissions);
            }
        }
    }


    public static boolean isDefaultDialer(Context context) {
        try {
            if (!Settings.canDrawOverlays(context)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public static boolean checkpermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_OWN_CALLS) == PackageManager.PERMISSION_GRANTED;
    }

    public interface PermissionListener {
        void onAllPermissionsGranted();

        void onPermissionsDenied(List<String> deniedPermissions);
    }
}
