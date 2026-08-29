package com.vehicle.information.trending.rtoexam.rto;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_AppOpenManager;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_InterstitialAdManager;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalContext;


public class MyApplication extends android.app.Application {

    public static boolean isShowingAppOpen = true, isAdsSplash = true;
    public Task_AppOpenManager taskAppOpenManager;
    private Task_InterstitialAdManager taskInterstitialAdManager;
    public static MyApplication mInstance;

    public static void showInterstitialAd(Activity activity, Task_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showAdIfAvailable(activity, onAdLoadInterface);
    }
    public static void showInterstitialAdWithOutCount(Activity activity, Task_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showInterstitialAd(activity, onAdLoadInterface);
    }
    public static void showFaceBookInterstitial(Activity activity, Task_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showFaceBookInterstitial(activity, onAdLoadInterface);
    }
    public static void showEditInterstitialAd(Activity activity, Task_InterstitialAdManager.OnAdLoadInterface onAdLoadInterface) {
        if (BuildConfig.DEBUG) {
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose();
            return;
        }
        ((MyApplication) activity.getApplication()).getInterstitialAdManager().showEDitAdIfAvailable(activity, onAdLoadInterface);
    }

    public Task_InterstitialAdManager getInterstitialAdManager() {
        if (taskInterstitialAdManager == null) {
            taskInterstitialAdManager = new Task_InterstitialAdManager(this);
        }
        return taskInterstitialAdManager;
    }

    public void loadInterstitialAd() {
        if (taskInterstitialAdManager == null)
            taskInterstitialAdManager = new Task_InterstitialAdManager(MyApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Enable verbose OneSignal logging to debug issues if needed.
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//
//        // OneSignal Initialization
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId("83d4adaf-4ae7-4f59-b91a-d0050698af6a");
//        OneSignal.promptForPushNotifications();
//        OneSignal.sendTag("Apps", "Text Art");

        AudienceNetworkAds.initialize(this);
        if (BuildConfig.DEBUG) {
            com.facebook.ads.AdSettings.setTestMode(true);
            com.google.android.gms.ads.RequestConfiguration configuration =
                    new com.google.android.gms.ads.RequestConfiguration.Builder()
                            .setTestDeviceIds(java.util.Arrays.asList(com.google.android.gms.ads.AdRequest.DEVICE_ID_EMULATOR, "012A7B0296D761BF65FF2E7E14112689", "9EB1C89D5458256B2C93F844BAAC93F5"))
                            .build();
            MobileAds.setRequestConfiguration(configuration);
        }

        MobileAds.initialize(this, initializationStatus -> Log.d(" AD", " RTO open ad"));
        taskAppOpenManager = new Task_AppOpenManager(this);
        GlobalContext.initialize(this);
    }

    public static synchronized MyApplication getInstance() {
        MyApplication myApp;
        synchronized (MyApplication.class) {
            myApp = mInstance;
        }
        return myApp;
    }

    public interface OnShowAdCompleteListener {
        void onShowAdComplete();
    }

    public void showAdIfAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (BuildConfig.DEBUG) {
            onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        taskAppOpenManager.showAdIfSplashAvailable(activity, onShowAdCompleteListener);
    }

    public void showAdIfHomeAvailable(@NonNull Activity activity, @NonNull OnShowAdCompleteListener onShowAdCompleteListener) {
        if (BuildConfig.DEBUG) {
            onShowAdCompleteListener.onShowAdComplete();
            return;
        }
        taskAppOpenManager.showAdIfAvailable(activity, onShowAdCompleteListener);
    }

    public void sendRequest() {
        taskAppOpenManager.sendRequest();
    }

    public boolean isAdAvailable() {
        return taskAppOpenManager.isAdAvailable();
    }



}
