package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import static androidx.lifecycle.Lifecycle.Event.ON_START;
import static androidx.lifecycle.Lifecycle.Event.ON_STOP;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

import java.util.Date;

public class Task_AppOpenManager implements LifecycleObserver, Application.ActivityLifecycleCallbacks {

    private static final String LOG_TAG = "AppOpenManager";
    private AppOpenAd appOpenAd = null;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final MyApplication myApplication;
    private static boolean isShowingAd = false;
    private Activity currentActivity;
    private long loadTime = 0;
    public static Integer AppOpenAdShow = 1;
    private static Task_PreferenceClass taskPreferenceClass;
    private String AD_UNIT_ID1, AD_UNIT_ID2;
    private static boolean isLoading = false;

    /**
     * Constructor
     */
    public Task_AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        // SMART LOAD: Do NOT preload on app start. Ad will only load when user goes to background.
        Log.e(LOG_TAG, "🟢 [APP_OPEN_AD] Manager initialized. Ad will load ONLY when user goes to background.");
    }

    /**
     * Request an ad
     */
    public void fetchAd() {
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(myApplication);
        }
        int splashPref = taskPreferenceClass.getInt("splashscreen", 1);
        int isAppOpenEnabled = (AppOpenAdShow != null) ? AppOpenAdShow : 0;
        int isForegroundEnabled = taskPreferenceClass.getInt("ForegroundAppOpenAd", 0);
        
        // Only load if either Splash needs App Open OR Foreground needs App Open
        boolean splashNeedsAppOpen = (splashPref == 1 && isAppOpenEnabled == 1);
        boolean foregroundNeedsAppOpen = (isForegroundEnabled == 1);
        
        if (!splashNeedsAppOpen && !foregroundNeedsAppOpen) {
            Log.e(LOG_TAG, "🔴 [APP_OPEN_AD] Ad disabled (Splash AppOpen=" + isAppOpenEnabled + ", Foreground=" + isForegroundEnabled + "). Skipping load. NO request sent.");
            return;
        }
        // Have unused ad, no need to fetch another.
        if (isAdAvailable()) {
            Log.e(LOG_TAG, "⚡ [APP_OPEN_AD] Ad already cached and ready. No new request needed.");
            return;
        }
        if (isLoading) {
            Log.e(LOG_TAG, "⏳ [APP_OPEN_AD] Ad is already loading. Skipping duplicate request.");
            return;
        }
        isLoading = true;

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) { isLoading = false;
                Task_AppOpenManager.this.appOpenAd = ad;
                Task_AppOpenManager.this.loadTime = (new Date()).getTime();
                Log.e(LOG_TAG, "🎉 [APP_OPEN_AD] ✅ Ad LOADED successfully! Ready to show on next foreground.");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) { isLoading = false;
                Log.e(LOG_TAG, "❌ [APP_OPEN_AD] AdMob Failed (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage() + " -> Trying AdX...");
                // fetchAdX();
            }
        };

        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(myApplication);
        }
        AD_UNIT_ID1 = taskPreferenceClass.getAdsId("GoogleAppopenAd");
        if (AD_UNIT_ID1 == null || AD_UNIT_ID1.trim().isEmpty()) {
            isLoading = false;
            Log.e(LOG_TAG, "⚠️ [APP_OPEN_AD] No AdMob ID found. Trying AdX...");
            // fetchAdX();
            return;
        }
        AD_UNIT_ID2 = taskPreferenceClass.getAdsId("AdxAppOpenID");
        AdRequest request = getAdRequest();
        Log.e(LOG_TAG, "📡 [APP_OPEN_AD] >>> REQUESTING Ad from AdMob (ID: " + AD_UNIT_ID1 + ")");
        AppOpenAd.load(myApplication, AD_UNIT_ID1, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    public void fetchAdX() {
        if (isAdAvailable() || isLoading) { return; }
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(myApplication);
        }
        AD_UNIT_ID2 = taskPreferenceClass.getAdsId("AdxAppOpenID");
        if (AD_UNIT_ID2 == null || AD_UNIT_ID2.trim().isEmpty()) {
            isLoading = false;
            return;
        }

        isLoading = true;
        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull AppOpenAd ad) {
                isLoading = false;
                Task_AppOpenManager.this.appOpenAd = ad;
                Task_AppOpenManager.this.loadTime = new Date().getTime();
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                Log.e(LOG_TAG, "AdX AppOpenAd failed to load: " + loadAdError.getMessage());
            }
        };
        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication, AD_UNIT_ID2, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    /**
     * Creates and returns ad request.
     */
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    /**
     * Utility method that checks if ad exists and can be shown.
     */
    public boolean isAdAvailable() {
        return appOpenAd != null && wasLoadTimeLessThanNHoursAgo(4);
    }

    public void sendRequest() {
        if (!isShowingAd && isAdAvailable()) {
            // Ad available
        } else {
            fetchAd();
        }
    }

    /**
     * Shows the ad if one isn't already showing.
     */
    public void showAdIfAvailable() {
        if (currentActivity == null || currentActivity.isFinishing() || currentActivity.isDestroyed()) {
            Log.e(LOG_TAG, "?? [APP_OPEN_AD] Cannot show - Activity is null or destroyed.");
            return;
        }
        
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(myApplication);
        }

        int isForegroundEnabled = taskPreferenceClass.getInt("ForegroundAppOpenAd", 0);

        if (!MyApplication.isShowingAppOpen || isForegroundEnabled == 0) {
            Log.e(LOG_TAG, "⚠️ [APP_OPEN_AD] Foreground: ForegroundAppOpenAd disabled or isShowingAppOpen=false. Skipping.");
            return;
        }
        
        if (!isShowingAd && isAdAvailable()) {
            Log.e(LOG_TAG, "?? [APP_OPEN_AD] >>> SHOWING App Open Ad NOW! <<<");
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Log.e(LOG_TAG, "👋 [APP_OPEN_AD] User dismissed the Ad. Preloading next one...");
                    Task_AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Log.e(LOG_TAG, "❌ [APP_OPEN_AD] Failed to show: " + adError.getMessage());
                    Task_AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                    Log.e(LOG_TAG, "? [APP_OPEN_AD] Ad is now VISIBLE on screen!");
                }
            };

            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(currentActivity);
        } else {
            Log.e(LOG_TAG, "?? [APP_OPEN_AD] Foreground: Ad not ready. Will load on next background.");
        }
    }

    public void showAdIfSplashAvailable(@NonNull final Activity activity, @NonNull MyApplication.OnShowAdCompleteListener onShowAdCompleteListener) {
        if (activity.isFinishing() || activity.isDestroyed()) {
            onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(myApplication);
        }

        int isAppOpenEnabled = (AppOpenAdShow != null) ? AppOpenAdShow : 0;
        int isFallbackEnabled = taskPreferenceClass.getInt("AppOpenFallbackInterstitial", 0);

        if (isAppOpenEnabled == 0 && isFallbackEnabled == 1) {
            Log.e(LOG_TAG, "?? [APP_OPEN_AD] Splash: AppOpen disabled, using Interstitial Fallback immediately...");
            MyApplication.forceShowInterstitialAd(activity, new Task_InterstitialAdManager.OnAdLoadInterface() {
                @Override
                public void onAdClose() { onShowAdCompleteListener.onShowAdComplete(); }
            });
            return;
        }

        if (isAppOpenEnabled == 0) {
            onShowAdCompleteListener.onShowAdComplete();
            return;
        }

        if (!isShowingAd && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Task_AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                    onShowAdCompleteListener.onShowAdComplete();
                }
                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Task_AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                    if (isFallbackEnabled == 1) {
                        MyApplication.forceShowInterstitialAd(activity, new Task_InterstitialAdManager.OnAdLoadInterface() {
                            @Override
                            public void onAdClose() { onShowAdCompleteListener.onShowAdComplete(); }
                        });
                    } else {
                        onShowAdCompleteListener.onShowAdComplete();
                    }
                }
                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(activity);
        } else {
            AD_UNIT_ID1 = taskPreferenceClass.getAdsId("GoogleAppopenAd");
            if (AD_UNIT_ID1 == null || AD_UNIT_ID1.trim().isEmpty()) {
                if (isFallbackEnabled == 1) {
                    MyApplication.forceShowInterstitialAd(activity, new Task_InterstitialAdManager.OnAdLoadInterface() {
                        @Override
                        public void onAdClose() { onShowAdCompleteListener.onShowAdComplete(); }
                    });
                } else {
                    onShowAdCompleteListener.onShowAdComplete();
                }
                return;
            }

            loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AppOpenAd ad) {
                    isLoading = false;
                    Log.e(LOG_TAG, "🎉 [APP_OPEN_AD] Splash AppOpen Loaded Successfully!");
                    Task_AppOpenManager.this.appOpenAd = ad;
                    Task_AppOpenManager.this.loadTime = (new Date()).getTime();

                    if (activity.isFinishing() || activity.isDestroyed()) {
                        onShowAdCompleteListener.onShowAdComplete();
                        return;
                    }

                    FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Task_AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                            onShowAdCompleteListener.onShowAdComplete();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            Log.e(LOG_TAG, "❌ [APP_OPEN_AD] Failed to show full screen content: " + adError.getMessage());
                            Task_AppOpenManager.this.appOpenAd = null;
                            isShowingAd = false;
                            fetchAd();
                            onShowAdCompleteListener.onShowAdComplete();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            isShowingAd = true;
                        }
                    };
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
                    appOpenAd.show(activity);
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    isLoading = false;
                    Log.e(LOG_TAG, "❌ [APP_OPEN_AD] Splash AppOpen failed to load: " + loadAdError.getMessage());
                    if (isFallbackEnabled == 1) {
                        Log.e(LOG_TAG, "🔄 [APP_OPEN_AD] Fallback is ON. Trying Interstitial Fallback...");
                        MyApplication.forceShowInterstitialAd(activity, new Task_InterstitialAdManager.OnAdLoadInterface() {
                            @Override
                            public void onAdClose() { onShowAdCompleteListener.onShowAdComplete(); }
                        });
                    } else {
                        onShowAdCompleteListener.onShowAdComplete();
                    }
                }
            };
            AdRequest request = getAdRequest();
            if (isLoading) {
                Log.e(LOG_TAG, "⏳ [APP_OPEN_AD] Splash: Already loading, skipping duplicate request.");
                onShowAdCompleteListener.onShowAdComplete();
                return;
            }
            isLoading = true;
            AppOpenAd.load(myApplication, AD_UNIT_ID1, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
        }
    }

    public void showAdIfAvailable(@NonNull final Activity activity, @NonNull MyApplication.OnShowAdCompleteListener onShowAdCompleteListener) {
        if (activity.isFinishing() || activity.isDestroyed()) {
            onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        if (!isShowingAd && isAdAvailable()) {
            FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    Task_AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                    onShowAdCompleteListener.onShowAdComplete();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    Task_AppOpenManager.this.appOpenAd = null;
                    isShowingAd = false;
                    fetchAd();
                    onShowAdCompleteListener.onShowAdComplete();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    isShowingAd = true;
                }
            };
            appOpenAd.setFullScreenContentCallback(fullScreenContentCallback);
            appOpenAd.show(activity);
        } else {
            fetchAd();
            onShowAdCompleteListener.onShowAdComplete();
        }
    }

    /**
     * ActivityLifecycleCallback methods
     */
    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        if (currentActivity == activity) {
            currentActivity = null;
        }
    }

    @OnLifecycleEvent(ON_START)
    public void onStart() {
        Log.e(LOG_TAG, "🔵 [APP_OPEN_AD] ======================== FOREGROUND DETECTED ========================");
        Log.e(LOG_TAG, "🔵 [APP_OPEN_AD] isAdsSplash=" + MyApplication.isAdsSplash + " | adAvailable=" + isAdAvailable() + " | isShowingAd=" + isShowingAd);
        if (!MyApplication.isAdsSplash) {
            showAdIfAvailable();
        } else {
            Log.e(LOG_TAG, "🔵 [APP_OPEN_AD] Splash screen active. Skipping App Open Ad.");
        }
    }

    @OnLifecycleEvent(ON_STOP)
    public void onStop() {
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(myApplication);
        }
        int isForegroundEnabled = taskPreferenceClass.getInt("ForegroundAppOpenAd", 0);
        if (isForegroundEnabled == 1) {
            Log.e(LOG_TAG, "🟠 [APP_OPEN_AD] ======================== BACKGROUND DETECTED ========================");
            Log.e(LOG_TAG, "🟠 [APP_OPEN_AD] ForegroundAppOpenAd=1. Loading Ad for next foreground...");
            fetchAd();
        } else {
            Log.e(LOG_TAG, "🟠 [APP_OPEN_AD] BACKGROUND detected but ForegroundAppOpenAd=0. NO request sent.");
        }
    }

    /**
     * Utility method to check if ad was loaded more than n hours ago.
     */
    private boolean wasLoadTimeLessThanNHoursAgo(long numHours) {
        long dateDifference = (new Date()).getTime() - this.loadTime;
        long numMilliSecondsPerHour = 3600000;
        return (dateDifference < (numMilliSecondsPerHour * numHours));
    }
}









