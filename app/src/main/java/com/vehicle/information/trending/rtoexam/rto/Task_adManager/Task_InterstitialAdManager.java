package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
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

    private final String admobInterstitialAdId, fbInterstitialAdId;
    private final Context context;
    private final Task_PreferenceClass taskPreferenceClass;
    private final String adXInterstitialAdId;
    private InterstitialAd admobInterstitialAd;
    private com.facebook.ads.InterstitialAd fbInterstitialAd;
    private OnAdLoadInterface onAdLoadInterface;
    private boolean isFailed = false;
    private ProgressDialog progressDialog;
    private boolean isLoading = false;

    public Task_InterstitialAdManager(Context context) {
        this.context = context;
        taskPreferenceClass = new Task_PreferenceClass(this.context);
        String rawAdmob = taskPreferenceClass.getAdsId("GoogleInterstitialAd");
        admobInterstitialAdId = (rawAdmob != null) ? rawAdmob.trim() : "";
        String rawAdX = taskPreferenceClass.getAdsId("AdxInterstitalAdunitID");
        adXInterstitialAdId = (rawAdX != null) ? rawAdX.trim() : "";
        String rawFb = taskPreferenceClass.getAdsId("FbInterstitialAd");
        fbInterstitialAdId = (rawFb != null) ? rawFb.trim() : "";

        Log.e("FIREBASE_ADS", "🔹 Interstitial Config: ID=" + admobInterstitialAdId + " | Clicks=" + taskPreferenceClass.getAdsStatus("InerstialClickCount"));
        if (!admobInterstitialAdId.isEmpty() && taskPreferenceClass.getAdsStatus("InerstialClickCount") > 0) {
            fetchAdMobAd();
        }
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
            public void onInterstitialDisplayed(Ad ad) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // fetchAdMobAd();
                fetchFbAd();
                if (onAdLoadInterface != null) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    onAdLoadInterface.onAdClose();
                }
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                isLoading = false;
                isFailed = true;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                isLoading = false;
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        };

        fbInterstitialAd.loadAd(fbInterstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());

    }

    public void fetchAdMobAd() {
        if (admobInterstitialAdId == null || admobInterstitialAdId.trim().isEmpty() || taskPreferenceClass.getAdsStatus("InerstialClickCount") <= 0) {
            return;
        }

        if (isAdmobAdAvailable() || isLoading) {
            return;
        }

        isLoading = true;
        Log.e("FIREBASE_ADS", "🟢 [INTERSTITIAL_AD] Loading AdMob Interstitial with ID: " + admobInterstitialAdId);
        InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                isLoading = false;
                Log.e("FIREBASE_ADS", "🎉 [INTERSTITIAL_AD] AdMob Interstitial Loaded Successfully!");
                admobInterstitialAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                Log.e("FIREBASE_ADS", "❌ [INTERSTITIAL_AD] AdMob Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
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
            fetchFbAd();
            return;
        }

        isLoading = true;
        Log.e("FIREBASE_ADS", "dYY [INTERSTITIAL_AD] Loading AdX Interstitial with ID: " + adXInterstitialAdId);
        InterstitialAdLoadCallback loadCallback = new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd ad) {
                isLoading = false;
                Log.e("FIREBASE_ADS", "dYZ% [INTERSTITIAL_AD] AdX Interstitial Loaded Successfully!");
                admobInterstitialAd = ad;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                Log.e("FIREBASE_ADS", "?O [INTERSTITIAL_AD] AdX Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                fetchFbAd();
            }
        };

        AdRequest request = getAdRequest();
        InterstitialAd.load(context, adXInterstitialAdId, request, loadCallback);
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
        this.onAdLoadInterface = onAdLoadInterface;

        int interstitalAdStatus = taskPreferenceClass.getAdsStatus("InerstialClickCount");
        if (interstitalAdStatus <= 0) {
            Log.e("FIREBASE_ADS", "🔴 [INTERSTITIAL_AD] Interstitial Ads DISABLED via Firebase (InerstialClickCount=0)");
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        int currentClick = taskPreferenceClass.getInt("getClickCount", 0) + 1;
        Log.e("FIREBASE_ADS", "📊 [INTERSTITIAL_COUNTER] Click " + currentClick + " / " + interstitalAdStatus + " (Firebase Target)");

        if (currentClick < interstitalAdStatus) {
            taskPreferenceClass.setInt("getClickCount", currentClick);
            if (!isAdmobAdAvailable()) {
                fetchAdMobAd();
            }
            if (onAdLoadInterface != null) {
                onAdLoadInterface.onAdClose();
            }
            return;
        }

        // Target reached: Reset counter and show ad
        taskPreferenceClass.setInt("getClickCount", 0);

        if (isAdmobAdAvailable()) {
            Log.e("FIREBASE_ADS", "🟢 [INTERSTITIAL_AD] Showing Google Interstitial Ad (" + admobInterstitialAdId + ")");
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (Task_InterstitialAdManager.this.onAdLoadInterface != null) {
                        Task_InterstitialAdManager.this.onAdLoadInterface.onAdClose();
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (Task_InterstitialAdManager.this.onAdLoadInterface != null) {
                        Task_InterstitialAdManager.this.onAdLoadInterface.onAdClose();
                    }
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            };
            admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
            showAdWithLoader(activity);
        } else if (isFbAdAvailable()) {
            Log.e("FIREBASE_ADS", "🟢 [INTERSTITIAL_AD] Showing Facebook Interstitial Ad");
            showAdWithLoader(activity);
        } else {
            Log.e("FIREBASE_ADS", "⚠️ [INTERSTITIAL_AD] Ad not ready yet. Pre-fetching now and proceeding.");
            fetchAdMobAd();
            if (onAdLoadInterface != null) {
                onAdLoadInterface.onAdClose();
            }
        }
    }
//    public void showAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) { // if google sec code implement open this method and comment above method
//        this.onAdLoadInterface = onAdLoadInterface;
//
//        if (isFailed) {
//            isFailed = false;
//            fetchAdMobAd();
//            fetchFbAd();
//     if (progressDialog != null && progressDialog.isShowing()) {
//        progressDialog.dismiss();
//    }
//            onAdLoadInterface.onAdClose();
//            return;
//        }
//
//        int interstitalAdStatus = taskPreferenceClass.getAdsStatus("InerstialClickCount");
//        int googleAdsTime =  taskPreferenceClass.getAdsStatus("GoogleAdsTime");//this flag for Google ads stop 20 sec after ones show
//
//        int getClickCount = taskPreferenceClass.getInt("getClickCount");
//        if (getClickCount < interstitalAdStatus) {
//            taskPreferenceClass.setInt("getClickCount", getClickCount + 1);
//     if (progressDialog != null && progressDialog.isShowing()) {
//        progressDialog.dismiss();
//    }
//            onAdLoadInterface.onAdClose();
//            return;
//        }
//
//        taskPreferenceClass.setInt("getClickCount", 0);
//
//        long currentTime = System.currentTimeMillis();
//        long lastGoogleAdShownTime = taskPreferenceClass.getLong("lastGoogleAdShownTime");
//        long timeDifference = currentTime - lastGoogleAdShownTime;
//
//        if (timeDifference < googleAdsTime * 1000) {
//            if (isFbAdAvailable()) {
//                showAdWithLoader(activity);
//            } else {
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//                onAdLoadInterface.onAdClose();
//            }
//        } else {
//            taskPreferenceClass.setLong("lastGoogleAdShownTime", currentTime);
//
//            if (isAdmobAdAvailable()) {
//                FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
//                    @Override
//                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
//                        super.onAdFailedToShowFullScreenContent(adError);
//                        admobInterstitialAd = null;
//                        isFailed = true;
//                        if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//                        onAdLoadInterface.onAdClose();
//                    }
//
//                    @Override
//                    public void onAdShowedFullScreenContent() {
//                        super.onAdShowedFullScreenContent();
//                    }
//
//                    @Override
//                    public void onAdDismissedFullScreenContent() {
//                        super.onAdDismissedFullScreenContent();
//                        admobInterstitialAd = null;
//                        fetchAdMobAd();
//     if (progressDialog != null && progressDialog.isShowing()) {
//        progressDialog.dismiss();
//    }
//                        onAdLoadInterface.onAdClose();
//                    }
//
//                    @Override
//                    public void onAdImpression() {
//                        super.onAdImpression();
//                    }
//                };
//                admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
//                showAdWithLoader(activity);
//            } else if (isFbAdAvailable()) {
//                showAdWithLoader(activity);
//            } else {
//            if (progressDialog != null && progressDialog.isShowing()) {
//                progressDialog.dismiss();
//            }
//                onAdLoadInterface.onAdClose();
//            }
//        }
//    }

    public void showInterstitialAd(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;

        if (isFailed) {
            isFailed = false;
            fetchAdMobAd();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        int interstitalAdStatus = taskPreferenceClass.getAdsStatus("InerstialClickCount"); // default 3
        if (interstitalAdStatus <= 0) {
            Log.e("FIREBASE_ADS", "🔴 [INTERSTITIAL_AD] Interstitial Ads are DISABLED via Firebase (InerstialClickCount=0)");
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        int getClickCount = taskPreferenceClass.getInt("getClickCount") + 1;
        Log.e("FIREBASE_ADS", "📊 [INTERSTITIAL_COUNTER] Current Click: " + getClickCount + " / Firebase Target Count: " + interstitalAdStatus);

        if (getClickCount < interstitalAdStatus) {
            taskPreferenceClass.setInt("getClickCount", getClickCount);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        taskPreferenceClass.setInt("getClickCount", 0);
        Log.e("FIREBASE_ADS", "🟢 [INTERSTITIAL_AD] Target Click Count Reached (" + interstitalAdStatus + "). Showing Interstitial Ad: " + admobInterstitialAdId);

        if (isAdmobAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    admobInterstitialAd = null;
                    isFailed = true;
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (Task_InterstitialAdManager.this.onAdLoadInterface != null) {
                        Task_InterstitialAdManager.this.onAdLoadInterface.onAdClose();
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (Task_InterstitialAdManager.this.onAdLoadInterface != null) {
                        Task_InterstitialAdManager.this.onAdLoadInterface.onAdClose();
                    }
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            };
            admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
            showAdWithLoader(activity);
        } else if (isFbAdAvailable()) {
            showAdWithLoader(activity);
        } else {
            fetchAdMobAd();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
        }
    }

    public void showFaceBookInterstitial(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;
        if (com.vehicle.information.trending.rtoexam.rto.BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        if (isFbAdAvailable()) {
            showAdWithLoader(activity);
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            onAdLoadInterface.onAdClose();
        }

    }

    public void showEDitAdIfAvailable(Activity activity, OnAdLoadInterface onAdLoadInterface) {
        this.onAdLoadInterface = onAdLoadInterface;
        if (com.vehicle.information.trending.rtoexam.rto.BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }

        if (isFailed) {
            isFailed = false;
            fetchAdMobAd();
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            onAdLoadInterface.onAdClose();
            return;
        }

        int interstitalAdStatus = taskPreferenceClass.getAdsStatus("EditScreenAdCount");

        int getClickCount = taskPreferenceClass.getInt("getEDitClickCount") + 1;
        if (getClickCount < interstitalAdStatus) {
            taskPreferenceClass.setInt("getEDitClickCount", getClickCount);
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            onAdLoadInterface.onAdClose();
            return;
        }

        taskPreferenceClass.setInt("getEDitClickCount", 0);

        if (isAdmobAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    admobInterstitialAd = null;
                    isFailed = true;
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    onAdLoadInterface.onAdClose();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    admobInterstitialAd = null;
                    fetchAdMobAd();
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    onAdLoadInterface.onAdClose();
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }
            };
            admobInterstitialAd.setFullScreenContentCallback(fullScreenContentCallback);
            showAdWithLoader(activity);
        } else if (isFbAdAvailable()) {
            showAdWithLoader(activity);
        } else {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            onAdLoadInterface.onAdClose();
        }

    }

    public interface OnAdLoadInterface {
        void onAdClose();
    }


    private void showAdWithLoader(final Activity activity) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Loading Ad...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isAdmobAdAvailable()) {
                    admobInterstitialAd.show(activity);
                } else if (isFbAdAvailable()) {
                    fbInterstitialAd.show();
                } else {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                }
                // Safety fallback: if ad fails to show or dismiss properly within 3 seconds, unlock UI
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
                        }
                    }
                }, 3000);
            }
        }, 1200);
    }
}


