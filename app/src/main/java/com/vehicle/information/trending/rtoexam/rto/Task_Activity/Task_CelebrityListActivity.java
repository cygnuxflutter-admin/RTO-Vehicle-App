package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_CelebrityListActivity extends AppCompatActivity {

    private Task_PreferenceClass taskPreferenceClass;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_celebrities_lst);

       //AdsManager.getInstance().loadBanner(this);

        taskPreferenceClass = new Task_PreferenceClass(this);

        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);
        if (taskPreferenceClass.getInt("NativeAdShow") == 1){
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
        } else {
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
        }

        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ((ImageView) findViewById(R.id.ll_actor)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "ACTORS");
//                startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
        ((ImageView) findViewById(R.id.ll_actress)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "ACTRESSES");
              //  startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
        ((ImageView) findViewById(R.id.ll_dancers)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "DANCERS");
              //  startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
        ((ImageView) findViewById(R.id.ll_singers)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "SINGERS");
               // startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
        ((ImageView) findViewById(R.id.ll_sports_person)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "SPORTS_PERSONS");
             //   startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
        ((ImageView) findViewById(R.id.ll_mrperfect)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "TYCOONS");
               // startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
        ((ImageView) findViewById(R.id.ll_politician)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Task_CelebrityListActivity.this.getApplicationContext(), Task_TrendPersonVehiclesActivity.class);
                intent.putExtra("PERSON_TYPE", "POLITICIANS");
               // startActivity(intent);
                MyApplication.showInterstitialAd(Task_CelebrityListActivity.this, () -> Task_CelebrityListActivity.this.startActivity(intent));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }


}
