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
import com.google.android.gms.ads.MediaContent;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.nativead.MediaView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.LinkedList;
import java.util.Queue;

public class Task_NativeAdUtil {

    private static final String TAG = "FIREBASE_ADS";
    private static final Queue<NativeAd> nativeAdCache = new LinkedList<>();
    private static boolean isPreloading = false;

    private final Context context;
    private final Task_PreferenceClass taskPreferenceClass;
    private final int width;
    private final int height;
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

    /**
     * Entry point to load or display a Native Ad in a container.
     */
    public static void loadNativeAd(final RelativeLayout nativeAdContainer, final Activity context) {
        if (nativeAdContainer == null || context == null || context.isFinishing() || context.isDestroyed()) {
            return;
        }

        // Check if container is already loaded to prevent redundant reload & view flashing on list scrolls
        if ("LOADED".equals(nativeAdContainer.getTag())) {
            return;
        }

        Task_PreferenceClass taskPref = new Task_PreferenceClass(context);
        if (taskPref.getInt("NativeAdShow", 0) == 0 || !Task_NetworkUtils.isNetworkAvailable(context)) {
            Log.d(TAG, "🔴 [NATIVE_AD] Native ads disabled or offline. Hiding container.");
            hideContainer(nativeAdContainer);
            return;
        }

        // Check cache first for instant ad presentation
        NativeAd cachedAd = getCachedNativeAd();
        if (cachedAd != null) {
            Log.d(TAG, "⚡ [NATIVE_AD] Serving cached Native Ad instantly!");
            showNativeAdInContainer(cachedAd, nativeAdContainer, context);
            preloadNativeAd(context.getApplicationContext());
            return;
        }

        // Show smooth skeleton shimmer while loading
        showLoadingShimmer(nativeAdContainer, context);

        Task_NativeAdUtil taskNativeAdUtil = new Task_NativeAdUtil(context);
        taskNativeAdUtil.fillAdmobNativeAd(nativeAdContainer);
    }

    private static synchronized NativeAd getCachedNativeAd() {
        while (!nativeAdCache.isEmpty()) {
            NativeAd ad = nativeAdCache.poll();
            if (ad != null) {
                return ad;
            }
        }
        return null;
    }

    public static synchronized void preloadNativeAd(final Context appContext) {
        if (appContext == null || isPreloading || nativeAdCache.size() >= 2) {
            return;
        }

        Task_PreferenceClass pref = new Task_PreferenceClass(appContext);
        if (pref.getInt("NativeAdShow", 0) == 0 || !Task_NetworkUtils.isNetworkAvailable(appContext)) {
            return;
        }

        String nativeId = pref.getAdsId("GoogleNativeAd");
        if (nativeId == null || nativeId.trim().isEmpty()) {
            nativeId = pref.getAdsId("AdxNativeUnitID");
        }
        if (nativeId == null || nativeId.trim().isEmpty()) {
            nativeId = pref.getAdsId("AdxNativeAdunitID");
        }
        if (nativeId == null || nativeId.trim().isEmpty()) {
            return;
        }

        isPreloading = true;
        AdLoader.Builder builder = new AdLoader.Builder(appContext, nativeId);
        builder.forNativeAd(ad -> {
            synchronized (Task_NativeAdUtil.class) {
                isPreloading = false;
                if (nativeAdCache.size() < 2) {
                    nativeAdCache.offer(ad);
                } else {
                    ad.destroy();
                }
            }
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                synchronized (Task_NativeAdUtil.class) {
                    isPreloading = false;
                }
            }
        });

        builder.build().loadAd(new AdRequest.Builder().build());
    }

    private static void showLoadingShimmer(RelativeLayout container, Context context) {
        container.removeAllViews();
        View shimmerView = LayoutInflater.from(context).inflate(R.layout.task_native_ad_layout_loading, container, false);
        ShimmerFrameLayout shimmerLayout = shimmerView.findViewById(R.id.shimmerLayout);
        if (shimmerLayout != null) {
            shimmerLayout.startShimmer();
            shimmerLayout.setVisibility(View.VISIBLE);
        }
        container.addView(shimmerView);
        container.setVisibility(View.VISIBLE);
    }

    private static void hideContainer(RelativeLayout container) {
        if (container != null) {
            container.removeAllViews();
            container.setVisibility(View.GONE);
            container.setTag(null);
        }
    }

    private static void showNativeAdInContainer(NativeAd nativeAd, RelativeLayout container, Context context) {
        if (container == null || context == null || nativeAd == null) return;

        NativeAdView adView = (NativeAdView) LayoutInflater.from(context).inflate(R.layout.task_native_ad_layout, null);
        populateUnifiedNativeAdView(nativeAd, adView);

        container.removeAllViews();
        container.addView(adView);
        container.setVisibility(View.VISIBLE);
        container.setBackgroundColor(Color.TRANSPARENT);
        container.setTag("LOADED");
    }

    public void fillAdmobNativeAd(final RelativeLayout nativeAdContainer) {
        String nativeId = taskPreferenceClass.getAdsId("GoogleNativeAd");
        if (nativeId == null || nativeId.trim().isEmpty()) {
            fillAdXNativeAd(nativeAdContainer);
            return;
        }

        Log.d(TAG, "🟢 [NATIVE_AD] Loading AdMob Native Ad with ID: " + nativeId);
        AdLoader.Builder builder = new AdLoader.Builder(context, nativeId);

        builder.forNativeAd(ad -> {
            if (this.nativeAd != null) {
                this.nativeAd.destroy();
            }
            this.nativeAd = ad;
            showNativeAdInContainer(ad, nativeAdContainer, context);
            Log.d(TAG, "🎉 [NATIVE_AD] AdMob Native Ad Loaded and Displayed!");
            preloadNativeAd(context.getApplicationContext());
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e(TAG, "❌ [NATIVE_AD] AdMob Failed (" + loadAdError.getCode() + "): " + loadAdError.getMessage() + " -> Fallback to AdX");
                fillAdXNativeAd(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    public void fillAdXNativeAd(final RelativeLayout nativeAdContainer) {
        String adxId = taskPreferenceClass.getAdsId("AdxNativeUnitID");
        if (adxId == null || adxId.trim().isEmpty()) {
            adxId = taskPreferenceClass.getAdsId("AdxNativeAdunitID");
        }
        if (adxId == null || adxId.trim().isEmpty()) {
            fbNativeAd(nativeAdContainer);
            return;
        }

        Log.d(TAG, "🟢 [NATIVE_AD] Loading AdX Native Ad with ID: " + adxId);
        AdLoader.Builder builder = new AdLoader.Builder(context, adxId);

        builder.forNativeAd(ad -> {
            if (this.nativeAd != null) {
                this.nativeAd.destroy();
            }
            this.nativeAd = ad;
            showNativeAdInContainer(ad, nativeAdContainer, context);
            Log.d(TAG, "🎉 [NATIVE_AD] AdX Native Ad Loaded and Displayed!");
            preloadNativeAd(context.getApplicationContext());
        });

        VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(true).build();
        NativeAdOptions adOptions = new NativeAdOptions.Builder().setVideoOptions(videoOptions).build();
        builder.withNativeAdOptions(adOptions);

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                Log.e(TAG, "❌ [NATIVE_AD] AdX Failed (" + loadAdError.getCode() + ") -> Fallback to Facebook");
                fbNativeAd(nativeAdContainer);
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void fbNativeAd(final RelativeLayout nativeAdContainer) {
        String fbId = taskPreferenceClass.getAdsId("FbNativeAd");
        if (fbId == null || fbId.trim().isEmpty()) {
            Log.w(TAG, "🔴 [NATIVE_AD] All Native sources exhausted -> Hiding container");
            hideContainer(nativeAdContainer);
            return;
        }

        com.facebook.ads.NativeAd fbNative = new com.facebook.ads.NativeAd(context, fbId);
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {}

            @Override
            public void onError(Ad ad, AdError adError) {
                Log.w(TAG, "🔴 [NATIVE_AD] Facebook Native Ad failed (" + adError.getErrorMessage() + ") -> Hiding container");
                hideContainer(nativeAdContainer);
            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (fbNative != ad) return;
                nativeAdContainer.removeAllViews();
                View adView = com.facebook.ads.NativeAdView.render(context, fbNative);
                nativeAdContainer.addView(adView);
                nativeAdContainer.setVisibility(View.VISIBLE);
                nativeAdContainer.setBackgroundColor(Color.TRANSPARENT);
                nativeAdContainer.setTag("LOADED");
            }

            @Override
            public void onAdClicked(Ad ad) {}

            @Override
            public void onLoggingImpression(Ad ad) {}
        };

        fbNative.loadAd(fbNative.buildLoadAdConfig().withAdListener(nativeAdListener).build());
    }

    public static void populateUnifiedNativeAdView(NativeAd unifiedNativeAd, NativeAdView unifiedNativeAdView) {
        if (unifiedNativeAd == null || unifiedNativeAdView == null) return;

        // Headline
        TextView headlineView = unifiedNativeAdView.findViewById(R.id.ad_headline);
        if (headlineView != null) {
            unifiedNativeAdView.setHeadlineView(headlineView);
            headlineView.setText(unifiedNativeAd.getHeadline());
        }

        // Body text
        TextView bodyView = unifiedNativeAdView.findViewById(R.id.ad_body);
        if (bodyView != null) {
            unifiedNativeAdView.setBodyView(bodyView);
            if (unifiedNativeAd.getBody() == null || unifiedNativeAd.getBody().trim().isEmpty()) {
                bodyView.setVisibility(View.GONE);
            } else {
                bodyView.setVisibility(View.VISIBLE);
                bodyView.setText(unifiedNativeAd.getBody());
            }
        }

        // Call To Action button
        Button callToActionView = unifiedNativeAdView.findViewById(R.id.ad_call_to_action);
        if (callToActionView != null) {
            unifiedNativeAdView.setCallToActionView(callToActionView);
            if (unifiedNativeAd.getCallToAction() == null || unifiedNativeAd.getCallToAction().trim().isEmpty()) {
                callToActionView.setVisibility(View.GONE);
            } else {
                callToActionView.setVisibility(View.VISIBLE);
                callToActionView.setText(unifiedNativeAd.getCallToAction());
            }
        }

        // App Icon
        ImageView iconView = unifiedNativeAdView.findViewById(R.id.ad_app_icon);
        if (iconView != null) {
            unifiedNativeAdView.setIconView(iconView);
            if (unifiedNativeAd.getIcon() == null || unifiedNativeAd.getIcon().getDrawable() == null) {
                iconView.setVisibility(View.GONE);
            } else {
                iconView.setVisibility(View.VISIBLE);
                iconView.setImageDrawable(unifiedNativeAd.getIcon().getDrawable());
            }
        }

        // MediaView for high-eCPM rich image or video ads
        MediaView mediaView = unifiedNativeAdView.findViewById(R.id.ad_media);
        if (mediaView != null) {
            unifiedNativeAdView.setMediaView(mediaView);
            MediaContent mediaContent = unifiedNativeAd.getMediaContent();
            if (mediaContent != null && (mediaContent.hasVideoContent() || mediaContent.getAspectRatio() > 0)) {
                mediaView.setMediaContent(mediaContent);
                mediaView.setVisibility(View.VISIBLE);
            } else {
                mediaView.setVisibility(View.GONE);
            }
        }

        unifiedNativeAdView.setNativeAd(unifiedNativeAd);
    }
}
