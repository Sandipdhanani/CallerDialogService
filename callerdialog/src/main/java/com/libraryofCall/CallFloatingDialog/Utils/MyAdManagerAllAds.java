package com.libraryofCall.CallFloatingDialog.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.MediaView;
import com.libraryofCall.CallFloatingDialog.Floating_dialog_service;
import com.libraryofCall.CallFloatingDialog.R;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdManagerAllAds {

    public static NativeAd preloadedNativeAd;
    public static InterstitialAd preloadedInterstitial;
    public static RewardedAd preloadedRewarded;

    private static final String TAG = "MyAdManager";

    // ✅ PRELOAD NATIVE AD
    public static void preloadNativeAd(Context context, String nativeAdUnitId) {
        AdLoader adLoader = new AdLoader.Builder(context, nativeAdUnitId)
                .forNativeAd(nativeAd -> {
                    if (preloadedNativeAd != null) {
                        preloadedNativeAd.destroy();
                    }
                    preloadedNativeAd = nativeAd;
                    Log.d(TAG, "Native ad preloaded");
                })
                .withAdListener(new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        Log.e(TAG, "Native ad failed: " + adError.getMessage());
                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    // ✅ SHOW NATIVE AD INTO VIEWGROUP
    public static void showNativeAd(Context context, ViewGroup container) {
        if (preloadedNativeAd == null) return;
        NativeAd nativeAd = preloadedNativeAd;
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
        // Show in container
        container.removeAllViews();
        container.setVisibility(View.VISIBLE);
        container.addView(adView);

        // Clear stored reference
        preloadedNativeAd = null;
    }

    // ✅ PRELOAD INTERSTITIAL
    public static void preloadInterstitialAd(Context context, String adUnitId) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, adUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(InterstitialAd interstitialAd) {
                preloadedInterstitial = interstitialAd;
                Log.d(TAG, "Interstitial preloaded");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(TAG, "Interstitial failed: " + adError.getMessage());
            }
        });
    }

    // ✅ SHOW INTERSTITIAL
    public static void showInterstitial(Activity activity) {
        if (preloadedInterstitial != null) {
            preloadedInterstitial.show(activity);
            preloadedInterstitial = null;
        }
    }

    // ✅ PRELOAD REWARDED
    public static void preloadRewardedAd(Context context, String adUnitId) {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, adUnitId, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(RewardedAd rewardedAd) {
                preloadedRewarded = rewardedAd;
                Log.d(TAG, "Rewarded ad preloaded");
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e(TAG, "Rewarded ad failed: " + adError.getMessage());
            }
        });
    }

    // ✅ SHOW REWARDED
    public static void showRewardedAd(Activity activity, Runnable onUserEarnedReward) {
        if (preloadedRewarded != null) {
            preloadedRewarded.show(activity, rewardItem -> {
                onUserEarnedReward.run();
            });
            preloadedRewarded = null;
        }
    }
}
