// CallMonitorForegroundService.java
package com.libraryofCall.CallFloatingDialog.Service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;
import com.libraryofCall.CallFloatingDialog.R;
import com.libraryofCall.CallFloatingDialog.Utils.MyAdManager;
import com.libraryofCall.CallFloatingDialog.Utils.MyAdManagerAllAds;
import com.libraryofCall.CallFloatingDialog.Utils.MyFBAdManager;
import com.libraryofCall.CallFloatingDialog.Utils.PermissionClass;


public class CallMonitorForegroundService extends Service {

    private int previousState = TelephonyManager.CALL_STATE_IDLE;
    private TelephonyCallback telephonyCallback;
    private boolean isCallIncoming = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("call_number_is", "number");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                Log.e("call_number_is123", "number");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(1, NotificationUtils.createNotification(this), ServiceInfo.FOREGROUND_SERVICE_TYPE_PHONE_CALL);
                } else {
                    startForeground(1, NotificationUtils.createNotification(this));
                }

                registerCallListener();
            } catch (Exception e) {
                e.printStackTrace();
                stopSelf();
            }
        }, 100); // ✅ Small delay avoids Android 14 crash
        return START_STICKY;
    }

    private void registerCallListener() {

        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (tm == null) return;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (PermissionClass.checkpermission(getApplicationContext())) {
                    telephonyCallback = new TelephonyCallbackImpl();
                    tm.registerTelephonyCallback(getMainExecutor(), telephonyCallback);
                }

            } else {
                tm.listen(new PhoneStateListener() {
                    @Override

                    public void onCallStateChanged(int state, String incomingNumber) {
                        handleCallState(state, incomingNumber);
                    }
                }, PhoneStateListener.LISTEN_CALL_STATE);
            }
        } catch (Exception e) {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    private class TelephonyCallbackImpl extends TelephonyCallback implements TelephonyCallback.CallStateListener {
        @Override
        public void onCallStateChanged(int state) {
            String call = "outgoing call";
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    isCallIncoming = true;
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    call = isCallIncoming ? "Incoming call" : "Outgoing call";
                    isCallIncoming = false;
                    break;
            }
            handleCallState(state, call);
        }
    }

    private void handleCallState(int state, String number) {
        Log.e("call_number_is", number);

        if (Floating_dialog_service.getAdd_status().equals("1")) {
            Log.e("Admob_ads_12", "visbla");
            if (Floating_dialog_service.getQureka().equals("a")) {
                if (MyAdManager.preloadedAdmobNative == null && MyAdManager.preloadedFbNative == null) {
                    MyAdManager.preloadNativeAd(this, Floating_dialog_service.getAdmob_Native(), Floating_dialog_service.getFB_Native());
                }
            } else if (Floating_dialog_service.getQureka().equals("f")) {
                if (MyFBAdManager.preloadedAdmobNative == null && MyFBAdManager.preloadedFbNative == null) {
                    MyFBAdManager.preloadNativeAd(this, Floating_dialog_service.getFB_Native(), Floating_dialog_service.getAdmob_Native());
                }
            }
        }

        if (state == TelephonyManager.CALL_STATE_IDLE && previousState == TelephonyManager.CALL_STATE_OFFHOOK) {
            Log.e("call_number_is_1", number);
            Intent intent = new Intent(this, FloatingDialogService.class);
            intent.putExtra("number", number);
            startService(intent);
        }
        previousState = state;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}