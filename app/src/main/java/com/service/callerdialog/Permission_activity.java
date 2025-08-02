package com.service.callerdialog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.libraryofCall.CallFloatingDialog.Utils.PermissionClass;

import java.util.List;

public class Permission_activity extends AppCompatActivity {
    Button btn_permission;
    PermissionClass permissionClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Defindid();
        inint();
    }

    private void Defindid() {
        btn_permission = findViewById(R.id.btn_permission);
    }

    private void inint() {
        permissionClass = new PermissionClass(Permission_activity.this, new PermissionClass.PermissionListener() {
            @Override
            public void onAllPermissionsGranted() {
                startActivitycl();
            }

            @Override
            public void onPermissionsDenied(List<String> deniedPermissions) {

            }
        });
        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionClass.checkpermission()) {
                    startActivitycl();
                } else {
                    permissionClass.RequestPermissions();
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionClass.handlePermissionsResult(requestCode, permissions, grantResults);
    }

    private void startActivitycl() {
        if (permissionClass.isDefaultDialer(Permission_activity.this)) {
            startActivity(new Intent(Permission_activity.this, MainActivity.class));
        } else {
            startActivity(new Intent(Permission_activity.this, OverlayPermisiion_Activity.class));
        }
    }
}