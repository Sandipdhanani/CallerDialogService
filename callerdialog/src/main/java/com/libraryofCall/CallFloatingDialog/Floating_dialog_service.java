package com.libraryofCall.CallFloatingDialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.libraryofCall.CallFloatingDialog.Service.CallMonitorForegroundService;

public class Floating_dialog_service {
    public static String Admob_Visibility;
    public static String add_status;

    public static String Admob_Banner;
    public static String Admob_Native;
    public static String FB_Native;
    public static String FB_NativeBanner;
    public static String qureka;
    public static Context context;
    public static Class<?> ActivityClass;
    public static int TextADtagcolor;
    public static Drawable Adbuttonbgcolor;

    public static boolean button_1;
    public static int btn_color;
    public static String btn_1_text;
    public static Drawable btn_1_image;

    public static boolean button_2;
    public static String btn_2_text;
    public static Drawable btn_2_image;

    public static boolean button_3;
    public static String btn_3_text;
    public static Drawable btn_3_image;

    public static int hadercolordialog;

    public static int getHadercolordialog() {
        return hadercolordialog;
    }

    public static void setHadercolordialog(int hadercolordialog) {
        Floating_dialog_service.hadercolordialog = hadercolordialog;
    }

    public static boolean isButton_1() {
        return button_1;
    }

    public static void setButton_1(boolean button_1) {
        Floating_dialog_service.button_1 = button_1;
    }

    public static int getBtn_color() {
        return btn_color;
    }

    public static void setBtn_color(int btn_1_color) {
        Floating_dialog_service.btn_color = btn_1_color;
    }

    public static String getBtn_1_text() {
        return btn_1_text;
    }

    public static void setBtn_1_text(String btn_1_text) {
        Floating_dialog_service.btn_1_text = btn_1_text;
    }

    public static Drawable getBtn_1_image() {
        return btn_1_image;
    }

    public static void setBtn_1_image(Drawable btn_1_image) {
        Floating_dialog_service.btn_1_image = btn_1_image;
    }

    public static boolean isButton_2() {
        return button_2;
    }

    public static void setButton_2(boolean button_2) {
        Floating_dialog_service.button_2 = button_2;
    }



    public static String getBtn_2_text() {
        return btn_2_text;
    }

    public static void setBtn_2_text(String btn_2_text) {
        Floating_dialog_service.btn_2_text = btn_2_text;
    }

    public static Drawable getBtn_2_image() {
        return btn_2_image;
    }

    public static void setBtn_2_image(Drawable btn_2_image) {
        Floating_dialog_service.btn_2_image = btn_2_image;
    }

    public static boolean isButton_3() {
        return button_3;
    }

    public static void setButton_3(boolean button_3) {
        Floating_dialog_service.button_3 = button_3;
    }



   public static String getBtn_3_text() {
        return btn_3_text;
    }

    public static void setBtn_3_text(String btn_3_text) {
        Floating_dialog_service.btn_3_text = btn_3_text;
    }

    public static Drawable getBtn_3_image() {
        return btn_3_image;
    }

    public static void setBtn_3_image(Drawable btn_3_image) {
        Floating_dialog_service.btn_3_image = btn_3_image;
    }

    public static String getDilog_appname() {
        return Dilog_appname;
    }

    public static void setDilog_appname(String dilog_appname) {
        Dilog_appname = dilog_appname;
    }
    public static String Dilog_appname;


    public static String getQureka() {
        return qureka;
    }

    public static void setQureka(String qureka) {
        Floating_dialog_service.qureka = qureka;
    }

    public static String getFB_NativeBanner() {
        return FB_NativeBanner;
    }

    public static void setFB_NativeBanner(String FB_NativeBanner) {
        Floating_dialog_service.FB_NativeBanner = FB_NativeBanner;
    }

    public static String getFB_Native() {
        return FB_Native;
    }

    public static void setFB_Native(String FB_Native) {
        Floating_dialog_service.FB_Native = FB_Native;
    }

    public static int getTextADtagcolor() {
        return TextADtagcolor;
    }

    public static void setTextADtagcolor(int textADtagcolor) {
        TextADtagcolor = textADtagcolor;
    }

    public static Drawable getAdbuttonbgcolor() {
        return Adbuttonbgcolor;
    }

    public static void setAdbuttonbgcolor(Drawable adbuttonbgcolor) {
        Adbuttonbgcolor = adbuttonbgcolor;
    }

    public static Class<?> getActivityClass() {
        return ActivityClass;
    }

    public static void setActivityClass(Class<?> activityClass) {
        ActivityClass = activityClass;
    }

    public Floating_dialog_service(Context context) {
        this.context = context;
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
