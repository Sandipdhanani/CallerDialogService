package com.libraryofCall.CallFloatingDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.libraryofCall.CallFloatingDialog.Service.CallMonitorForegroundService;

public class Floating_dialog_service {
    public static String Admob_Visibility;
    public static String add_status;

    public static String Admob_Banner;
    public static String Admob_Native;
    public static Context context;

    public Floating_dialog_service(Context context) {
        this.context=context;
    }

    public static void setAdmob_Visibility(String admob_Visibility) {
        Admob_Visibility = admob_Visibility;
    }

    public static void setAdd_status(String add_status) {
        Floating_dialog_service.add_status = add_status;
    }

    public static String getAdmob_Visibility() {
        return Admob_Visibility;
    }

    public static String getAdd_status() {
        return add_status;
    }

    public static String getAdmob_Banner() {
        return Admob_Banner;
    }

    public static String getAdmob_Native() {
        return Admob_Native;
    }

    public static Context getContext() {
        return context;
    }

    public static void setAdmob_Banner(String admob_Banner) {
        Admob_Banner = admob_Banner;
    }

    public static void setAdmob_Native(String admob_Native) {
        Admob_Native = admob_Native;
    }

    public void callservice(Activity activity) {
        Intent serviceIntent = new Intent(activity, CallMonitorForegroundService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            activity.startForegroundService(serviceIntent);
        } else {
            activity.startService(serviceIntent);
        }
    }
}
