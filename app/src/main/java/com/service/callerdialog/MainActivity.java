package com.service.callerdialog;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;

public class MainActivity extends AppCompatActivity {
    Floating_dialog_service floatingDialogService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // using XML layout
        intit();
    }

    private void intit() {
        floatingDialogService = new Floating_dialog_service(MainActivity.this);
        Floating_dialog_service.setAdd_status("1");
        Floating_dialog_service.setAdmob_Visibility("1");
        Floating_dialog_service.setAdmob_Native("ca-app-pub-3940256099942544/2247696110");
        Floating_dialog_service.setAdmob_Banner("ca-app-pub-3940256099942544/9214589741");

        floatingDialogService.callservice(MainActivity.this);
    }
}
