package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_StateListAdpter;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_OficeActivity extends AppCompatActivity {
    String[] ary_states = {"Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Daman and Diu", "Delhi", "Dadra and Nagar Haveli", "Goa", "Gujarat", "Himachal Pradesh", "Haryana", "Jharkhand", "Jammu and Kashmir", "Karnataka", "Kerala", "Lakshadweep", "Maharashtra", "Meghalaya", "Manipur", "Madhya Pradesh", "Mizoram", "Nagaland", "Odisha", "Punjab", "Puducherry", "Rajasthan", "Tamil Nadu", "Tripura", "Telangana", "Uttarakhand", "Uttar Pradesh", "West Bengal"};

    ListView lv_states;
    Task_StateListAdpter rtoOffice_statesList_adp;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_offices);

      // //AdsManager.getInstance().loadBanner(this);
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
                Task_OficeActivity.this.onBackPressed();
            }
        });
        this.lv_states = (ListView) findViewById(R.id.lv_states);
        Task_StateListAdpter m_rtoOfcStateLstAdpter = new Task_StateListAdpter(this, this.ary_states);
        this.rtoOffice_statesList_adp = m_rtoOfcStateLstAdpter;
        this.lv_states.setAdapter((ListAdapter) m_rtoOfcStateLstAdpter);
        this.lv_states.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Intent intent = new Intent(Task_OficeActivity.this, Task_CityActivity.class);
                intent.putExtra("states_name", Task_OficeActivity.this.ary_states[i]);
//                Task_OficeActivity.this.startActivity(intent);
                MyApplication.showInterstitialAd(Task_OficeActivity.this, () -> Task_OficeActivity.this.startActivity(intent));
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
