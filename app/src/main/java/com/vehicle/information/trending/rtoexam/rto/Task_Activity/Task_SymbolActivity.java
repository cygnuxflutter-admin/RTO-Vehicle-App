package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;

public class Task_SymbolActivity extends AppCompatActivity {
    private Task_PreferenceClass taskPreferenceClass;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_symbol);
       //AdsManager.getInstance().loadBanner(this);
        taskPreferenceClass = new Task_PreferenceClass(this);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1){
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_SymbolActivity.this.onBackPressed();
            }
        });
        ((CardView) findViewById(R.id.cv_mandatory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Mandatory");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () ->  Task_SymbolActivity.this.startActivity(intent));
            }
        });

        ((CardView) findViewById(R.id.cv_cautionary)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Cautionary");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () ->   Task_SymbolActivity.this.startActivity(intent));

            }
        });
        ((CardView) findViewById(R.id.cv_informatory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Informatory");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () ->  Task_SymbolActivity.this.startActivity(intent));



            }
        });
        ((CardView) findViewById(R.id.cv_roadsignals)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Road & Signals");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () ->  Task_SymbolActivity.this.startActivity(intent));

            }
        });
        ((CardView) findViewById(R.id.cv_drivingrules)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Driving Rules");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () ->  Task_SymbolActivity.this.startActivity(intent));

            }
        });
        ((CardView) findViewById(R.id.cv_trafficpolice_signals)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_SymbolActivity.this, Task_SymbolDetailActivity.class);
                intent.putExtra("passvalue", "Traffic Police Signals");
                MyApplication.showInterstitialAd(Task_SymbolActivity.this, () ->  Task_SymbolActivity.this.startActivity(intent));

            }
        });
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
