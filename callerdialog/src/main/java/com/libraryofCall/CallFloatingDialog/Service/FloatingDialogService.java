package com.libraryofCall.CallFloatingDialog.Service;


import static com.libraryofCall.CallFloatingDialog.Utils.Helper.generateAvatar;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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


import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.libraryofCall.CallFloatingDialog.R;
import com.libraryofCall.CallFloatingDialog.Utils.MyAdManager;
import com.libraryofCall.CallFloatingDialog.Utils.MyAdManagerAllAds;
import com.libraryofCall.CallFloatingDialog.Utils.MyFBAdManager;
import com.makeramen.roundedimageview.RoundedImageView;
import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;
import com.libraryofCall.CallFloatingDialog.Utils.NativeTemplateStyle;

import org.jetbrains.annotations.NotNull;

public class FloatingDialogService extends Service {

    Context context;

    private View overlayView;
    private WindowManager windowManager;

    TextView callerName, callerNumber, network, country, app_name;
    RoundedImageView profilePic;
    RelativeLayout first;
    LinearLayout button_1, button_2, button_3, btn_lin_1, btn_lin_2, btn_lin_3;
    ImageView btn_close, btn_img_1, btn_img_2, btn_img_3;
    TextView btn_txt_1, btn_txt_2, btn_txt_3;
    String native_ads = "";
    String Banner_ads = "";
    String FB_Native = "";
    String FB_NativeBanner = "";
    String qureka = "";
    String Admob_Visibility = "1";
    String add_stats = "1";

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String label = intent.getStringExtra("number");
        this.native_ads = Floating_dialog_service.getAdmob_Native();
        this.Banner_ads = Floating_dialog_service.getAdmob_Banner();
        this.Admob_Visibility = Floating_dialog_service.getAdmob_Visibility();
        this.FB_Native = Floating_dialog_service.getFB_Native();
        this.FB_NativeBanner = Floating_dialog_service.getFB_NativeBanner();
        this.add_stats = Floating_dialog_service.getAdd_status();
        this.qureka = Floating_dialog_service.getQureka();
        showFloatingOverlay(label);
        Log.e("Admob_ads_12_ads_unit", this.native_ads + " , " + this.Banner_ads);
        return START_NOT_STICKY;
    }

    private void showFloatingOverlay(String label) {
        Log.e("CAll_run1_cut", "showFloatingOverlay52369");
        if (windowManager == null) {
            windowManager = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
            Context themedContext = new ContextThemeWrapper(getApplicationContext(), R.style.Theme_CAller); // Use your app theme here
            LayoutInflater inflater = LayoutInflater.from(themedContext);
            overlayView = inflater.inflate(R.layout.activity_custom_dialog, null);
            if (this.add_stats.equals("1")) {
                Log.e("Admob_ads_12", "visbla");
                if (this.qureka.equals("a")) {
//                    Native_Ads_Admob((ViewGroup) overlayView.findViewById(R.id.fl_adplaceholder));
                    MyAdManager.showNativeAd(this, (ViewGroup) overlayView.findViewById(R.id.fl_adplaceholder));
                    Native_Banner_Admob((ViewGroup) overlayView.findViewById(R.id.adview), overlayView);
                } else if (this.qureka.equals("f")) {
                    Facebook_nativebanner((ViewGroup) overlayView.findViewById(R.id.adview));
//                    facebooknative((ViewGroup) overlayView.findViewById(R.id.fl_adplaceholder));
                    MyFBAdManager.showNativeAd(this, (ViewGroup) overlayView.findViewById(R.id.fl_adplaceholder));
                }
            }
            btn_close = overlayView.findViewById(R.id.closeIcon);
            callerName = overlayView.findViewById(R.id.callerName);
            callerNumber = overlayView.findViewById(R.id.callerNumber);
            network = overlayView.findViewById(R.id.network);
            country = overlayView.findViewById(R.id.country);
            profilePic = overlayView.findViewById(R.id.profile_pic);
            first = overlayView.findViewById(R.id.first);
            button_1 = overlayView.findViewById(R.id.button_1);
            button_2 = overlayView.findViewById(R.id.button_2);
            button_3 = overlayView.findViewById(R.id.button_3);
            btn_lin_1 = overlayView.findViewById(R.id.btn_lin_1);
            btn_lin_2 = overlayView.findViewById(R.id.btn_lin_2);
            btn_lin_3 = overlayView.findViewById(R.id.btn_lin_3);
            btn_img_1 = overlayView.findViewById(R.id.btn_img_1);
            btn_img_2 = overlayView.findViewById(R.id.btn_img_2);
            btn_img_3 = overlayView.findViewById(R.id.btn_img_3);
            btn_txt_1 = overlayView.findViewById(R.id.btn_txt_1);
            btn_txt_2 = overlayView.findViewById(R.id.btn_txt_2);
            btn_txt_3 = overlayView.findViewById(R.id.btn_txt_3);
            app_name = overlayView.findViewById(R.id.app_name);

            app_name.setText(Floating_dialog_service.getDilog_appname());

            first.setBackgroundTintList(ColorStateList.valueOf(Floating_dialog_service.getHadercolordialog()));
            int layoutFlag;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutFlag = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutFlag = WindowManager.LayoutParams.TYPE_PHONE;
            }


            float density = getResources().getDisplayMetrics().density;
            int heightInPx;
            if (this.qureka.equals("f")) {
                heightInPx = (int) (670 * density);
            } else {
                heightInPx = (int) (620 * density);
            }

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    heightInPx,
                    layoutFlag,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT
            );

            params.gravity = Gravity.CENTER;
            windowManager.addView(overlayView, params);

//            if (Floating_dialog_service.getAdd_status().equals("1")) {
//                Log.e("Admob_ads_12", "visbla");
//                Native_Ads_Admob((ViewGroup) overlayView.findViewById(R.id.fl_adplaceholder));
//                Native_Banner_Admob((ViewGroup) overlayView.findViewById(R.id.adview), overlayView);
//            }
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


        if (Floating_dialog_service.isButton_1()) {
            button_1.setVisibility(View.VISIBLE);
            btn_lin_1.setBackgroundColor(Floating_dialog_service.getBtn_color());
            btn_img_1.setImageDrawable(Floating_dialog_service.getBtn_1_image());
            btn_txt_1.setText(Floating_dialog_service.getBtn_1_text());
        } else {
            button_1.setVisibility(View.GONE);
        }

        if (Floating_dialog_service.isButton_2()) {
            button_2.setVisibility(View.VISIBLE);
            btn_lin_2.setBackgroundColor(Floating_dialog_service.getBtn_color());
            btn_img_2.setImageDrawable(Floating_dialog_service.getBtn_2_image());
            btn_txt_2.setText(Floating_dialog_service.getBtn_2_text());
        } else {
            button_2.setVisibility(View.GONE);
        }

        if (Floating_dialog_service.isButton_3()) {
            button_3.setVisibility(View.VISIBLE);
            btn_lin_3.setBackgroundColor(Floating_dialog_service.getBtn_color());
            btn_img_3.setImageDrawable(Floating_dialog_service.getBtn_3_image());
            btn_txt_3.setText(Floating_dialog_service.getBtn_3_text());
        } else {
            button_3.setVisibility(View.GONE);
        }

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
            button_1.setOnClickListener(v -> {
                openactivity(call);
            });
            button_2.setOnClickListener(v -> {
                openactivity(call);
            });
            button_3.setOnClickListener(v -> {
                openactivity(call);
            });

        }
    }

    private void openactivity(String call) {
        SharedPreferences sharedpreferences;
        SharedPreferences.Editor editor;
        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString("phone", call);
        editor.putString("mail", "****.gmail.com");
        editor.apply();
        Intent intent;


        intent = new Intent(getApplicationContext(), Floating_dialog_service.getActivityClass());
        intent.putExtra("country_code", "+91");
        intent.putExtra("phone_no", call.replace("+91", ""));
        intent.putExtra("email_id", "****.gmail.com");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        removedialog();
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


    public void Facebook_nativebanner(final ViewGroup viewGroup) {

        viewGroup.post(() -> {
            final NativeBannerAd nativeBannerAd = new NativeBannerAd(context, FB_NativeBanner);
            nativeBannerAd.loadAd(nativeBannerAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
                public void onAdClicked(Ad ad) {
                }

                public void onLoggingImpression(Ad ad) {
                }

                public void onMediaDownloaded(Ad ad) {
                }

                public void onError(Ad ad, AdError adError) {
//              viewGroup.setVisibility(View.GONE);
                    if (overlayView != null) {
                        final com.google.android.gms.ads.AdView adView = new com.google.android.gms.ads.AdView(context);
                        adView.setAdSize(getAdSizeFromAttachedView(overlayView));
                        adView.setAdUnitId(Banner_ads);
                        adView.loadAd(new AdRequest.Builder().build());
                        adView.setAdListener(new AdListener() {
                            public void onAdLoaded() {
                                super.onAdLoaded();

                                try {
                                    viewGroup.removeAllViews();
                                    viewGroup.addView(adView);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onAdFailedToLoad(LoadAdError adError) {

                                viewGroup.setVisibility(View.GONE);


                            }
                        });

                    }
                }

                public void onAdLoaded(Ad ad) {
                    View render = NativeBannerAdView.render(context, nativeBannerAd, NativeBannerAdView.Type.HEIGHT_120);
                    viewGroup.setVisibility(View.VISIBLE);
                    viewGroup.removeAllViews();
                    viewGroup.addView(render);
                }
            }).build());
        });

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
                            if (overlayView != null) {
                                final NativeBannerAd nativeBannerAd = new NativeBannerAd(context, FB_NativeBanner);
                                nativeBannerAd.loadAd(nativeBannerAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
                                    public void onAdClicked(Ad ad) {
                                    }

                                    public void onLoggingImpression(Ad ad) {
                                    }

                                    public void onMediaDownloaded(Ad ad) {
                                    }

                                    public void onError(Ad ad, AdError adError) {
                                        viewGroup.setVisibility(View.GONE);
                                    }

                                    public void onAdLoaded(Ad ad) {
                                        View render = NativeBannerAdView.render(context, nativeBannerAd, NativeBannerAdView.Type.HEIGHT_120);
                                        viewGroup.setVisibility(View.VISIBLE);
                                        viewGroup.removeAllViews();
                                        viewGroup.addView(render);
                                    }
                                }).build());
                            }
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
        if (attachedView == null || attachedView.getDisplay() == null) {
            Log.e("AdSize", "View or Display is null, fallback to default AdSize");
            return AdSize.BANNER; // or any safe fallback
        }
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
                        TextView txtadtag = adView.findViewById(R.id.txt_adtag);
                        txtadtag.setBackgroundColor(Floating_dialog_service.getTextADtagcolor());


                        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
                        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
                        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
                        ((Button) adView.getCallToActionView()).setBackground(Floating_dialog_service.getAdbuttonbgcolor());


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
                        if (!FB_Native.equals("0")) {
                            final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(context, FB_Native);
                            nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
                                public void onAdClicked(Ad ad) {
                                }

                                public void onLoggingImpression(Ad ad) {
                                }

                                public void onMediaDownloaded(Ad ad) {
                                }

                                public void onError(Ad ad, AdError adError) {
                                    viewGroup.setVisibility(View.GONE);
                                }

                                public void onAdLoaded(Ad ad) {
                                    View render = NativeAdView.render(context, nativeAd, NativeAdView.Type.HEIGHT_300);
                                    viewGroup.setVisibility(View.VISIBLE);
                                    viewGroup.addView(render);
                                }
                            }).build());
                        }
                    }
                }).build().loadAd(new AdRequest.Builder().build());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Admob_ads_12_native", e.getMessage());
            }
        }

    }

    public void facebooknative(final ViewGroup viewGroup) {


        final com.facebook.ads.NativeAd nativeAd = new com.facebook.ads.NativeAd(context, FB_Native);
        nativeAd.loadAd(nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            public void onAdClicked(Ad ad) {
            }

            public void onLoggingImpression(Ad ad) {
            }

            public void onMediaDownloaded(Ad ad) {
            }

            public void onError(Ad ad, AdError adError) {
                try {

                    final Activity activity = (Activity) context;
                    AdLoader.Builder builder = new AdLoader.Builder((Context) context, native_ads);
                    builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        @Override
                        public void onNativeAdLoaded(@NonNull @NotNull NativeAd nativeAd) {
                            Log.e("NativeAds", "Loaded");
                            NativeTemplateStyle build = new NativeTemplateStyle.Builder().build();
                            com.google.android.gms.ads.nativead.NativeAdView adView = (com.google.android.gms.ads.nativead.NativeAdView) activity.getLayoutInflater().inflate(R.layout.ad_unified, null);
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
                        public void onAdFailedToLoad(@NonNull @NotNull LoadAdError loadAdError) {
                            super.onAdFailedToLoad(loadAdError);
                            Log.d("Admob_native_fail", "" + loadAdError);

                        }
                    }).build().loadAd(new AdRequest.Builder().build());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            public void onAdLoaded(Ad ad) {
                View render = NativeAdView.render(context, nativeAd, NativeAdView.Type.HEIGHT_300);
                viewGroup.setVisibility(View.VISIBLE);
                viewGroup.addView(render);
            }
        }).build());

    }
}
