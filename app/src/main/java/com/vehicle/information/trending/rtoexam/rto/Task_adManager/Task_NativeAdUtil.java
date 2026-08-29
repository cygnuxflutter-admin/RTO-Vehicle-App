package com.vehicle.information.trending.rtoexam.rto.Task_adManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.NativeAdListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_NativeAdUtil {

    private final Context context;
    private final Task_PreferenceClass taskPreferenceClass;
    private final int width;
    private final int height;
    private NativeAdView adView;
    private NativeAd nativeAd;

    public Task_NativeAdUtil(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
        this.taskPreferenceClass = new Task_PreferenceClass(context);
    }

    public Task_NativeAdUtil(Context context) {
        this.context = context;
        this.width = -1;
        this.height = -1;
        this.taskPreferenceClass = new Task_PreferenceClass(context);
    }

    public static void loadNativeAd(RelativeLayout nativeAdContainer, Activity context) {
        if (nativeAdContainer == null || context == null || context.isFinishing()) return;

        nativeAdContainer.removeAllViews();
        nativeAdContainer.addView(getLoadingView(context));
        nativeAdContainer.setVisibility(View.VISIBLE);

        Task_NativeAdUtil taskNativeAdUtil = new Task_NativeAdUtil(context);
        taskNativeAdUtil.fillAdmobNativeAd(nativeAdContainer);
    }

    private static View getLoadingView(Activity context) {
        View adView = LayoutInflater.from(context).inflate(R.layout.task_native_ad_layout_loading, null);
        ShimmerFrameLayout shimmerLayout = adView.findViewById(R.id.shimmerLayout);
        if (shimmerLayout != null) {
            shimmerLayout.startShimmer();
            shimmerLayout.setVisibility(View.VISIBLE);
        }
        return adView;
    }

    public void fillAdmobNativeAd(final RelativeLayout nativeAdContainer) {
        String nativeId = taskPreferenceClass.getAdsId("GoogleNativeAd");
        if (nativeId == null || nativeId.isEmpty()) {
            nativeId = "ca-app-pub-3940256099942544/2247696110";
        }
        AdLoader.Builder builder = new AdLoader.Builder(context, nativeId);

        builder.forNativeAd(nativeAd -> {
            if (this.nativeAd != null) {
                this.nativeAd.destroy();
            }
            this.nativeAd = nativeAd;
            adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.task_native_ad_layout, null);
            populateUnifiedNativeAdView(nativeAd, adView);
            nativeAdContainer.removeAllViews();
            nativeAdContainer.addView(adView);
            nativeAdContainer.setBackgroundColor(Color.TRANSPARENT);
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                fillAdXNativeAd(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void fillAdXNativeAd(final RelativeLayout nativeAdContainer) {
        String adxId = taskPreferenceClass.getAdsId("AdxNativeUnitID");
        if (adxId == null || adxId.isEmpty()) {
            adxId = "ca-app-pub-3940256099942544/2247696110";
        }
        AdLoader.Builder builder = new AdLoader.Builder(context, adxId);

        builder.forNativeAd(nativeAd -> {
            if (this.nativeAd != null) {
                this.nativeAd.destroy();
            }
            this.nativeAd = nativeAd;
            adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.task_native_ad_layout, null);
            populateUnifiedNativeAdView(nativeAd, adView);
            nativeAdContainer.removeAllViews();
            nativeAdContainer.addView(adView);
            nativeAdContainer.setBackgroundColor(Color.TRANSPARENT);
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                fbNativeAd(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void fbNativeAd(final RelativeLayout nativeAdContainer) {
        com.facebook.ads.NativeAd fbNative = new com.facebook.ads.NativeAd(context, taskPreferenceClass.getAdsId("FbNativeAd"));
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {}

            @Override
            public void onError(Ad ad, AdError adError) {}

            @Override
            public void onAdLoaded(Ad ad) {
                if (fbNative != ad) return;
                nativeAdContainer.removeAllViews();
                View adView = com.facebook.ads.NativeAdView.render(context, fbNative);
                nativeAdContainer.addView(adView);
                nativeAdContainer.setBackgroundColor(Color.TRANSPARENT);
            }

            @Override
            public void onAdClicked(Ad ad) {}

            @Override
            public void onLoggingImpression(Ad ad) {}
        };

        fbNative.loadAd(fbNative.buildLoadAdConfig().withAdListener(nativeAdListener).build());
    }

    public void populateUnifiedNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        TextView headlineView = unifiedNativeAdView.findViewById(R.id.ad_headline);
        if (headlineView != null) {
            unifiedNativeAdView.setHeadlineView(headlineView);
            headlineView.setText(unifiedNativeAd.getHeadline());
        }

        TextView bodyView = unifiedNativeAdView.findViewById(R.id.ad_body);
        if (bodyView != null) {
            unifiedNativeAdView.setBodyView(bodyView);
            if (unifiedNativeAd.getBody() == null) {
                bodyView.setVisibility(View.GONE);
            } else {
                bodyView.setVisibility(View.VISIBLE);
                bodyView.setText(unifiedNativeAd.getBody());
            }
        }

        Button callToActionView = unifiedNativeAdView.findViewById(R.id.ad_call_to_action);
        if (callToActionView != null) {
            unifiedNativeAdView.setCallToActionView(callToActionView);
            if (unifiedNativeAd.getCallToAction() == null) {
                callToActionView.setVisibility(View.GONE);
            } else {
                callToActionView.setVisibility(View.VISIBLE);
                callToActionView.setText(unifiedNativeAd.getCallToAction());
            }
        }

        ImageView iconView = unifiedNativeAdView.findViewById(R.id.ad_app_icon);
        if (iconView != null) {
            unifiedNativeAdView.setIconView(iconView);
            if (unifiedNativeAd.getIcon() == null) {
                iconView.setVisibility(View.GONE);
            } else {
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            }
        }

        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }
}