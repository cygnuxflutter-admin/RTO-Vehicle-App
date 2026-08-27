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

import java.util.Objects;


public class Task_SplashScreenActivity extends AppCompatActivity {
    private Task_PreferenceClass taskPreferenceClass;
    public FirebaseDatabase database;
    private DatabaseReference project_data2;
    private InterstitialAd interstitial = null;
    public com.facebook.ads.InterstitialAd interstitialFB;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.task_activity_splash_screen);


        taskPreferenceClass = new Task_PreferenceClass(this);
        MyApplication.isAdsSplash = true;
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            getData();
        } else {
            Toast.makeText(this, "no internet", Toast.LENGTH_SHORT).show();
            //Task_MaterialDialogUtils.getInstance().errorDialog(this, getResources().getString(R.string.internet_error));
        }
    }

    public void startToMainActivity() {
        startIntent();
    }

    public void next() {
        new Handler().postDelayed(new Runnable() {
            @Override // java.lang.Runnable
            public final void run() {

                startToMainActivity();
            }
        }, 3000);
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
////                      ----------------------------------------------- Live ADS -----------------------------------------------
                        taskPreferenceClass.setDataType("GoogleBannerAd", Objects.requireNonNull(snapshot.child("GoogleBannerAd").getValue()).toString());
                        taskPreferenceClass.setDataType("GoogleAppopenAd", Objects.requireNonNull(snapshot.child("GoogleAppopenAd").getValue()).toString());
                        taskPreferenceClass.setDataType("GoogleInterstitialAd", Objects.requireNonNull(snapshot.child("GoogleInterstitialAd").getValue()).toString());
                        taskPreferenceClass.setDataType("GoogleInterstialRewardAd", Objects.requireNonNull(snapshot.child("GoogleInterstialRewardAd").getValue()).toString());
                        taskPreferenceClass.setDataType("GoogleRewardedAd", Objects.requireNonNull(snapshot.child("GoogleRewardedAd").getValue()).toString());
                        taskPreferenceClass.setDataType("GoogleNativeAd", Objects.requireNonNull(snapshot.child("GoogleNativeAd").getValue()).toString());
//
                        taskPreferenceClass.setDataType("FbNativeAd", Objects.requireNonNull(snapshot.child("FbNativeAd").getValue()).toString());
                        taskPreferenceClass.setDataType("FbInterstitialAd", Objects.requireNonNull(snapshot.child("FbInterstitialAd").getValue()).toString());
                        taskPreferenceClass.setDataType("FbBannerAd", Objects.requireNonNull(snapshot.child("FbBannerAd").getValue()).toString());
//
//
                        taskPreferenceClass.setDataType("AdxBannerAdunitID", Objects.requireNonNull(snapshot.child("AdxBannerAdunitID").getValue()).toString());
                        taskPreferenceClass.setDataType("AdxInterstitalAdunitID", Objects.requireNonNull(snapshot.child("AdxInterstitalAdunitID").getValue()).toString());
                        taskPreferenceClass.setDataType("AdxRewardVideoUnitID", Objects.requireNonNull(snapshot.child("AdxRewardVideoUnitID").getValue()).toString());
                        taskPreferenceClass.setDataType("AdxNativeUnitID", Objects.requireNonNull(snapshot.child("AdxNativeUnitID").getValue()).toString());
                        taskPreferenceClass.setDataType("AdxAppOpenID", Objects.requireNonNull(snapshot.child("AdxAppOpenID").getValue()).toString());
                        taskPreferenceClass.setDataType("CollapsibleBannerID", Objects.requireNonNull(snapshot.child("CollapsibleBannerID").getValue()).toString());

                        //                   ----------------------------------------------- Test ADS -----------------------------------------------

//                        taskPreferenceClass.setDataType("GoogleNativeAd", "ca-app-pub-3940256099942544/2247696110");
//                        taskPreferenceClass.setDataType("GoogleAppopenAd", "ca-app-pub-3940256099942544/9257395921");
//                        taskPreferenceClass.setDataType("GoogleBannerAd", "ca-app-pub-3940256099942544/6300978111");
//                        taskPreferenceClass.setDataType("GoogleInterstitialAd", "ca-app-pub-3940256099942544/1033173712");
//                        taskPreferenceClass.setDataType("GoogleRewardedAd", "ca-app-pub-3940256099942544/5224354917");
//                        taskPreferenceClass.setDataType("GoogleInterstialRewardAd", "ca-app-pub-3940256099942544/5354046379");
//                        taskPreferenceClass.setDataType("CollapsibleBannerID","ca-app-pub-3940256099942544/9214589741");
//
//                        taskPreferenceClass.setDataType("FbNativeAd", "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
//                        taskPreferenceClass.setDataType("FbInterstitialAd", "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
//                        taskPreferenceClass.setDataType("FbBannerAd", "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID");
//
//                        taskPreferenceClass.setDataType("AdxBannerAdunitID", "ca-app-pub-3940256099942544/6300978111");
//                        taskPreferenceClass.setDataType("AdxInterstitalAdunitID", "ca-app-pub-3940256099942544/1033173712");
//                        taskPreferenceClass.setDataType("AdxRewardVideoUnitID", "ca-app-pub-3940256099942544/5224354917");
//                        taskPreferenceClass.setDataType("AdxNativeUnitID", "ca-app-pub-3940256099942544/2247696110");
//                        taskPreferenceClass.setDataType("AdxAppOpenID", "ca-app-pub-3940256099942544/9257395921");

//                    --------------------------------------------------------------------------------------------------------
                        taskPreferenceClass.setInt("NativeAdShow", Integer.parseInt(snapshot.child("NativeAdShow").getValue().toString()));
                        taskPreferenceClass.setInt("BannerAdShow", Integer.parseInt(snapshot.child("BannerAdShow").getValue().toString()));
                        taskPreferenceClass.setInt("MainScreenAd", Integer.parseInt(snapshot.child("MainScreenAd").getValue().toString()));
                        taskPreferenceClass.setInt("StartScreen_AD", Integer.parseInt(snapshot.child("StartScreen_AD").getValue().toString()));

                        Task_AppOpenManager.AppOpenAdShow = Integer.parseInt(snapshot.child("AppOpenAdShow").getValue().toString());

                        taskPreferenceClass.setInt("InerstialClickCount", Integer.parseInt(Objects.requireNonNull(snapshot.child("InerstialClickCount").getValue().toString())));//Premium background Ads Type Reward / Full
//                        taskPreferenceClass.setInt("GoogleAdsTime", Integer.parseInt(Objects.requireNonNull(snapshot.child("GoogleAdsTime").getValue().toString())));
                    } catch (Exception e) {
                        e.getMessage();
                    }

                    try {
                        if (taskPreferenceClass.getInt("UpdateAvailable") == 1 && !taskPreferenceClass.getAdsId("UpdateVersionName").equals(BuildConfig.VERSION_NAME)) {

                            @SuppressLint("ResourceType") Dialog materialDialog = new Dialog(Task_SplashScreenActivity.this, 16974126);
                            materialDialog.requestWindowFeature(1);
                            materialDialog.setContentView(R.layout.task_reward_dialog);
                            materialDialog.setCancelable(false);

                            if (!materialDialog.isShowing()) materialDialog.show();

                            TextView tv_title = materialDialog.findViewById(R.id.title);
                            TextView tv_description = materialDialog.findViewById(R.id.description);

                            TextView button1 = materialDialog.findViewById(R.id.button1);
                            TextView button2 = materialDialog.findViewById(R.id.button2);

                            tv_title.setText("Update is Available");
//                        tv_description.setText(message);
                            button1.setText("Cancal");
                            button2.setText("Update Now");
                            button2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    try {
                                        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
                                    } catch (ActivityNotFoundException unused) {
                                        Toast.makeText(Task_SplashScreenActivity.this, " unable to find market app", Toast.LENGTH_LONG).show();
                                    }

                                    if (materialDialog != null && materialDialog.isShowing()) {
                                        materialDialog.dismiss();
                                        next();
                                    }
                                }
                            });
                            button1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (materialDialog != null && materialDialog.isShowing()) {
                                        materialDialog.dismiss();
                                        next();
                                    }
                                }
                            });

                        } else {
                            next();
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }

//
//                    Intent intent = new Intent(getApplicationContext(), Task_MainActivity.class);
//                    startActivity(intent);
//                    finish();
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

    private void startIntent() {
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
