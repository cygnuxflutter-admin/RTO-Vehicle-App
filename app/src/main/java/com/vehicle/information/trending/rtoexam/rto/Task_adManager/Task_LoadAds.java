package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;

import java.util.UUID;

public class Task_LoadAds {

    public static ShimmerFrameLayout shimmerFrameLayout;

    public static void loadAdmobBannerAd(Activity activity, RelativeLayout mainLayout) {
        if (activity == null || mainLayout == null) return;
        Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(activity);
        if (taskPreferenceClass.getInt("BannerAdShow", 0) == 0) {
            Log.e("FIREBASE_ADS", "🔴 [BANNER_AD] Banner Ads are DISABLED via Firebase (BannerAdShow=0)");
            mainLayout.setVisibility(View.GONE);
            mainLayout.removeAllViews();
            return;
        }

        String bannerAdunitID = taskPreferenceClass.getAdsId("GoogleBannerAd"); 
        if (bannerAdunitID == null || bannerAdunitID.trim().isEmpty()) { 
            Log.e("FIREBASE_ADS", "🔴 [BANNER_AD] No GoogleBannerAd ID in Firebase -> Checking ADX or Hiding");
            loadADXBannerAd(activity, mainLayout);
            return;
        }
        mainLayout.setVisibility(View.VISIBLE);
        mainLayout.removeAllViews();
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT);
        mainLayout.addView(getBannerView(activity));
        Log.e("FIREBASE_ADS", "🟢 [BANNER_AD] Loading Banner Ad with ID: " + bannerAdunitID);

        AdView adView = new AdView(activity);
            AdSize adSize = getAdSize(activity);
            adView.setAdSize(adSize);
            adView.setAdUnitId(bannerAdunitID);

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e("FIREBASE_ADS", "❌ [BANNER_AD] AdMob Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                    loadADXBannerAd(activity, mainLayout);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("FIREBASE_ADS", "🎉 [BANNER_AD] AdMob Banner Loaded Successfully!");
                    mainLayout.removeAllViews();
                    mainLayout.addView(adView, bannerParameters);
                }
            });

        }
    private static void loadADXBannerAd(Activity activity, RelativeLayout mainLayout) {
        String AdxBannerAdunitID = new Task_PreferenceClass(activity).getAdsId("AdxBannerAdunitID");
        if (AdxBannerAdunitID == null || AdxBannerAdunitID.trim().isEmpty()) {
            loadFBBannerAd(activity, mainLayout);
            return;
        }

        AdView adView = new AdView(activity);
        AdSize adSize = getAdSize(activity);
        adView.setAdSize(adSize);
        adView.setAdUnitId(AdxBannerAdunitID);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT);

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("FIREBASE_ADS", "❌ [BANNER_AD] AdMob Failed to load (Code " + loadAdError.getCode() + "): " + loadAdError.getMessage());
                loadFBBannerAd(activity, mainLayout);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("FIREBASE_ADS", "🎉 [BANNER_AD] AdMob Banner Loaded Successfully!");
                mainLayout.setVisibility(View.VISIBLE);
                mainLayout.removeAllViews();
                mainLayout.addView(adView, bannerParameters);
            }
        });
    }

    private static void loadFBBannerAd(Activity activity, RelativeLayout mainLayout) {
        String fbBannerAdunitID = new Task_PreferenceClass(activity).getAdsId("FbBannerAd");
        if (fbBannerAdunitID == null || fbBannerAdunitID.trim().isEmpty()) {
            Log.e("FIREBASE_ADS", "🔴 [BANNER_AD] No banner ads available from Firebase -> Hiding container");
            mainLayout.removeAllViews();
            mainLayout.setVisibility(View.GONE);
            return;
        }
        com.facebook.ads.AdView fbBannerView = new com.facebook.ads.AdView(activity, fbBannerAdunitID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mainLayout.setGravity(Gravity.BOTTOM);

        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                Log.e("FIREBASE_ADS", "🔴 [BANNER_AD] All banner sources failed -> Hiding ad container");
                mainLayout.removeAllViews();
                mainLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                mainLayout.setVisibility(View.VISIBLE);
                mainLayout.removeAllViews();
                mainLayout.addView(fbBannerView);
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };

        fbBannerView.loadAd(fbBannerView.buildLoadAdConfig().withAdListener(adListener).build());

    }
    private static View getBannerView(Activity activity) {
        View adView =  LayoutInflater.from(activity).inflate(R.layout.task_banner_ad_layout_loading, null);
        ShimmerFrameLayout shimmerLayout = adView.findViewById(R.id.shimmerLayout);

        shimmerLayout.startShimmer(); // Start the shimmer effect
        shimmerLayout.setVisibility(View.VISIBLE);
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


    public static void loadCollapsibleBanner(Activity activity, String str, FrameLayout mainLayout, RelativeLayout relativeLayout, ShimmerFrameLayout shimmer_view_container) {
        if (activity == null || mainLayout == null) return;
        Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(activity);
        if (taskPreferenceClass.getInt("BannerAdShow", 1) == 0) {
            Log.e("FIREBASE_ADS", "🔴 [COLLAPSIBLE_BANNER] Banner Ads are DISABLED via Firebase (BannerAdShow=0)");
            if (relativeLayout != null) relativeLayout.setVisibility(View.GONE);
            mainLayout.setVisibility(View.GONE);
            return;
        }

        String CollapsiblebannerID = taskPreferenceClass.getAdsId("CollapsibleBannerID");
        if (taskPreferenceClass == null) {
            taskPreferenceClass = new Task_PreferenceClass(activity);
        }
        CollapsiblebannerID = taskPreferenceClass.getAdsId("CollapsibleBannerID");
        if (CollapsiblebannerID == null || CollapsiblebannerID.trim().isEmpty()) {
            CollapsiblebannerID = taskPreferenceClass.getAdsId("GoogleBannerAd");
        }
        if (CollapsiblebannerID == null || CollapsiblebannerID.trim().isEmpty()) {
            CollapsiblebannerID = taskPreferenceClass.getAdsId("AdxBannerAdunitID");
        }
        if (CollapsiblebannerID == null || CollapsiblebannerID.trim().isEmpty()) {
            return;
        }
        Log.e("FIREBASE_ADS", "🟢 [COLLAPSIBLE_BANNER] Loading Collapsible Banner with ID: " + CollapsiblebannerID);

        shimmerFrameLayout = shimmer_view_container;
        mainLayout.removeAllViews();
        if (relativeLayout != null) relativeLayout.setVisibility(View.VISIBLE);
        mainLayout.setVisibility(View.VISIBLE);

        AdView adView = new AdView(activity);
        AdSize adSize = getAdSize(activity, mainLayout);
        adView.setAdSize(adSize);
        adView.setAdUnitId(CollapsiblebannerID);
        Bundle extras = new Bundle();
        extras.putString("collapsible", str != null ? str : "top");
        extras.putString("collapsible_request_id", UUID.randomUUID().toString());
        AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();

        try {
            adView.loadAd(adRequest);
        } catch (Exception e) {
            Log.e("FIREBASE_ADS", "❌ [COLLAPSIBLE_BANNER] Error: " + e.getMessage());
        }

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.e("FIREBASE_ADS", "❌ [COLLAPSIBLE_BANNER] Failed to load: " + loadAdError.getMessage());
                if (relativeLayout != null) relativeLayout.setVisibility(View.GONE);
                mainLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("FIREBASE_ADS", "🎉 [COLLAPSIBLE_BANNER] Collapsible Banner Loaded Successfully!");
                if (shimmer_view_container != null) {
                    shimmer_view_container.stopShimmer();
                    shimmer_view_container.setVisibility(View.GONE);
                    shimmer_view_container.hideShimmer();
                }
            }
        });

        mainLayout.addView(adView);
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

