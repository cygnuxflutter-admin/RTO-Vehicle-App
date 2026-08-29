package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SymbolActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_symbol);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                if (rl_ad != null) rl_ad.setVisibility(View.GONE);
                View rlBanner = findViewById(R.id.rlBanner);
                if (rlBanner != null) rlBanner.setVisibility(View.GONE);
            }
        }

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(view -> onBackPressed());
        }

        View cvMandatory = findViewById(R.id.cv_mandatory);
        if (cvMandatory != null) {
            cvMandatory.setOnClickListener(view -> {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Mandatory");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
            });
        }

        View cvCautionary = findViewById(R.id.cv_cautionary);
        if (cvCautionary != null) {
            cvCautionary.setOnClickListener(view -> {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Cautionary");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
            });
        }

        View cvInformatory = findViewById(R.id.cv_informatory);
        if (cvInformatory != null) {
            cvInformatory.setOnClickListener(view -> {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Informatory");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
            });
        }

        View cvRoadSignals = findViewById(R.id.cv_roadsignals);
        if (cvRoadSignals != null) {
            cvRoadSignals.setOnClickListener(view -> {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Road & Signals");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
            });
        }

        View cvDrivingRules = findViewById(R.id.cv_drivingrules);
        if (cvDrivingRules != null) {
            cvDrivingRules.setOnClickListener(view -> {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Driving Rules");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
            });
        }

        View cvTrafficPolice = findViewById(R.id.cv_trafficpolice_signals);
        if (cvTrafficPolice != null) {
            cvTrafficPolice.setOnClickListener(view -> {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Traffic Police Signals");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () -> Task_SymbolActivity.this.startActivity(intent));
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}