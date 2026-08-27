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
        mainLayout.removeAllViews();
        RelativeLayout.LayoutParams bannerParameters =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        bannerParameters.addRule(RelativeLayout.CENTER_IN_PARENT);
        mainLayout.addView(getBannerView(activity));

        String bannerAdunitID = new Task_PreferenceClass(activity).getAdsId("GoogleBannerAd");
        Log.e("TAG%%Banner", "GoogleBannerAd: " + bannerAdunitID);

        if (bannerAdunitID != null) {
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
                    loadADXBannerAd(activity, mainLayout);
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();

                    mainLayout.removeAllViews();
                    mainLayout.addView(adView, bannerParameters);
                }
            });

        }
    }
    private static void loadADXBannerAd(Activity activity, RelativeLayout mainLayout) {
        mainLayout.removeAllViews();
        String AdxBannerAdunitID = new Task_PreferenceClass(activity).getAdsId("AdxBannerAdunitID");
        Log.e("TAG%%Banner", "adXInterstitialAdId: " + AdxBannerAdunitID);

        if (AdxBannerAdunitID != null) {
            AdView adView = new AdView(activity);
            AdSize adSize = getAdSize(activity);
            adView.setAdSize(adSize);
            adView.setAdUnitId(AdxBannerAdunitID);

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    loadFBBannerAd(activity, mainLayout);
                }
            });

            RelativeLayout.LayoutParams bannerParameters =
                    new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
            bannerParameters.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mainLayout.addView(adView, bannerParameters);
        }
    }
    private static void loadFBBannerAd(Activity activity, RelativeLayout mainLayout) {
        String fbBannerAdunitID = new Task_PreferenceClass(activity).getAdsId("FbBannerAd");
        Log.e("TAG%%Banner", "FbBannerAd: " + fbBannerAdunitID);
        com.facebook.ads.AdView fbBannerView = new com.facebook.ads.AdView(activity, fbBannerAdunitID, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
        mainLayout.setGravity(Gravity.BOTTOM);

        com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
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


    public static void loadCollapsibleBanner(Activity activity, String str,FrameLayout mainLayout, RelativeLayout relativeLayout,ShimmerFrameLayout shimmer_view_container) {

        String CollapsiblebannerID = new Task_PreferenceClass(activity).getAdsId("CollapsibleBannerID");

        shimmerFrameLayout = shimmer_view_container;
        mainLayout.removeAllViews();
        String bannerAdunitID = CollapsiblebannerID ;
        if (bannerAdunitID != null) {
            AdView adView = new AdView(activity);
            AdSize adSize = getAdSize(activity,mainLayout);
            adView.setAdSize(adSize);
            adView.setAdUnitId(bannerAdunitID);
            Bundle extras = new Bundle();
            extras.putString("collapsible", str);
            extras.putString("collapsible_request_id", UUID.randomUUID().toString());
            AdRequest adRequest = new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build();
            mainLayout.setVisibility(View.VISIBLE);
            try {
                adView.loadAd(adRequest);
            } catch (Exception e) {
                Log.e("TAG", "loadCollapsibleBanner: Catch"+ e.getMessage() );
            }


            adView.setAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    mainLayout.setVisibility(View.GONE);
//                    loadADXBannerAd(activity, relativeLayout);
                    Log.e("TAG", "onAdFailedToLoad: Collapse Fail="+ loadAdError.getMessage() );
                }
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (shimmerFrameLayout!= null) {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        shimmerFrameLayout.hideShimmer();
                    }
                }

            });

            mainLayout.addView(adView);
        }

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
