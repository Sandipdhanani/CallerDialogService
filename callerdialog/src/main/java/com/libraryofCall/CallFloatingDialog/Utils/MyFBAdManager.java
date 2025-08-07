package com.libraryofCall.CallFloatingDialog.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdValue;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;
import com.libraryofCall.CallFloatingDialog.R;

public class MyFBAdManager {

    private static final String TAG = "MyAdManager";

    public static com.facebook.ads.NativeAd preloadedFbNative;
    public static NativeAd preloadedAdmobNative;
    public static String admonnativ = "";

    // ✅ Preload Facebook Native Ad first
    public static void preloadNativeAd(final Context context, final String fbAdUnitId, final String admobAdUnitId) {
        admonnativ = admobAdUnitId;
        preloadedFbNative = new com.facebook.ads.NativeAd(context, fbAdUnitId);

        preloadedFbNative.loadAd(
                preloadedFbNative.buildLoadAdConfig()
                        .withAdListener(new NativeAdListener() {
                            @Override
                            public void onMediaDownloaded(Ad ad) {
                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {
                                Log.e(TAG, "Facebook Native failed: " + adError.getErrorMessage());

                                // 🔁 Fallback to AdMob
                                preloadAdmobNativeAd(context, admobAdUnitId);
                            }

                            @Override
                            public void onAdLoaded(Ad ad) {
                                Log.d(TAG, "Facebook Native preloaded");
                            }

                            @Override
                            public void onAdClicked(Ad ad) {
                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        })
                        .build()
        );
    }

    // ✅ Preload AdMob Native Ad (called only if Facebook fails)
    private static void preloadAdmobNativeAd(final Context context, final String admobAdUnitId) {
        AdLoader adLoader = new AdLoader.Builder(context, admobAdUnitId)
                .forNativeAd(nativeAd -> {
                    if (preloadedAdmobNative != null) {
                        preloadedAdmobNative.destroy();
                    }
                    preloadedAdmobNative = nativeAd;
                    Log.d(TAG, "AdMob Native Ad preloaded");
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.e(TAG, "AdMob Native failed: " + loadAdError.getMessage());
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    // ✅ Show Native Ad (Facebook if loaded, otherwise AdMob)
    public static void showNativeAd(Context context, ViewGroup container) {
        container.removeAllViews();

        // ✅ Show Facebook Native if available
        if (preloadedFbNative != null && preloadedFbNative.isAdLoaded()) {
            Log.d(TAG, "Showing Facebook Native Ad");

            View render = com.facebook.ads.NativeAdView.render(context, preloadedFbNative, com.facebook.ads.NativeAdView.Type.HEIGHT_300);
            container.setVisibility(View.VISIBLE);
            container.addView(render);

            preloadedFbNative = null;
        }

        // 🔁 Fallback to AdMob if Facebook not available
        else if (preloadedAdmobNative != null) {
            Log.d(TAG, "Showing AdMob Native Ad");

            NativeAd nativeAd = preloadedAdmobNative;
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


            container.removeAllViews();
            container.addView(adView);

            nativeAd.setOnPaidEventListener(new OnPaidEventListener() {
                @Override
                public void onPaidEvent(@NonNull AdValue adValue) {
                }
            });

            preloadedAdmobNative = null;
        } else {
            Log.w(TAG, "No native ad available to show.");
            container.setVisibility(View.GONE);
        }
    }
}
