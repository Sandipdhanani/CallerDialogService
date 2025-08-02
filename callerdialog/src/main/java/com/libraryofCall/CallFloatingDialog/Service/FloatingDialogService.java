package com.libraryofCall.CallFloatingDialog.Service;


import static com.libraryofCall.CallFloatingDialog.Utils.Helper.generateAvatar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.libraryofCall.CallFloatingDialog.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;
import com.libraryofCall.CallFloatingDialog.Utils.NativeTemplateStyle;

public class FloatingDialogService extends Service {

    Context context;

    private View overlayView;
    private WindowManager windowManager;

    TextView callerName, callerNumber, network, country, blockTxt, tagName, txt_view_profile;
    RoundedImageView whoProfile, profilePic;
    RelativeLayout first;
    LinearLayout viewProfile;
    ImageView tagIcon, btn_close;
    String native_ads = "";
    String Banner_ads = "";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String label = intent.getStringExtra("number");

        SharedPreferences sh = getSharedPreferences("MyPREFERENCES_ads", Context.MODE_PRIVATE);
        this.native_ads = Floating_dialog_service.getAdmob_Native();
        this.Banner_ads = Floating_dialog_service.getAdmob_Banner();

        Log.e("Admob_ads_12_ads_unit", this.native_ads + " , " + this.Banner_ads);
        showFloatingOverlay(label);

        return START_NOT_STICKY;
    }

    private void showFloatingOverlay(String label) {
        Log.e("CAll_run1_cut", "showFloatingOverlay52369");
        if (windowManager == null) {
            windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
            Context themedContext = new ContextThemeWrapper(getApplicationContext(), R.style.Theme_CAller); // Use your app theme here
            LayoutInflater inflater = LayoutInflater.from(themedContext);
            overlayView = inflater.inflate(R.layout.activity_custom_dialog, null);
            btn_close = overlayView.findViewById(R.id.closeIcon);
            callerName = overlayView.findViewById(R.id.callerName);
            callerNumber = overlayView.findViewById(R.id.callerNumber);
            network = overlayView.findViewById(R.id.network);
            country = overlayView.findViewById(R.id.country);
            tagName = overlayView.findViewById(R.id.tag_name);
            whoProfile = overlayView.findViewById(R.id.who_profile);
            profilePic = overlayView.findViewById(R.id.profile_pic);
            first = overlayView.findViewById(R.id.first);
            viewProfile = overlayView.findViewById(R.id.view_profile);
            tagIcon = overlayView.findViewById(R.id.tag_icon);


            int layoutFlag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
            }


            float density = getResources().getDisplayMetrics().density;

            int heightInPx = (int) (620 * density);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    heightInPx,
                    layoutFlag,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            );

            params.gravity = Gravity.CENTER;
            windowManager.addView(overlayView, params);

            if (Floating_dialog_service.getAdd_status().equals("1")) {
                Log.e("Admob_ads_12", "visbla");
                Native_Ads_Admob((ViewGroup) overlayView.findViewById(R.id.fl_adplaceholder));
                Native_Banner_Admob((ViewGroup) overlayView.findViewById(R.id.adview), overlayView);
            }
            btn_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removedialog();
                }
            });
            setdata(label);
        }
    }

    private void removedialog() {
        try {
            if (overlayView.getWindowToken() != null) {
                windowManager.removeViewImmediate(overlayView);
            }
        } catch (Exception e) {
            Log.e("CAll_run1_cut_OverlayRemove", "Error: " + e.getMessage());
        }
        overlayView = null;
        stopSelf();
    }

    private void setdata(String label) {
        Log.e("floating_dialog1235", "display");

        profilePic.setImageBitmap(generateAvatar("P"));
        if (!label.isEmpty()) {
            callerNumber.setText(label);
            String number1 = "";
            if (label.contains("Incoming call") || label.contains("outgoing call")) {
                number1 = generateRandomIndianNumber();
            } else {
                number1 = label;
            }
            String call = number1;
            viewProfile.setOnClickListener(v -> {

                SharedPreferences sharedpreferences;
                SharedPreferences.Editor editor;
                sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                editor.putString("phone", call);
                editor.putString("mail", "****.gmail.com");
                editor.apply();
                Intent intent;

//                intent = new Intent(getApplicationContext(), User_profile_activity.class);
//                intent.putExtra("country_code", "+91");
//                intent.putExtra("phone_no", call.replace("+91", ""));
//                intent.putExtra("email_id", "****.gmail.com");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);


//                Intent in = new Intent(getApplicationContext(), GaneratPdfActivity.class);
//                in.putExtra("flag", 0);
//                in.putExtra("data", 1);
//                in.putExtra("phone", call);
//                in.putExtra("package", "Call");
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(in);
                removedialog();
            });

        }
    }

    public static String generateRandomIndianNumber() {
        // Indian mobile numbers typically start with 6, 7, 8, or 9
        int[] firstDigits = {6, 7, 8, 9};
        int firstDigit = firstDigits[(int) (Math.random() * firstDigits.length)];

        // Generate the remaining 9 digits randomly
        StringBuilder number = new StringBuilder();
        number.append(firstDigit);
        for (int i = 0; i < 9; i++) {
            int digit = (int) (Math.random() * 10); // 0–9
            number.append(digit);
        }

        return "+91" + number.toString();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void Native_Banner_Admob(final ViewGroup viewGroup, View overlayView) {

        if (Floating_dialog_service.getAdmob_Visibility().equals("1")) {
            try {
                Log.e("Admob_ads_12_native_banner", "load_ads - " + Banner_ads);

                viewGroup.post(() -> {
                    final com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(overlayView.getContext());
                    adView.setAdSize(getAdSizeFromAttachedView(overlayView));
//                    adView.setAdSize(getAdSize(context));
                    adView.setAdUnitId(Banner_ads);
                    adView.loadAd(new AdRequest.Builder().build());
                    adView.setAdListener(new AdListener() {
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            Log.d("Adaptive_Banner", "");
                            try {
                                viewGroup.removeAllViews();
                                viewGroup.addView(adView);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onAdFailedToLoad(LoadAdError adError) {
                            Log.e("Admob_ads_12_native_banner", adError.getMessage());


                        }
                    });
                });


            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Admob_ads_12_native_banner", e.getMessage());

            }

        }


    }

    private AdSize getAdSizeFromAttachedView(View attachedView) {
        Display display = attachedView.getDisplay(); // ✅ Safe if view is attached to window
        Point outPoint = new Point();
        display.getRealSize(outPoint);
        int widthPixels = outPoint.x;
        float density = attachedView.getResources().getDisplayMetrics().density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private com.google.android.gms.ads.AdSize getAdSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context.getDisplay().getRealMetrics(displayMetrics);
        } else {
            Display display = windowManager.getDefaultDisplay();
            display.getMetrics(displayMetrics);
        }

        int widthPixels = displayMetrics.widthPixels;
        float density = displayMetrics.density;
        int adWidth = (int) (widthPixels / density);

        return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }

    public void Native_Ads_Admob(final ViewGroup viewGroup) {
        if (Floating_dialog_service.getAdmob_Visibility().equals("1")) {
            try {
                Log.e("Admob_ads_12_native", "load_ads - " + native_ads);
                AdLoader.Builder builder = new AdLoader.Builder((Context) this.context, native_ads);
                builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                    @Override
                    public void onNativeAdLoaded(@NonNull NativeAd nativeAd) {
                        Log.e("NativeAds", "Loaded");
                        NativeTemplateStyle build = new NativeTemplateStyle.Builder().build();
                        com.google.android.gms.ads.nativead.NativeAdView adView = (com.google.android.gms.ads.nativead.NativeAdView) LayoutInflater.from(context).inflate(R.layout.ad_unified, null);
                        MediaView mediaView = adView.findViewById(R.id.ad_media);
                        adView.setMediaView(mediaView);
                        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
                        adView.setBodyView(adView.findViewById(R.id.ad_body));
                        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
                        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
                        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));


                        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
                        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
                        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());


                        if (nativeAd.getIcon() == null) {
                            adView.getIconView().setVisibility(View.GONE);
                        } else {
                            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                            adView.getIconView().setVisibility(View.VISIBLE);
                        }

                        if (nativeAd.getAdvertiser() == null) {
                            adView.getAdvertiserView().setVisibility(View.GONE);
                        } else {
                            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                            adView.getAdvertiserView().setVisibility(View.VISIBLE);
                        }


                        adView.setNativeAd(nativeAd);


                        viewGroup.removeAllViews();
                        viewGroup.addView(adView);
                    }
                });
                builder.withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.d("Admob_native_fail", "");
                        Log.e("Admob_ads_12_native", loadAdError.getMessage());


                    }
                }).build().loadAd(new AdRequest.Builder().build());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Admob_ads_12_native", e.getMessage());
            }
        }

    }
}
