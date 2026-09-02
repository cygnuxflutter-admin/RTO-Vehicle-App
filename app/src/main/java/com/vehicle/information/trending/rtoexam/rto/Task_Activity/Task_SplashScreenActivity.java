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

        View ivSplashLogo = findViewById(R.id.iv_splash_logo);
        if (ivSplashLogo != null) {
            ivSplashLogo.setScaleX(0.75f);
            ivSplashLogo.setScaleY(0.75f);
            ivSplashLogo.setAlpha(0f);
            ivSplashLogo.animate()
                    .scaleX(1.0f)
                    .scaleY(1.0f)
                    .alpha(1.0f)
                    .setDuration(750)
                    .setInterpolator(new android.view.animation.OvershootInterpolator(1.2f))
                    .withEndAction(() -> {
                        ObjectAnimator floatAnim = ObjectAnimator.ofFloat(ivSplashLogo, "translationY", 0f, -8f, 0f);
                        floatAnim.setDuration(2200);
                        floatAnim.setRepeatCount(ValueAnimator.INFINITE);
                        floatAnim.setRepeatMode(ValueAnimator.RESTART);
                        floatAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                        floatAnim.start();
                    })
                    .start();
        }

        View brandContainer = findViewById(R.id.brand_container);
        if (brandContainer != null) {
            brandContainer.setAlpha(0f);
            brandContainer.animate().alpha(1.0f).setDuration(500).start();
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
            }, (long) i * 750);
        }

        taskPreferenceClass = new Task_PreferenceClass(this);
        MyApplication.isAdsSplash = true;
        
        // Safety Watchdog: Ensure Splash screen NEVER gets stuck if Firebase is slow/disconnected
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !hasStarted) {
                    Log.w("FIREBASE_FETCH", "Firebase fetch timed out or taking too long -> Proceeding safely to Next Screen");
                    startToMainActivity();
                }
            }
        }, 4000);

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
        long remaining = Math.max(500, 3000 - elapsed);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startToMainActivity();
            }
        }, remaining);
    }


    private void getData() {
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            try {
                database = FirebaseDatabase.getInstance();
                project_data2 = database.getReference("Ads_data");
                project_data2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.e("Firebase_Ads_data", "Snapshot received: " + snapshot);
                    try {
                        taskPreferenceClass.setInt("splashscreen", getFirebaseInt(snapshot, "SplashScreenAdsManage", 0));
                        taskPreferenceClass.setInt("UpdateAvailable", getFirebaseInt(snapshot, "UpdateAvailable", 0));
                        taskPreferenceClass.setInt("UpdateVersionCode", getFirebaseInt(snapshot, "UpdateVersionCode", BuildConfig.VERSION_CODE));
                        taskPreferenceClass.setDataType("UpdateVersionName", getFirebaseString(snapshot, "UpdateVersionName", BuildConfig.VERSION_NAME));
                        taskPreferenceClass.setInt("ForceUpdate", getFirebaseInt(snapshot, "ForceUpdate", 0));
                        taskPreferenceClass.setDataType("UpdateMessage", getFirebaseString(snapshot, "UpdateMessage", ""));

                        // ----------------- Google AdMob IDs (Strictly from Firebase ONLY) -----------------
                        taskPreferenceClass.setDataType("GoogleBannerAd", getFirebaseString(snapshot, "GoogleBannerAd", ""));
                        taskPreferenceClass.setDataType("GoogleAppopenAd", getFirebaseString(snapshot, "GoogleAppopenAd", ""));
                        taskPreferenceClass.setDataType("GoogleInterstitialAd", getFirebaseString(snapshot, "GoogleInterstitialAd", ""));
                        taskPreferenceClass.setDataType("GoogleInterstialRewardAd", getFirebaseString(snapshot, "GoogleInterstialRewardAd", ""));
                        taskPreferenceClass.setDataType("GoogleRewardedAd", getFirebaseString(snapshot, "GoogleRewardedAd", ""));
                        taskPreferenceClass.setDataType("GoogleNativeAd", getFirebaseString(snapshot, "GoogleNativeAd", ""));
                        taskPreferenceClass.setDataType("CollapsibleBannerID", getFirebaseString(snapshot, "CollapsibleBannerID", ""));

                        // ----------------- Facebook Audience Network IDs (Strictly from Firebase ONLY) -----------------
                        taskPreferenceClass.setDataType("FbNativeAd", getFirebaseString(snapshot, "FbNativeAd", ""));
                        taskPreferenceClass.setDataType("FbInterstitialAd", getFirebaseString(snapshot, "FbInterstitialAd", ""));
                        taskPreferenceClass.setDataType("FbBannerAd", getFirebaseString(snapshot, "FbBannerAd", ""));

                        // ----------------- ADX Fallback IDs (Strictly from Firebase ONLY) -----------------
                        taskPreferenceClass.setDataType("AdxBannerAdunitID", getFirebaseString(snapshot, "AdxBannerAdunitID", ""));
                        taskPreferenceClass.setDataType("AdxInterstitalAdunitID", getFirebaseString(snapshot, "AdxInterstitalAdunitID", ""));
                        taskPreferenceClass.setDataType("AdxRewardVideoUnitID", getFirebaseString(snapshot, "AdxRewardVideoUnitID", ""));
                        taskPreferenceClass.setDataType("AdxNativeUnitID", getFirebaseString(snapshot, "AdxNativeUnitID", ""));
                        taskPreferenceClass.setDataType("AdxAppOpenID", getFirebaseString(snapshot, "AdxAppOpenID", ""));

                        // ----------------- Ad Visibility & Click Controls (Strictly from Firebase ONLY) -----------------
                        taskPreferenceClass.setInt("NativeAdShow", getFirebaseInt(snapshot, "NativeAdShow", 0));
                        taskPreferenceClass.setInt("BannerAdShow", getFirebaseInt(snapshot, "BannerAdShow", 0));
                        taskPreferenceClass.setInt("MainScreenAd", getFirebaseInt(snapshot, "MainScreenAd", 0));
                        taskPreferenceClass.setInt("StartScreen_AD", getFirebaseInt(snapshot, "StartScreen_AD", 0));
                        Task_AppOpenManager.AppOpenAdShow = getFirebaseInt(snapshot, "AppOpenAdShow", 0);
                        taskPreferenceClass.setInt("InerstialClickCount", getFirebaseInt(snapshot, "InerstialClickCount", 0));
                        taskPreferenceClass.setInt("NativeAdIntervalCount", getFirebaseInt(snapshot, "NativeAdIntervalCount", 0));
                        taskPreferenceClass.setInt("RewardUnlockEnabled", getFirebaseInt(snapshot, "RewardUnlockEnabled", 0));
                        taskPreferenceClass.setInt("FreeQuestionsCount", getFirebaseInt(snapshot, "FreeQuestionsCount", 20));

                        Log.e("FIREBASE_ADS", "=================================================================");
                        Log.e("FIREBASE_ADS", "🎉 [FIREBASE CONFIG RECEIVED SUCCESSFULLY] 🎉");
                        Log.e("FIREBASE_ADS", "----------------------- AD UNITS --------------------------------");
                        Log.e("FIREBASE_ADS", "🔹 GoogleBannerAd        : " + taskPreferenceClass.getAdsId("GoogleBannerAd"));
                        Log.e("FIREBASE_ADS", "🔹 GoogleInterstitialAd  : " + taskPreferenceClass.getAdsId("GoogleInterstitialAd"));
                        Log.e("FIREBASE_ADS", "🔹 GoogleNativeAd        : " + taskPreferenceClass.getAdsId("GoogleNativeAd"));
                        Log.e("FIREBASE_ADS", "🔹 GoogleAppopenAd       : " + taskPreferenceClass.getAdsId("GoogleAppopenAd"));
                        Log.e("FIREBASE_ADS", "🔹 GoogleRewardedAd      : " + taskPreferenceClass.getAdsId("GoogleRewardedAd"));
                        Log.e("FIREBASE_ADS", "🔹 GoogleInterstialReward: " + taskPreferenceClass.getAdsId("GoogleInterstialRewardAd"));
                        Log.e("FIREBASE_ADS", "----------------------- AD CONTROLS -----------------------------");
                        Log.e("FIREBASE_ADS", "⚙️ BannerAdShow          : " + (taskPreferenceClass.getInt("BannerAdShow") == 1 ? "ENABLED (1)" : "DISABLED (0)"));
                        Log.e("FIREBASE_ADS", "⚙️ NativeAdShow          : " + (taskPreferenceClass.getInt("NativeAdShow") == 1 ? "ENABLED (1)" : "DISABLED (0)"));
                        Log.e("FIREBASE_ADS", "⚙️ AppOpenAdShow         : " + (Task_AppOpenManager.AppOpenAdShow == 1 ? "ENABLED (1)" : "DISABLED (0)"));
                        Log.e("FIREBASE_ADS", "⚙️ SplashScreenAdsManage : " + taskPreferenceClass.getInt("splashscreen"));
                        Log.e("FIREBASE_ADS", "⚙️ InerstialClickCount   : Every " + taskPreferenceClass.getInt("InerstialClickCount") + " clicks");
                        Log.e("FIREBASE_ADS", "⚙️ NativeAdIntervalCount : Every " + taskPreferenceClass.getInt("NativeAdIntervalCount") + " items");
                        Log.e("FIREBASE_ADS", "⚙️ RewardUnlockEnabled   : " + (taskPreferenceClass.getInt("RewardUnlockEnabled") == 1 ? "ENABLED (1)" : "DISABLED (0)"));
                        Log.e("FIREBASE_ADS", "⚙️ FreeQuestionsCount    : First " + taskPreferenceClass.getInt("FreeQuestionsCount") + " questions free");
                        Log.e("FIREBASE_ADS", "----------------------- APP UPDATE ------------------------------");
                        Log.e("FIREBASE_ADS", "🚀 UpdateAvailable       : " + taskPreferenceClass.getInt("UpdateAvailable"));
                        Log.e("FIREBASE_ADS", "🚀 UpdateVersionCode     : " + taskPreferenceClass.getInt("UpdateVersionCode") + " (Current: " + BuildConfig.VERSION_CODE + ")");
                        Log.e("FIREBASE_ADS", "🚀 UpdateVersionName     : " + taskPreferenceClass.getDataType("UpdateVersionName", "") + " (Current: " + BuildConfig.VERSION_NAME + ")");
                        Log.e("FIREBASE_ADS", "🚀 ForceUpdate           : " + (taskPreferenceClass.getInt("ForceUpdate") == 1 ? "MANDATORY (1)" : "OPTIONAL (0)"));
                        Log.e("FIREBASE_ADS", "=================================================================");

                    } catch (Exception e) {
                        Log.e("FIREBASE_ADS", "❌ Error parsing Firebase ads data: " + e.getMessage());
                    }

                    try {
                        int updateAvailable = taskPreferenceClass.getInt("UpdateAvailable", 0);
                        int targetVersionCode = taskPreferenceClass.getInt("UpdateVersionCode", BuildConfig.VERSION_CODE);
                        String targetVersionName = taskPreferenceClass.getDataType("UpdateVersionName", BuildConfig.VERSION_NAME);
                        int isForceUpdate = taskPreferenceClass.getInt("ForceUpdate", 0);
                        String updateMessage = taskPreferenceClass.getDataType("UpdateMessage", "");

                        boolean isNewerVersion = (targetVersionCode > BuildConfig.VERSION_CODE) ||
                                (!targetVersionName.equalsIgnoreCase(BuildConfig.VERSION_NAME) && !targetVersionName.trim().isEmpty());

                        if (updateAvailable == 1 && isNewerVersion) {
                            Log.e("FIREBASE_ADS", "🔔 TRIGGERING UPDATE DIALOG -> Version: " + targetVersionName + " (Force: " + isForceUpdate + ")");
                            showUpdateDialog(targetVersionName, isForceUpdate == 1, updateMessage);
                        } else {
                            Log.e("FIREBASE_ADS", "✅ No update required (App is up to date). Proceeding to splash flow.");
                            next();
                        }
                    } catch (Exception e) {
                        next();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("Firebase_Ads_data", "Database error: " + error.getMessage());
                    next();
                }
            });
            } catch (Exception e) {
                Log.e("Firebase_Ads_data", "Error connecting to Firebase: " + e.getMessage());
                next();
            }
        } else {
            next();
        }
    }

    private String getFirebaseString(DataSnapshot snapshot, String key, String defaultValue) {
        try {
            if (snapshot != null && snapshot.hasChild(key) && snapshot.child(key).getValue() != null) {
                String val = snapshot.child(key).getValue().toString().trim();
                if (!val.isEmpty()) return val;
            }
        } catch (Exception ignored) {}
        return defaultValue;
    }

    private int getFirebaseInt(DataSnapshot snapshot, String key, int defaultValue) {
        try {
            if (snapshot != null && snapshot.hasChild(key) && snapshot.child(key).getValue() != null) {
                return Integer.parseInt(snapshot.child(key).getValue().toString().trim());
            }
        } catch (Exception ignored) {}
        return defaultValue;
    }

    private Dialog materialDialog = null;

    private void showUpdateDialog(String versionName, boolean isForceUpdate, String customMsg) {
        runOnUiThread(() -> {
            if (isFinishing() || isDestroyed()) return;
            if (materialDialog != null && materialDialog.isShowing()) return;

            materialDialog = new Dialog(Task_SplashScreenActivity.this);
            materialDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            materialDialog.setContentView(R.layout.task_reward_dialog);
            materialDialog.setCancelable(!isForceUpdate);
            materialDialog.setCanceledOnTouchOutside(!isForceUpdate);
            if (materialDialog.getWindow() != null) {
                materialDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                materialDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            TextView tv_title = materialDialog.findViewById(R.id.title);
            TextView tv_description = materialDialog.findViewById(R.id.description);
            TextView button1 = materialDialog.findViewById(R.id.button1);
            TextView button2 = materialDialog.findViewById(R.id.button2);

            tv_title.setText(isForceUpdate ? "Important App Update" : "New Update Available");

            if (customMsg != null && !customMsg.trim().isEmpty()) {
                tv_description.setText(customMsg);
            } else if (versionName != null && !versionName.isEmpty()) {
                tv_description.setText("A newer version (v" + versionName + ") of RTO Vehicle App is ready with updated 2026 questions, live fuel rates, and improvements.");
            } else {
                tv_description.setText("A newer version of RTO Vehicle App is ready with updated 2026 questions, live fuel rates, and improvements.");
            }

            button2.setText("Update Now");
            button2.setOnClickListener(v -> {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    } catch (Exception e) {
                        Toast.makeText(Task_SplashScreenActivity.this, "Unable to find Google Play Store", Toast.LENGTH_LONG).show();
                    }
                }
                if (!isForceUpdate) {
                    if (materialDialog != null && materialDialog.isShowing()) {
                        materialDialog.dismiss();
                    }
                    next();
                }
            });

            if (isForceUpdate) {
                if (button1 != null) button1.setVisibility(View.GONE);
            } else {
                if (button1 != null) {
                    button1.setVisibility(View.VISIBLE);
                    button1.setText("Later");
                    button1.setOnClickListener(v -> {
                        if (materialDialog != null && materialDialog.isShowing()) {
                            materialDialog.dismiss();
                        }
                        next();
                    });
                }
            }

            materialDialog.show();
        });
    }

    private void startIntent() {
        if (BuildConfig.DEBUG) {
            callMainActivity();
            return;
        }
        int splashPref = taskPreferenceClass.getInt("splashscreen", 1);
        if (splashPref == 1) {
            callStartActivity();
        } else if (splashPref == 2) {
            this.interstitialFB = new com.facebook.ads.InterstitialAd(Task_SplashScreenActivity.this, taskPreferenceClass.getAdsId("FbInterstitialAd"));
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    Log.e("TAG", "Interstitial ad displayed.");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    callMainActivity();
                }

                @Override
                public void onError(Ad ad, com.facebook.ads.AdError adError) {
                    callMainActivity();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (interstitialFB != null) {
                        interstitialFB.show();
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };

            interstitialFB.loadAd(interstitialFB.buildLoadAdConfig().withAdListener(interstitialAdListener).build());

        } else if (splashPref == 3) {
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
        } else {
            // 0 = Direct without any ad on Splash
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
