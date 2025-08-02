// CallMonitorForegroundService.java
package com.libraryofCall.CallFloatingDialog.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.libraryofCall.CallFloatingDialog.Utils.PermissionClass;


public class CallMonitorForegroundService extends Service {

    private int previousState = TelephonyManager.CALL_STATE_IDLE;
    private TelephonyCallback telephonyCallback;
    private boolean isCallIncoming = false;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("call_number_is", "number");
        startForeground(1, NotificationUtils.createNotification(this));
        registerCallListener();
    }

    private void registerCallListener() {
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
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