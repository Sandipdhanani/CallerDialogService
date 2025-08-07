package com.service.callerdialog;

import android.annotation.SuppressLint;
import android.graphics.Color;
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void intit() {
        floatingDialogService = new Floating_dialog_service(MainActivity.this);
        Floating_dialog_service.setAdd_status("1");
        Floating_dialog_service.setAdmob_Visibility("1");
        Floating_dialog_service.setQureka("a");
        Floating_dialog_service.setFB_Native("CAROUSEL_IMG_SQUARE_APP_INSTALL#YOUR_PLACEMENT_ID");
        Floating_dialog_service.setFB_NativeBanner("CAROUSEL_IMG_SQUARE_APP_INSTALL#YOUR_PLACEMENT_ID");
        Floating_dialog_service.setAdmob_Native("ca-app-pub-3940256099942544/2247696110");
        Floating_dialog_service.setAdmob_Banner("ca-app-pub-3940256099942544/9214589741");
        Floating_dialog_service.setActivityClass(MainActivity.class);
        Floating_dialog_service.setTextADtagcolor(Color.BLUE);
        Floating_dialog_service.setDilog_appname("Gps map stamp");
        Floating_dialog_service.setAdbuttonbgcolor(getDrawable(R.drawable.buttone_bg));
        Floating_dialog_service.setHadercolordialog(getColor(R.color.hader_color));
        Floating_dialog_service.setBtn_color(getColor(R.color.blue));
        Floating_dialog_service.setButton_1(true);
        Floating_dialog_service.setBtn_1_image(getDrawable(R.drawable.ic_home));
        Floating_dialog_service.setBtn_1_text("Open app");
        Floating_dialog_service.setButton_2(true);
        Floating_dialog_service.setBtn_2_image(getDrawable(R.drawable.ic_share_app));
        Floating_dialog_service.setBtn_2_text("Share app");
        Floating_dialog_service.setButton_3(false);
        Floating_dialog_service.setBtn_3_image(getDrawable(R.drawable.ic_rate_app));
        Floating_dialog_service.setBtn_3_text("Rate app");
        floatingDialogService.callservice(MainActivity.this);
    }
}
