package com.service.callerdialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;
import com.libraryofCall.CallFloatingDialog.Utils.PermissionClass;

import java.util.List;

public class Splace_Activity extends AppCompatActivity {
    PermissionClass permissionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splace);
        permissionClass = new PermissionClass(this, new PermissionClass.PermissionListener() {
            @Override
            public void onAllPermissionsGranted() {

            }

            @Override
            public void onPermissionsDenied(List<String> deniedPermissions) {

            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (permissionClass.checkpermission()&&permissionClass.isDefaultDialer(Splace_Activity.this)) {
                    startActivity(new Intent(Splace_Activity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(Splace_Activity.this, Permission_activity.class));
                }
            }
        }, 3000);
    }
}
