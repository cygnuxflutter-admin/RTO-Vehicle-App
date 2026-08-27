package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.InterstitialAd;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_CitiesModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;


public class Task_OfficeDetailsActivity extends AppCompatActivity {
    TextView address_txt;
    ArrayList<Task_CitiesModel> arrlist;
    TextView code_txt;
    ImageView contact_img;
    TextView contact_txt;
    TextView distinct_txt;
    private InterstitialAd interstitialAd;
    ImageView map_img;
    int pos;
    TextView state_txt;
    String str_states_name;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_office_detail);
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


        ((ImageView) findViewById(R.id.iv_back)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_OfficeDetailsActivity.this.onBackPressed();
            }
        });
        this.code_txt = (TextView) findViewById(R.id.code_txt);
        this.distinct_txt = (TextView) findViewById(R.id.distinct_txt);
        this.state_txt = (TextView) findViewById(R.id.state_txt);
        this.address_txt = (TextView) findViewById(R.id.address_txt);
        this.contact_txt = (TextView) findViewById(R.id.contact_txt);
        this.map_img = (ImageView) findViewById(R.id.map_img);
        this.contact_img = (ImageView) findViewById(R.id.contact_img);
        this.str_states_name = getIntent().getStringExtra("states_name");
        this.pos = getIntent().getIntExtra("position", 0);
        ArrayList<Task_CitiesModel> arrayList = Task_CityActivity.arrlist;
        this.arrlist = arrayList;
        this.code_txt.setText(arrayList.get(this.pos).getField2().substring(0, 4));
        this.distinct_txt.setText(this.arrlist.get(this.pos).getField2().substring(7));
        this.state_txt.setText(this.str_states_name);
        this.address_txt.setText(this.arrlist.get(this.pos).getField3());
        this.contact_txt.setText(this.arrlist.get(this.pos).getField5());
        this.map_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=" + Task_OfficeDetailsActivity.this.address_txt.getText().toString()));
                intent.setPackage("com.google.android.apps.maps");
                Task_OfficeDetailsActivity.this.startActivity(intent);
            }
        });
        this.contact_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Task_OfficeDetailsActivity.this.contact_txt.getText().toString().equals("")) {
                    Toast.makeText(Task_OfficeDetailsActivity.this, "Contact Not Available", 0).show();
                    return;
                }
                Intent intent = new Intent("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:" + Task_OfficeDetailsActivity.this.contact_txt.getText().toString()));
                Task_OfficeDetailsActivity.this.startActivity(intent);
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
