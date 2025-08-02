package com.service.callerdialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.libraryofCall.CallFloatingDialog.Utils.PermissionClass;

import java.util.List;

public class OverlayPermisiion_Activity extends AppCompatActivity {
    Button btn_permission;
    private static final int OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay_permisiion);
        Defindid();
        inint();
    }

    private void Defindid() {
        btn_permission = findViewById(R.id.btn_permission);
    }

    private void inint() {
        btn_permission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestOverlayPermission();

            }
        });
    }

    private void requestOverlayPermission() {
        Toast.makeText(this, "Please allow 'Draw over other apps' permission", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show();
            } else {
                startactivity();
            }
        }
    }


    private void startactivity() {
        Intent intent = new Intent(OverlayPermisiion_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
    }
}