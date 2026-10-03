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
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;

public class Task_RewardVideoManager {

    private static final String TAG = "RewardVideoManager";
    private static Task_PreferenceClass taskPreferenceClass;
    private static RewardedAd googleRewardedAd = null;
    private static RewardedInterstitialAd mRewardedInterstitialAd = null;
    private static boolean isLoading = false;
    private static boolean isUserEarnReward = false;
    private static AlertDialog alertDialog;

    public static void preloadRewardAd(final Context context) {
        if (context == null || isRewardAdAvailable() || isLoading) return;
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(context);
        }

        String adUnitId = taskPreferenceClass.getAdsId("GoogleRewardedAd");
        if (adUnitId == null || adUnitId.trim().isEmpty()) {
            // adUnitId = taskPreferenceClass.getAdsId("AdxRewardVideoUnitID");
        }
        if (adUnitId == null || adUnitId.trim().isEmpty()) {
            return;
        }

        isLoading = true;
        Log.d(TAG, "🟢 [REWARD_AD] Pre-loading Rewarded Ad with ID: " + adUnitId);
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context.getApplicationContext(), adUnitId, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                isLoading = false;
                googleRewardedAd = rewardedAd;
                Log.d(TAG, "🎉 [REWARD_AD] Rewarded Ad Pre-loaded Successfully!");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                googleRewardedAd = null;
                Log.e(TAG, "❌ [REWARD_AD] Pre-load Failed: " + loadAdError.getMessage());
                preloadRewardedInterstitialFallback(context);
            }
        });
    }

    private static void preloadRewardedInterstitialFallback(final Context context) {
        if (context == null || mRewardedInterstitialAd != null || isLoading) return;
        String rwInterId = taskPreferenceClass.getAdsId("GoogleInterstialRewardAd");
        if (rwInterId == null || rwInterId.trim().isEmpty()) {
            return;
        }

        isLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedInterstitialAd.load(context.getApplicationContext(), rwInterId, adRequest, new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd ad) {
                isLoading = false;
                mRewardedInterstitialAd = ad;
                Log.d(TAG, "🎉 [REWARD_INTERSTITIAL] Pre-loaded Successfully!");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isLoading = false;
                mRewardedInterstitialAd = null;
                Log.e(TAG, "❌ [REWARD_INTERSTITIAL] Fallback load failed: " + loadAdError.getMessage());
            }
        });
    }

    public static boolean isRewardAdAvailable() {
        return googleRewardedAd != null || mRewardedInterstitialAd != null;
    }

    public static void showRewardVideoAd(final Activity context, final OnRewardAdLoadInterface onAdLoadInterface) {
        if (context == null || context.isFinishing() || context.isDestroyed()) {
            Log.e(TAG, "🔴 [REWARD_AD] Cannot show - Activity is null or destroyed.");
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
            return;
        }

        isUserEarnReward = false;
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(context);
        }

        // Case 1: Rewarded Ad already pre-loaded
        if (googleRewardedAd != null) {
            Log.e(TAG, "🟢 [REWARD_AD] >>> SHOWING pre-loaded Rewarded Ad to user NOW! <<<");
            final RewardedAd adToShow = googleRewardedAd;
            googleRewardedAd = null;

            adToShow.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.e(TAG, "👋 [REWARD_AD] User dismissed the Ad. Reward earned: " + isUserEarnReward);
                    // SMART LOAD: Do NOT preload. Will load only when user clicks reward button again.
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(isUserEarnReward);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Log.e(TAG, "❌ [REWARD_AD] Failed to show: " + adError.getMessage());
                    // SMART LOAD: Do NOT preload. Will load only when user clicks reward button again.
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.e(TAG, "✅ [REWARD_AD] Ad is now VISIBLE on screen! (Impression counted)");
                }
            });

            adToShow.show(context, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    isUserEarnReward = true;
                    Log.e(TAG, "🏆 [REWARD_AD] User EARNED reward: " + rewardItem.getAmount() + " " + rewardItem.getType());
                }
            });
            return;
        }

        // Case 2: Rewarded Interstitial Fallback available
        if (mRewardedInterstitialAd != null) {
            Log.e(TAG, "🟢 [REWARD_AD] >>> SHOWING pre-loaded Rewarded Interstitial to user NOW! <<<");
            final RewardedInterstitialAd adToShow = mRewardedInterstitialAd;
            mRewardedInterstitialAd = null;

            adToShow.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    Log.e(TAG, "👋 [REWARD_AD] User dismissed Rewarded Interstitial. Reward earned: " + isUserEarnReward);
                    // SMART LOAD: Do NOT preload. Will load only when user clicks reward button again.
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(isUserEarnReward);
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    Log.e(TAG, "❌ [REWARD_AD] Rewarded Interstitial failed to show: " + adError.getMessage());
                    // SMART LOAD: Do NOT preload. Will load only when user clicks reward button again.
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    Log.e(TAG, "✅ [REWARD_AD] Rewarded Interstitial is now VISIBLE on screen!");
                }
            });

            adToShow.show(context, rewardItem -> isUserEarnReward = true);
            return;
        }

        // Case 3: Ad not preloaded yet -> show loader and fetch ON-DEMAND
        Log.e(TAG, "📡 [REWARD_AD] No cached ad. User clicked reward button -> Loading ad ON-DEMAND now...");
        String adUnitId = taskPreferenceClass.getAdsId("GoogleRewardedAd");
        if (adUnitId == null || adUnitId.trim().isEmpty()) {
            // adUnitId = taskPreferenceClass.getAdsId("AdxRewardVideoUnitID");
        }
        if (adUnitId == null || adUnitId.trim().isEmpty()) {
            Log.e(TAG, "🔴 [REWARD_AD] No Reward Ad ID found. Skipping.");
            if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
            return;
        }

        showLoadingDialog(context);
        Log.e(TAG, "📡 [REWARD_AD] >>> REQUESTING Ad from AdMob (ID: " + adUnitId + ")");
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, adUnitId, adRequest, new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                dismissLoadingDialog();
                Log.e(TAG, "🎉 [REWARD_AD] ✅ Ad LOADED on-demand! Showing immediately...");
                if (context.isFinishing() || context.isDestroyed()) {
                    if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
                    return;
                }

                rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.e(TAG, "👋 [REWARD_AD] User dismissed on-demand Ad. Reward earned: " + isUserEarnReward);
                        // SMART LOAD: Do NOT preload. Will load only when user clicks reward button again.
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(isUserEarnReward);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.e(TAG, "❌ [REWARD_AD] On-demand ad failed to show: " + adError.getMessage());
                        // SMART LOAD: Do NOT preload. Will load only when user clicks reward button again.
                        if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.e(TAG, "✅ [REWARD_AD] On-demand Ad is now VISIBLE on screen! (Impression counted)");
                    }
                });

                rewardedAd.show(context, rewardItem -> {
                    isUserEarnReward = true;
                    Log.e(TAG, "🏆 [REWARD_AD] User EARNED reward!");
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                dismissLoadingDialog();
                Log.e(TAG, "❌ [REWARD_AD] On-demand load FAILED (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                if (onAdLoadInterface != null) onAdLoadInterface.onAdClose(true);
            }
        });
    }

    private static void showLoadingDialog(Activity activity) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
        dismissLoadingDialog();
        try {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.task_lottie_anim_dialog, null);
            dialogBuilder.setView(dialogView);
            alertDialog = dialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.setCanceledOnTouchOutside(false);
            if (alertDialog.getWindow() != null) {
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            alertDialog.show();
        } catch (Exception ignored) {}
    }

    private static void dismissLoadingDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            try {
                alertDialog.dismiss();
            } catch (Exception ignored) {}
            alertDialog = null;
        }
    }

    public interface OnRewardAdLoadInterface {
        void onAdClose(boolean isWithReward);
        void onAdFail();
    }
}
