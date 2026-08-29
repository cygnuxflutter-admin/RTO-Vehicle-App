package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.view.Window;

import com.facebook.ads.Ad;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vehicle.information.trending.rtoexam.rto.BuildConfig;
import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_AppOpenManager;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_MaterialDialogUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Looper;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import java.util.Objects;


public class Task_SplashScreenActivity extends AppCompatActivity {
    private Task_PreferenceClass taskPreferenceClass;
    public FirebaseDatabase database;
    private DatabaseReference project_data2;
    private InterstitialAd interstitial = null;
    public com.facebook.ads.InterstitialAd interstitialFB;


    private long startTime = 0;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startTime = System.currentTimeMillis();
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.task_activity_splash_screen);

        ImageView ivSplashLogo = findViewById(R.id.iv_splash_logo);
        if (ivSplashLogo != null) {
            ObjectAnimator floatAnim = ObjectAnimator.ofFloat(ivSplashLogo, "translationY", 0f, -10f, 0f);
            floatAnim.setDuration(2200);
            floatAnim.setRepeatCount(ValueAnimator.INFINITE);
            floatAnim.setRepeatMode(ValueAnimator.RESTART);
            floatAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            floatAnim.start();
        }

        TextView tvStatus = findViewById(R.id.tv_splash_status);
        String[] statusMessages = new String[]{"Loading RTO exam questions...", "Preparing traffic signs guide...", "Checking vehicle utilities...", "Welcome!"};
        Handler statusHandler = new Handler(Looper.getMainLooper());
        for (int i = 0; i < statusMessages.length; i++) {
            final String msg = statusMessages[i];
            statusHandler.postDelayed(() -> {
                if (tvStatus != null && !isFinishing()) {
                    tvStatus.setText(msg);
                }
            }, (long) i * 700);
        }

                taskPreferenceClass = new Task_PreferenceClass(this);
        if (BuildConfig.DEBUG) {
            taskPreferenceClass.setDataType("GoogleNativeAd", "ca-app-pub-3940256099942544/2247696110");
            taskPreferenceClass.setDataType("GoogleAppopenAd", "ca-app-pub-3940256099942544/9257395921");
            taskPreferenceClass.setDataType("GoogleBannerAd", "ca-app-pub-3940256099942544/6300978111");
            taskPreferenceClass.setDataType("GoogleInterstitialAd", "ca-app-pub-3940256099942544/1033173712");
            taskPreferenceClass.setDataType("GoogleRewardedAd", "ca-app-pub-3940256099942544/5224354917");
            taskPreferenceClass.setDataType("GoogleInterstialRewardAd", "ca-app-pub-3940256099942544/5354046379");
            taskPreferenceClass.setDataType("CollapsibleBannerID", "ca-app-pub-3940256099942544/9214589741");
            taskPreferenceClass.setInt("NativeAdShow", 1);
            taskPreferenceClass.setInt("BannerAdShow", 1);
            taskPreferenceClass.setInt("MainScreenAd", 2);
            taskPreferenceClass.setInt("StartScreen_AD", 2);
            taskPreferenceClass.setInt("splashscreen", 1);
            Task_AppOpenManager.AppOpenAdShow = 1;
            taskPreferenceClass.setInt("InerstialClickCount", 1);
        }
        MyApplication.isAdsSplash = true;
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            getData();
        } else {
            next();
        }
    }

    private boolean hasStarted = false;

    public synchronized void startToMainActivity() {
        if (hasStarted) return;
        hasStarted = true;
        startIntent();
    }

    public void next() {
        long elapsed = System.currentTimeMillis() - startTime;
        long remaining = Math.max(600, 2800 - elapsed);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startToMainActivity();
            }
        }, remaining);
    }


    private void getData() {
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            database = FirebaseDatabase.getInstance();
            project_data2 = database.getReference("Ads_data");
            project_data2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.e("snapshot", "" + snapshot);
                    try {
                        taskPreferenceClass.setInt("splashscreen", Integer.parseInt(snapshot.child("SplashScreenAdsManage").getValue().toString()));
                        taskPreferenceClass.setInt("UpdateAvailable", Integer.parseInt(snapshot.child("UpdateAvailable").getValue().toString()));
                        taskPreferenceClass.setDataType("UpdateVersionName", Objects.requireNonNull(snapshot.child("UpdateVersionName").getValue()).toString());

                        if (BuildConfig.DEBUG) {
                            // ----------------------------------------------- Test ADS (Debug Mode) -----------------------------------------------
                            taskPreferenceClass.setDataType("GoogleNativeAd", "ca-app-pub-3940256099942544/2247696110");
                            taskPreferenceClass.setDataType("GoogleAppopenAd", "ca-app-pub-3940256099942544/9257395921");
                            taskPreferenceClass.setDataType("GoogleBannerAd", "ca-app-pub-3940256099942544/6300978111");
                            taskPreferenceClass.setDataType("GoogleInterstitialAd", "ca-app-pub-3940256099942544/1033173712");
                            taskPreferenceClass.setDataType("GoogleRewardedAd", "ca-app-pub-3940256099942544/5224354917");
                            taskPreferenceClass.setDataType("GoogleInterstialRewardAd", "ca-app-pub-3940256099942544/5354046379");
                            taskPreferenceClass.setDataType("CollapsibleBannerID", "ca-app-pub-3940256099942544/9214589741");

                            taskPreferenceClass.setDataType("FbNativeAd", "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                            taskPreferenceClass.setDataType("FbInterstitialAd", "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
                            taskPreferenceClass.setDataType("FbBannerAd", "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");

                            taskPreferenceClass.setDataType("AdxBannerAdunitID", "ca-app-pub-3940256099942544/6300978111");
                            taskPreferenceClass.setDataType("AdxInterstitalAdunitID", "ca-app-pub-3940256099942544/1033173712");
                            taskPreferenceClass.setDataType("AdxRewardVideoUnitID", "ca-app-pub-3940256099942544/5224354917");
                            taskPreferenceClass.setDataType("AdxNativeUnitID", "ca-app-pub-3940256099942544/2247696110");
                            taskPreferenceClass.setDataType("AdxAppOpenID", "ca-app-pub-3940256099942544/9257395921");

                            taskPreferenceClass.setInt("NativeAdShow", 1);
                            taskPreferenceClass.setInt("BannerAdShow", 1);
                            taskPreferenceClass.setInt("MainScreenAd", 2);
                            taskPreferenceClass.setInt("StartScreen_AD", 2);
                            taskPreferenceClass.setInt("splashscreen", 1);
                            Task_AppOpenManager.AppOpenAdShow = 1;
                            taskPreferenceClass.setInt("InerstialClickCount", 1);
                        } else {
                            // ----------------------------------------------- Live ADS (Release Mode) -----------------------------------------------
                            taskPreferenceClass.setDataType("GoogleBannerAd", Objects.requireNonNull(snapshot.child("GoogleBannerAd").getValue()).toString());
                            taskPreferenceClass.setDataType("GoogleAppopenAd", Objects.requireNonNull(snapshot.child("GoogleAppopenAd").getValue()).toString());
                            taskPreferenceClass.setDataType("GoogleInterstitialAd", Objects.requireNonNull(snapshot.child("GoogleInterstitialAd").getValue()).toString());
                            taskPreferenceClass.setDataType("GoogleInterstialRewardAd", Objects.requireNonNull(snapshot.child("GoogleInterstialRewardAd").getValue()).toString());
                            taskPreferenceClass.setDataType("GoogleRewardedAd", Objects.requireNonNull(snapshot.child("GoogleRewardedAd").getValue()).toString());
                            taskPreferenceClass.setDataType("GoogleNativeAd", Objects.requireNonNull(snapshot.child("GoogleNativeAd").getValue()).toString());

                            taskPreferenceClass.setDataType("FbNativeAd", Objects.requireNonNull(snapshot.child("FbNativeAd").getValue()).toString());
                            taskPreferenceClass.setDataType("FbInterstitialAd", Objects.requireNonNull(snapshot.child("FbInterstitialAd").getValue()).toString());
                            taskPreferenceClass.setDataType("FbBannerAd", Objects.requireNonNull(snapshot.child("FbBannerAd").getValue()).toString());

                            taskPreferenceClass.setDataType("AdxBannerAdunitID", Objects.requireNonNull(snapshot.child("AdxBannerAdunitID").getValue()).toString());
                            taskPreferenceClass.setDataType("AdxInterstitalAdunitID", Objects.requireNonNull(snapshot.child("AdxInterstitalAdunitID").getValue()).toString());
                            taskPreferenceClass.setDataType("AdxRewardVideoUnitID", Objects.requireNonNull(snapshot.child("AdxRewardVideoUnitID").getValue()).toString());
                            taskPreferenceClass.setDataType("AdxNativeUnitID", Objects.requireNonNull(snapshot.child("AdxNativeUnitID").getValue()).toString());
                            taskPreferenceClass.setDataType("AdxAppOpenID", Objects.requireNonNull(snapshot.child("AdxAppOpenID").getValue()).toString());
                            taskPreferenceClass.setDataType("CollapsibleBannerID", Objects.requireNonNull(snapshot.child("CollapsibleBannerID").getValue()).toString());

                            taskPreferenceClass.setInt("NativeAdShow", Integer.parseInt(snapshot.child("NativeAdShow").getValue().toString()));
                            taskPreferenceClass.setInt("BannerAdShow", Integer.parseInt(snapshot.child("BannerAdShow").getValue().toString()));
                            taskPreferenceClass.setInt("MainScreenAd", Integer.parseInt(snapshot.child("MainScreenAd").getValue().toString()));
                            taskPreferenceClass.setInt("StartScreen_AD", Integer.parseInt(snapshot.child("StartScreen_AD").getValue().toString()));
                            Task_AppOpenManager.AppOpenAdShow = Integer.parseInt(snapshot.child("AppOpenAdShow").getValue().toString());
                            taskPreferenceClass.setInt("InerstialClickCount", Integer.parseInt(Objects.requireNonNull(snapshot.child("InerstialClickCount").getValue().toString())));
                        }
                    } catch (Exception e) {
                        Log.e("SPLASH_AD", "Error reading Firebase ads data: " + e.getMessage());
                    }

                    try {
                        if (!BuildConfig.DEBUG && taskPreferenceClass.getInt("UpdateAvailable") == 1 && !taskPreferenceClass.getAdsId("UpdateVersionName").equals(BuildConfig.VERSION_NAME)) {
                            showUpdateDialog(taskPreferenceClass.getAdsId("UpdateVersionName"));
                        } else {
                            next();
                        }
                    } catch (Exception e) {
                        next();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Task_MaterialDialogUtils.getInstance().errorDialog(Task_SplashScreenActivity.this, getResources().getString(R.string.something_went_wrong));
                }
            });
        } else {
            Task_MaterialDialogUtils.getInstance().errorDialog(this, getResources().getString(R.string.internet_error));
        }
    }
    private Dialog materialDialog = null;

    private void showUpdateDialog(String versionName) {
        runOnUiThread(() -> {
            if (isFinishing() || isDestroyed()) return;
            if (materialDialog != null && materialDialog.isShowing()) return;

            materialDialog = new Dialog(Task_SplashScreenActivity.this);
            materialDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            materialDialog.setContentView(R.layout.task_reward_dialog);
            materialDialog.setCancelable(false);
            if (materialDialog.getWindow() != null) {
                materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                materialDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            TextView tv_title = materialDialog.findViewById(R.id.title);
            TextView tv_description = materialDialog.findViewById(R.id.description);
            TextView button1 = materialDialog.findViewById(R.id.button1);
            TextView button2 = materialDialog.findViewById(R.id.button2);

            tv_title.setText("New Update Available");
            if (versionName != null && !versionName.isEmpty()) {
                tv_description.setText("A newer version (v" + versionName + ") of RTO Vehicle Guide is ready with updated 2026 questions, live fuel rates, and improvements.");
            } else {
                tv_description.setText("A newer version of RTO Vehicle Guide is ready with updated 2026 questions, live fuel rates, and improvements.");
            }

            button1.setText("Later");
            button2.setText("Update Now");
            button2.setOnClickListener(v -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    Toast.makeText(Task_SplashScreenActivity.this, "Unable to find Google Play Store", Toast.LENGTH_LONG).show();
                }
                if (materialDialog != null && materialDialog.isShowing()) {
                    materialDialog.dismiss();
                    next();
                }
            });

            button1.setOnClickListener(v -> {
                if (materialDialog != null && materialDialog.isShowing()) {
                    materialDialog.dismiss();
                    next();
                }
            });

            materialDialog.show();
        });
    }

    private void startIntent() {
        if (BuildConfig.DEBUG) {
            callMainActivity();
            return;
        }
        if (taskPreferenceClass.getInt("splashscreen") == 1) {
            callStartActivity();
        } else if (taskPreferenceClass.getInt("splashscreen") == 0) {
            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(Task_SplashScreenActivity.this, taskPreferenceClass.getAdsId("GoogleInterstitialAd"), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    interstitial = interstitialAd;

                    interstitial.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            interstitial = null;
                            callMainActivity();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                            interstitial = null;
                            callMainActivity();
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            interstitial = null;

                        }
                    });

                    if (interstitial != null) {
                        interstitial.show(Task_SplashScreenActivity.this);
                    }


                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    interstitial = null;
                    callMainActivity();
                }
            });
        } else if (taskPreferenceClass.getInt("splashscreen") == 2) {

            this.interstitialFB = new com.facebook.ads.InterstitialAd(Task_SplashScreenActivity.this, taskPreferenceClass.getAdsId("FbInterstitialAd"));
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    Log.e("TAG", "Interstitial ad displayed.");
//                    Log.e("#1", "" + ad.toString());
                    // Interstitial ad displayed callback
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Log.e("TAG", "Interstitial ad dismissed.");
//                    Log.e("#2", "" + ad.toString());
                    // Interstitial dismissed callback
                    callMainActivity();
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    Log.e("#3", "" + adError.getErrorMessage());
                    Log.e("#3_1", "" + adError.getErrorCode());
                    // Ad error callback
                    Log.e("TAG", "Interstitial ad failed to load: " + adError.getErrorMessage());
                    callMainActivity();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    // Interstitial ad is loaded and ready to be displayed
                    // Show the ad
//                    Log.e("#2", "" + ad.toString());
                    interstitialFB.show();
                }

                @Override
                public void onAdClicked(Ad ad) {
                    // Ad clicked callback
                    Log.d("TAG", "Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    // Ad impression logged callback
                    Log.d("TAG", "Interstitial ad impression logged!");
                }
            };
            com.facebook.ads.InterstitialAd interstitialAd = this.interstitialFB;
            interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
        } else {
            callMainActivity();
        }
    }

    public void callStartActivity() {
        Application application = getApplication();
        ((MyApplication) application).showAdIfAvailable(Task_SplashScreenActivity.this, () -> {
            MyApplication.isAdsSplash = false;
            ((MyApplication) getApplicationContext()).loadInterstitialAd();
            Intent intent = new Intent(getApplicationContext(), Task_StartActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void callMainActivity() {
        MyApplication.isAdsSplash = false;
        ((MyApplication) getApplicationContext()).sendRequest();
        ((MyApplication) getApplicationContext()).loadInterstitialAd();

        Intent intent = new Intent(getApplicationContext(), Task_StartActivity.class);
        startActivity(intent);
        finish();
    }

}
