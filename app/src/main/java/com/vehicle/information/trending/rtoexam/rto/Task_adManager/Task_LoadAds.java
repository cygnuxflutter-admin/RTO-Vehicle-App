package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.util.UUID;

public class Task_LoadAds {

    private static final String TAG = "Task_LoadAds";

    public static void loadAdmobBannerAd(final Activity activity, final RelativeLayout mainLayout) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || mainLayout == null) return;
        Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(activity);
        if (taskPreferenceClass.getInt("BannerAdShow", 1) == 0) {
            Log.d(TAG, "🔴 [BANNER_AD] Banner Ads disabled via Firebase (BannerAdShow=0)");
            mainLayout.setVisibility(View.GONE);
            return;
        }

        String bannerAdunitID = taskPreferenceClass.getAdsId("GoogleBannerAd");
        if (bannerAdunitID == null || bannerAdunitID.trim().isEmpty()) {
            loadADXBannerAd(activity, mainLayout);
            return;
        }

        mainLayout.setVisibility(View.VISIBLE);
        mainLayout.removeAllViews();
        final View loadingView = getBannerLoadingView(activity, mainLayout);
        mainLayout.addView(loadingView);

        final AdView adView = new AdView(activity);
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        adView.setAdUnitId(bannerAdunitID);

        final RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e(TAG, "❌ [BANNER_AD] AdMob Banner Failed (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                loadADXBannerAd(activity, mainLayout);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "🎉 [BANNER_AD] AdMob Banner Loaded Successfully!");
                if (activity.isFinishing() || activity.isDestroyed()) {
                    adView.destroy();
                    return;
                }
                mainLayout.removeAllViews();
                mainLayout.addView(adView, bannerParameters);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private static void loadADXBannerAd(final Activity activity, final RelativeLayout mainLayout) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || mainLayout == null) return;
        Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(activity);
        String adxBannerAdunitID = taskPreferenceClass.getAdsId("AdxBannerUnitID");
        if (adxBannerAdunitID == null || adxBannerAdunitID.trim().isEmpty()) {
            adxBannerAdunitID = taskPreferenceClass.getAdsId("AdxBannerAdunitID");
        }
        if (adxBannerAdunitID == null || adxBannerAdunitID.trim().isEmpty()) {
            mainLayout.removeAllViews();
            mainLayout.setVisibility(View.GONE);
            return;
        }

        final AdView adView = new AdView(activity);
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        adView.setAdUnitId(adxBannerAdunitID);

        final RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e(TAG, "❌ [BANNER_AD] AdX Banner Failed (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                mainLayout.removeAllViews();
                mainLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "🎉 [BANNER_AD] AdX Banner Loaded Successfully!");
                if (activity.isFinishing() || activity.isDestroyed()) {
                    adView.destroy();
                    return;
                }
                mainLayout.removeAllViews();
                mainLayout.addView(adView, bannerParameters);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    private static View getBannerLoadingView(Activity activity, ViewGroup parent) {
        View adView = LayoutInflater.from(activity).inflate(R.layout.task_banner_ad_layout_loading, parent, false);
        ShimmerFrameLayout shimmerLayout = adView.findViewById(R.id.shimmerLayout);
        if (shimmerLayout != null) {
            shimmerLayout.startShimmer();
        }
        return adView;
    }

    private static AdSize getAdSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }

    public static void loadCollapsibleBanner(final Activity activity, String str, final FrameLayout mainLayout, final RelativeLayout relativeLayout, final ShimmerFrameLayout shimmer_view_container) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed() || mainLayout == null) return;
        Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(activity);
        if (taskPreferenceClass.getInt("BannerAdShow", 1) == 0) {
            if (relativeLayout != null) relativeLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.GONE);
            return;
        }

        String collapsibleBannerID = taskPreferenceClass.getAdsId("CollapsibleBannerID");
        if (collapsibleBannerID == null || collapsibleBannerID.trim().isEmpty()) {
            collapsibleBannerID = taskPreferenceClass.getAdsId("GoogleBannerAd");
        }
        if (collapsibleBannerID == null || collapsibleBannerID.trim().isEmpty()) {
            collapsibleBannerID = taskPreferenceClass.getAdsId("AdxBannerUnitID");
        }
        if (collapsibleBannerID == null || collapsibleBannerID.trim().isEmpty()) {
            collapsibleBannerID = taskPreferenceClass.getAdsId("AdxBannerAdunitID");
        }
        if (collapsibleBannerID == null || collapsibleBannerID.trim().isEmpty()) {
            if (relativeLayout != null) relativeLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.GONE);
            return;
        }

        if (shimmer_view_container != null) {
            shimmer_view_container.setVisibility(View.VISIBLE);
            shimmer_view_container.startShimmer();
        }
        if (relativeLayout != null) relativeLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.VISIBLE);

        final AdView adView = new AdView(activity);
        AdSize adSize = getAdSize(activity, mainLayout);
        adView.setAdSize(adSize);
        adView.setAdUnitId(collapsibleBannerID);

        Bundle extras = new Bundle();
        extras.putString("collapsible", str != null ? str : "top");
        extras.putString("collapsible_request_id", UUID.randomUUID().toString());
        AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e(TAG, "❌ [COLLAPSIBLE_BANNER] Failed to load: " + loadAdError.getMessage());
                if (shimmer_view_container != null) {
                    shimmer_view_container.stopShimmer();
                    shimmer_view_container.setVisibility(View.GONE);
                }
                mainLayout.removeAllViews();
                mainLayout.setVisibility(View.GONE);
                if (relativeLayout != null) relativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.d(TAG, "🎉 [COLLAPSIBLE_BANNER] Collapsible Banner Loaded Successfully!");
                if (activity.isFinishing() || activity.isDestroyed()) {
                    adView.destroy();
                    return;
                }
                if (shimmer_view_container != null) {
                    shimmer_view_container.stopShimmer();
                    shimmer_view_container.setVisibility(View.GONE);
                }
                mainLayout.removeAllViews();
                mainLayout.addView(adView);
                mainLayout.setVisibility(View.VISIBLE);
            }
        });

        adView.loadAd(adRequest);
    }

    private static AdSize getAdSize(Activity activity, FrameLayout mainLayout) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;
        float adWidthPixels = mainLayout.getWidth();
        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth);
    }
}
