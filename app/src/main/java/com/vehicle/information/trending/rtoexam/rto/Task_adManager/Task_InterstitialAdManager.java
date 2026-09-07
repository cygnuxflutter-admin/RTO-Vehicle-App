package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class Task_InterstitialAdManager {

    private static final String TAG = "InterstitialAdManager";
    private final String admobInterstitialAdId, fbInterstitialAdId;
    private final Context context;
    private final Task_PreferenceClass taskPreferenceClass;
    private String adXInterstitialAdId;
    private InterstitialAd admobInterstitialAd;
    private com.facebook.ads.InterstitialAd fbInterstitialAd;
    private boolean isLoading = false;

    public Task_InterstitialAdManager(Context context) {
        this.context = context.getApplicationContext() != null ? context.getApplicationContext() : context;
        taskPreferenceClass = new Task_PreferenceClass(this.context);
        
        String rawAdmob = taskPreferenceClass.getAdsId("GoogleInterstitialAd");
        admobInterstitialAdId = (rawAdmob != null) ? rawAdmob.trim() : "";
        
        String rawAdX = taskPreferenceClass.getAdsId("AdxInterstialUnitID");
        if (rawAdX == null || rawAdX.trim().isEmpty()) {
            rawAdX = taskPreferenceClass.getAdsId("AdxInterstitalAdunitID");
        }
        adXInterstitialAdId = (rawAdX != null) ? rawAdX.trim() : "";
        
        String rawFb = taskPreferenceClass.getAdsId("FbInterstitialAd");
        fbInterstitialAdId = (rawFb != null) ? rawFb.trim() : "";

        Log.d(TAG, "🔹 Interstitial Config: ID=" + admobInterstitialAdId + " | TargetClicks=" + taskPreferenceClass.getAdsStatus("InerstialClickCount"));
        if (!admobInterstitialAdId.isEmpty() && taskPreferenceClass.getAdsStatus("InerstialClickCount") > 0) {
            fetchAdMobAd();
        }
    }

    public void fetchAdMobAd() {
        if (admobInterstitialAdId == null || admobInterstitialAdId.trim().isEmpty() || taskPreferenceClass.getAdsStatus("InerstialClickCount") <= 0) {
            return;
        }

        if (isAdmobAdAvailable() || isLoading) {
            return;
        }

        isLoading = true;
        Log.d(TAG, "🟢 [INTERSTITIAL_AD] Loading AdMob Interstitial with ID: " + admobInterstitialAdId);
        InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                isLoading = false;
                Log.d(TAG, "🎉 [INTERSTITIAL_AD] AdMob Interstitial Loaded Successfully!");
                admobInterstitialAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                Log.e(TAG, "❌ [INTERSTITIAL_AD] AdMob Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                fetchAdXAd();
            }
        };
        AdRequest request = getAdRequest();
        InterstitialAd.load(context, admobInterstitialAdId, request, loadCallback);
    }

    public void fetchAdXAd() {
        if (isAdmobAdAvailable() || isLoading) {
            return;
        }

        if (adXInterstitialAdId == null || adXInterstitialAdId.trim().isEmpty()) {
            String rawAdX = taskPreferenceClass.getAdsId("AdxInterstialUnitID");
            if (rawAdX == null || rawAdX.trim().isEmpty()) {
                rawAdX = taskPreferenceClass.getAdsId("AdxInterstitalAdunitID");
            }
            adXInterstitialAdId = (rawAdX != null) ? rawAdX.trim() : "";
        }

        if (adXInterstitialAdId == null || adXInterstitialAdId.trim().isEmpty()) {
            fetchFbAd();
            return;
        }

        isLoading = true;
        Log.d(TAG, "🟡 [INTERSTITIAL_AD] Loading AdX Interstitial with ID: " + adXInterstitialAdId);
        InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                isLoading = false;
                Log.d(TAG, "🎉 [INTERSTITIAL_AD] AdX Interstitial Loaded Successfully!");
                admobInterstitialAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                Log.e(TAG, "❌ [INTERSTITIAL_AD] AdX Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                fetchFbAd();
            }
        };

        AdRequest request = getAdRequest();
        InterstitialAd.load(context, adXInterstitialAdId, request, loadCallback);
    }

    private void fetchFbAd() {
        if (fbInterstitialAdId == null || fbInterstitialAdId.isEmpty() || taskPreferenceClass.getAdsStatus("InerstialClickCount") <= 0) {
            return;
        }
        if (isFbAdAvailable() || isLoading) {
            return;
        }
        isLoading = true;
        fbInterstitialAd = new com.facebook.ads.InterstitialAd(context, fbInterstitialAdId);

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {}

            @Override
            public void onInterstitialDismissed(Ad ad) {
                fetchAdMobAd();
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                isLoading = false;
            }

            @Override
            public void onAdLoaded(Ad ad) {
                isLoading = false;
            }

            @Override
            public void onAdClicked(Ad ad) {}

            @Override
            public void onLoggingImpression(Ad ad) {}
        };

        try {
            fbInterstitialAd.loadAd(fbInterstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
        } catch (Exception e) {
            isLoading = false;
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    public boolean isAdmobAdAvailable() {
        return admobInterstitialAd != null;
    }

    public boolean isFbAdAvailable() {
        return fbInterstitialAd != null && fbInterstitialAd.isAdLoaded() && !fbInterstitialAd.isAdInvalidated();
    }

    public void showAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        showInterstitialAd(activity, onAdLoadInterface);
    }

    public void showInterstitialAd(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        int interstitalAdStatus = taskPreferenceClass.getAdsStatus("InerstialClickCount"); // default 3
        if (interstitalAdStatus <= 0) {
            Log.d(TAG, "🔴 [INTERSTITIAL_AD] Interstitial Ads DISABLED via Firebase (InerstialClickCount=0)");
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        int getClickCount = taskPreferenceClass.getInt("getClickCount") + 1;
        Log.d(TAG, "📊 [INTERSTITIAL_COUNTER] Current Click: " + getClickCount + " / Target: " + interstitalAdStatus);

        if (getClickCount < interstitalAdStatus) {
            taskPreferenceClass.setInt("getClickCount", getClickCount);
            if (!isAdmobAdAvailable()) {
                fetchAdMobAd();
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        // Target reached: Reset counter and attempt display
        taskPreferenceClass.setInt("getClickCount", 0);

        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        if (isAdmobAdAvailable()) {
            Log.d(TAG, "🟢 [INTERSTITIAL_AD] Showing Google Interstitial Ad");
            final boolean[] callbackTriggered = {false};
            
            admobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (!callbackTriggered[0]) {
                        callbackTriggered[0] = true;
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (!callbackTriggered[0]) {
                        callbackTriggered[0] = true;
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                    }
                }
            });
            admobInterstitialAd.show(activity);
        } else if (isFbAdAvailable()) {
            Log.d(TAG, "🟢 [INTERSTITIAL_AD] Showing Facebook Interstitial Ad");
            fbInterstitialAd.show();
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
        } else {
            Log.d(TAG, "⚠️ [INTERSTITIAL_AD] Ad not ready yet. Pre-fetching now and continuing flow.");
            fetchAdMobAd();
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
        }
    }

    public void showFaceBookInterstitial(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        if (isFbAdAvailable() && activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            fbInterstitialAd.show();
        }
        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
    }

    public void showEDitAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        showInterstitialAd(activity, onAdLoadInterface);
    }

    public interface OnAdLoadInterface {
        void onAdClose();
    }
}
