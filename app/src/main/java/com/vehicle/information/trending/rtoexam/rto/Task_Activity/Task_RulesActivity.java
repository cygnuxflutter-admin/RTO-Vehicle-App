package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
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


public class Task_RulesActivity extends AppCompatActivity {

    ImageView iv_back;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_rules);
       //AdsManager.getInstance().loadBanner(this);

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

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_change:
                Intent intent = new Intent(this, Task_DetailActivity.class);
                intent.putExtra("main_url", "file:///android_asset/addressChange.html");
                intent.putExtra("position", "Address Change");
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent));
              //  startActivity(intent);
                return;
            case R.id.iv_cert_issues:
                Intent intent2 = new Intent(this, Task_DetailActivity.class);
                intent2.putExtra("main_url", "file:///android_asset/duplicateTradeIssue.html");
                intent2.putExtra("position", "Certificate Issues");
               // startActivity(intent2);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent2));
                return;
            case R.id.iv_diplom_vehi:
                Intent intent3 = new Intent(this, Task_DetailActivity.class);
                intent3.putExtra("main_url", "file:///android_asset/diplomatic.html");
                intent3.putExtra("position", "Diplomatic Vehicles");
               // startActivity(intent3);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent3));
                return;
            case R.id.iv_duplicate_rc:
                Intent intent4 = new Intent(this, Task_DetailActivity.class);
                intent4.putExtra("main_url", "file:///android_asset/duplicateRC.html");
                intent4.putExtra("position", "Duplicate RC");
                //startActivity(intent4);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent4));
                return;
            case R.id.iv_hp_endors:
                Intent intent5 = new Intent(this, Task_DetailActivity.class);
                intent5.putExtra("main_url", "file:///android_asset/endorsement.html");
                intent5.putExtra("position", "HP Endorsment");
               // startActivity(intent5);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent5));
                return;
            case R.id.iv_hp_term:
                Intent intent6 = new Intent(this, Task_DetailActivity.class);
                intent6.putExtra("main_url", "file:///android_asset/termination.html");
                intent6.putExtra("position", "HP Termination");
               // startActivity(intent6);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent6));
                return;
            case R.id.iv_no_obj:
                Intent intent7 = new Intent(this, Task_DetailActivity.class);
                intent7.putExtra("main_url", "file:///android_asset/Objection.html");
                intent7.putExtra("position", "No Objection Certificate");
               // startActivity(intent7);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent7));
                return;
            case R.id.iv_owner_trans:
                Intent intent8 = new Intent(this, Task_DetailActivity.class);
                intent8.putExtra("main_url", "file:///android_asset/ownership.html");
                intent8.putExtra("position", "Ownership Transfer");
               // startActivity(intent8);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent8));
                return;
            case R.id.iv_perm_reg:
                Intent intent9 = new Intent(this, Task_DetailActivity.class);
                intent9.putExtra("main_url", "file:///android_asset/permanentRegistration.html");
                intent9.putExtra("position", "Permanent Registration");
                //startActivity(intent9);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent9));
                return;
            case R.id.iv_reassi_vehi:
                Intent intent10 = new Intent(this, Task_DetailActivity.class);
                intent10.putExtra("main_url", "file:///android_asset/reassignment.html");
                intent10.putExtra("position", "Reassignment of Vehicle");
               // startActivity(intent10);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent10));
                return;
            case R.id.iv_regi_display:
                Intent intent11 = new Intent(this, Task_DetailActivity.class);
                intent11.putExtra("main_url", "file:///android_asset/registrationDisplay.html");
                intent11.putExtra("position", "Registration Display");
                //startActivity(intent11);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent11));
                return;
            case R.id.iv_renewal_reg:
                Intent intent12 = new Intent(this, Task_DetailActivity.class);
                intent12.putExtra("main_url", "file:///android_asset/rcRenewal.html");
                intent12.putExtra("position", "Renewal of Registration");
               // startActivity(intent12);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent12));
                return;
            case R.id.iv_temp_reg:
                Intent intent13 = new Intent(this, Task_DetailActivity.class);
                intent13.putExtra("main_url", "file:///android_asset/temporaryRegistration.html");
                intent13.putExtra("position", "Temporary Registration");
              //  startActivity(intent13);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent13));
                return;
            case R.id.iv_trade_certi:
                Intent intent14 = new Intent(this, Task_DetailActivity.class);
                intent14.putExtra("main_url", "file:///android_asset/trade.html");
                intent14.putExtra("position", "Trade Certificate");
                //startActivity(intent14);
                MyApplication.showInterstitialAd(Task_RulesActivity.this, () -> Task_RulesActivity.this.startActivity(intent14));
                return;
            default:
                return;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
