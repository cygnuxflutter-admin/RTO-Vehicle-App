package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class Task_RewardVideoManager {
    private static Task_PreferenceClass taskPreferenceClass;
    private static String AD_google_Rw;
    private static AlertDialog alertDialog;
    public static RewardedInterstitialAd mRewardedAd;

    static boolean isUserEarnReward = false;
    public static com.facebook.ads.InterstitialAd interstitialFB;

    private static com.google.android.gms.ads.rewarded.RewardedAd googleRewardedAd;

    public static void showRewardVideoAd(final Activity context, OnRewardAdLoadInterface onAdLoadInterface) {
        if (context == null || context.isFinishing()) return;
        isUserEarnReward = false;
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(context);
        }
        AD_google_Rw = taskPreferenceClass.getAdsId("GoogleRewardedAd");
        if (AD_google_Rw == null || AD_google_Rw.trim().isEmpty()) {
            Log.e("FIREBASE_ADS", "🔴 [REWARD_AD] ID is empty -> Checking ADX/FB or hiding.");
            // We could fall back to ADX/FB here, but for now we just won't load the ad if missing.
            // If the user wants a reward but ad is missing, we must let them through or gracefully handle it.
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true); // Grant reward if no ads are set
            return;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.task_lottie_anim_dialog, null);
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        Log.e("FIREBASE_ADS", "🟢 [REWARD_AD] Loading AdMob Rewarded with ID: " + AD_google_Rw);
        com.google.android.gms.ads.rewarded.RewardedAd.load(context, AD_google_Rw, adRequest, new com.google.android.gms.ads.rewarded.RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull com.google.android.gms.ads.rewarded.RewardedAd rewardedAd) {
                Log.e("FIREBASE_ADS", "🎉 [REWARD_AD] AdMob Rewarded Loaded Successfully!");
                googleRewardedAd = rewardedAd;
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                googleRewardedAd.show(context, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        isUserEarnReward = true;
                    }
                });
                googleRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        googleRewardedAd = null;
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(isUserEarnReward);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.e("FIREBASE_ADS", "❌ [REWARD_AD] AdMob Failed to SHOW: " + adError.getMessage());
                        googleRewardedAd = null;
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("FIREBASE_ADS", "❌ [REWARD_AD] AdMob Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                loadRewardedInterstitialFallback(context, onAdLoadInterface);
            }
        });
    }

    private static void loadRewardedInterstitialFallback(final Activity context, final OnRewardAdLoadInterface onAdLoadInterface) {
        String rwInterId = taskPreferenceClass.getAdsId("GoogleInterstialRewardAd");
        if (rwInterId == null || rwInterId.trim().isEmpty()) {
            fbInterstitial(context, onAdLoadInterface);
            return;
        }
        AdRequest adRequest = new AdRequest.Builder().build();
        Log.e("FIREBASE_ADS", "🟢 [REWARD_INTERSTITIAL] Loading with ID: " + rwInterId);
        RewardedInterstitialAd.load(context, rwInterId, adRequest, new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
                Log.e("FIREBASE_ADS", "🎉 [REWARD_INTERSTITIAL] AdMob Loaded Successfully!");
                mRewardedAd = ad;
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                mRewardedAd.show(context, rewardItem -> isUserEarnReward = true);
                mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        mRewardedAd = null;
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(isUserEarnReward);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.e("FIREBASE_ADS", "❌ [REWARD_INTERSTITIAL] Failed to SHOW: " + adError.getMessage());
                        mRewardedAd = null;
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("FIREBASE_ADS", "❌ [REWARD_INTERSTITIAL] Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                fbInterstitial(context, onAdLoadInterface);
            }
        });
    }

    public static void fbInterstitial(Context context, OnRewardAdLoadInterface onAdLoadInterface) {
        interstitialFB = new com.facebook.ads.InterstitialAd(context, taskPreferenceClass.getAdsId("FbInterstitialAd"));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
//                    Log.e("#1", "" + ad.toString());
                // Interstitial ad displayed callback
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
//                    Log.e("#2", "" + ad.toString());
                // Interstitial dismissed callback
                if (alertDialog != null) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                onAdLoadInterface.onAdClose(true);
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                Log.e("#3", "" + adError.getErrorMessage());
                Log.e("#3_1", "" + adError.getErrorCode());
                // Ad error callback
                if (alertDialog != null) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                onAdLoadInterface.onAdFail();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Show the ad
//                    Log.e("#2", "" + ad.toString());
                if (alertDialog != null) {
                    if (alertDialog.isShowing()) {
                        alertDialog.dismiss();
                    }
                }
                interstitialFB.show();

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        com.facebook.ads.InterstitialAd interstitialAd = interstitialFB;
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }

    public interface OnRewardAdLoadInterface {
        void onAdClose(boolean isWithReward);

        void onAdFail();
    }
}