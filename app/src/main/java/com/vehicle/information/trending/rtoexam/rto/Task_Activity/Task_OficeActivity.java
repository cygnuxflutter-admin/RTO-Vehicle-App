package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    String[] ary_states = {
            "Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh",
            "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli and Daman and Diu",
            "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir",
            "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh",
            "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha",
            "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana",
            "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"
    };

    ListView lv_states;
    Task_StateListAdpter rtoOffice_statesList_adp;
    EditText et_search;
    ImageView iv_clear_search;
    LinearLayout ll_empty_state;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_offices);

        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
                findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task_OficeActivity.this.onBackPressed();
            }
        });

        this.lv_states = findViewById(R.id.lv_states);
        this.et_search = findViewById(R.id.et_search);
        this.iv_clear_search = findViewById(R.id.iv_clear_search);
        this.ll_empty_state = findViewById(R.id.ll_empty_state);

        this.rtoOffice_statesList_adp = new Task_StateListAdpter(this, this.ary_states);
        this.lv_states.setAdapter(this.rtoOffice_statesList_adp);

        this.et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                rtoOffice_statesList_adp.filter(query);
                if (iv_clear_search != null) {
                    iv_clear_search.setVisibility(query.length() > 0 ? View.VISIBLE : View.GONE);
                }
                if (ll_empty_state != null) {
                    ll_empty_state.setVisibility(rtoOffice_statesList_adp.getCount() == 0 ? View.VISIBLE : View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        this.iv_clear_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });

        this.lv_states.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                String selectedState = rtoOffice_statesList_adp.getItem(i);
                Intent intent = new Intent(Task_OficeActivity.this, Task_CityActivity.class);
                intent.putExtra("states_name", selectedState);
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