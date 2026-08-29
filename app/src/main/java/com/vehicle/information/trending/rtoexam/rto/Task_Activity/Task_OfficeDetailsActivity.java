package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_CitiesModel;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;

public class Task_OfficeDetailsActivity extends AppCompatActivity {
    TextView code_hero;
    TextView distinct_hero;
    TextView state_hero;

    TextView code_txt;
    TextView distinct_txt;
    TextView state_txt;
    TextView address_txt;
    TextView contact_txt;

    FrameLayout map_img;
    FrameLayout contact_img;
    LinearLayout btn_action_call;
    LinearLayout btn_action_map;
    LinearLayout btn_action_copy;

    String rtoCode = "";
    String rtoDistrict = "";
    String rtoState = "";
    String rtoAddress = "";
    String rtoPhone = "";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_office_detail);

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
                Task_OfficeDetailsActivity.this.onBackPressed();
            }
        });

        this.code_hero = findViewById(R.id.code_hero);
        this.distinct_hero = findViewById(R.id.distinct_hero);
        this.state_hero = findViewById(R.id.state_hero);

        this.code_txt = findViewById(R.id.code_txt);
        this.distinct_txt = findViewById(R.id.distinct_txt);
        this.state_txt = findViewById(R.id.state_txt);
        this.address_txt = findViewById(R.id.address_txt);
        this.contact_txt = findViewById(R.id.contact_txt);

        this.map_img = findViewById(R.id.map_img);
        this.contact_img = findViewById(R.id.contact_img);
        this.btn_action_call = findViewById(R.id.btn_action_call);
        this.btn_action_map = findViewById(R.id.btn_action_map);
        this.btn_action_copy = findViewById(R.id.btn_action_copy);

        // Retrieve data
        String stateFromIntent = getIntent().getStringExtra("states_name");
        int pos = getIntent().getIntExtra("position", 0);

        this.rtoCode = getIntent().getStringExtra("code");
        this.rtoDistrict = getIntent().getStringExtra("district");
        this.rtoAddress = getIntent().getStringExtra("address");
        this.rtoPhone = getIntent().getStringExtra("phone");
        this.rtoState = stateFromIntent != null ? stateFromIntent : "";

        String statesTitle = getIntent().getStringExtra("states_title");
        if (statesTitle != null && !statesTitle.isEmpty()) {
            Task_CitiesModel temp = new Task_CitiesModel();
            temp.setField2(statesTitle);
            if (this.rtoCode == null || this.rtoCode.isEmpty()) this.rtoCode = temp.getCode();
            if (this.rtoDistrict == null || this.rtoDistrict.isEmpty()) this.rtoDistrict = temp.getDistrict();
            if (this.rtoState.isEmpty()) this.rtoState = temp.getState();
        }
        String statesAddress = getIntent().getStringExtra("states_address");
        if (statesAddress != null && (this.rtoAddress == null || this.rtoAddress.isEmpty())) {
            this.rtoAddress = statesAddress;
        }
        String statesPhone = getIntent().getStringExtra("states_phone");
        if (statesPhone != null && (this.rtoPhone == null || this.rtoPhone.isEmpty())) {
            this.rtoPhone = statesPhone;
        }

        // Fallback to arraylist if extras were not populated
        if ((this.rtoCode == null || this.rtoCode.isEmpty()) && Task_CityActivity.arrlist != null && pos < Task_CityActivity.arrlist.size()) {
            Task_CitiesModel model = Task_CityActivity.arrlist.get(pos);
            if (model != null) {
                this.rtoCode = model.getCode();
                this.rtoDistrict = model.getDistrict();
                this.rtoAddress = model.getAddress();
                this.rtoPhone = model.getPhone();
                if (this.rtoState.isEmpty()) {
                    this.rtoState = model.getState();
                }
            }
        }

        if (this.rtoCode == null || this.rtoCode.isEmpty()) this.rtoCode = "RTO";
        if (this.rtoDistrict == null || this.rtoDistrict.isEmpty()) this.rtoDistrict = "District Office";
        if (this.rtoState == null || this.rtoState.isEmpty()) this.rtoState = "India";
        if (this.rtoAddress == null || this.rtoAddress.isEmpty()) this.rtoAddress = "Address not available";
        if (this.rtoPhone == null || this.rtoPhone.isEmpty()) this.rtoPhone = "Not Available";

        // Bind data
        this.code_hero.setText(this.rtoCode);
        this.distinct_hero.setText(this.rtoDistrict);
        this.state_hero.setText(this.rtoState);

        this.code_txt.setText(this.rtoCode);
        this.distinct_txt.setText(this.rtoDistrict);
        this.state_txt.setText(this.rtoState);
        this.address_txt.setText(this.rtoAddress);
        this.contact_txt.setText(this.rtoPhone);

        // Action: Call
        View.OnClickListener callListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rtoPhone.equals("Not Available") || rtoPhone.trim().isEmpty()) {
                    Toast.makeText(Task_OfficeDetailsActivity.this, "Contact number not available", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + rtoPhone.replaceAll("[^0-9+]", "")));
                try {
                    Task_OfficeDetailsActivity.this.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(Task_OfficeDetailsActivity.this, "Unable to dial: " + rtoPhone, Toast.LENGTH_SHORT).show();
                }
            }
        };
        this.btn_action_call.setOnClickListener(callListener);
        this.contact_img.setOnClickListener(callListener);

        // Action: Map
        View.OnClickListener mapListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = rtoDistrict + " RTO Office " + rtoState + ", " + rtoAddress;
                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(query));
                Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
                intent.setPackage("com.google.android.apps.maps");
                try {
                    Task_OfficeDetailsActivity.this.startActivity(intent);
                } catch (Exception e) {
                    Intent webMap = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=" + Uri.encode(query)));
                    Task_OfficeDetailsActivity.this.startActivity(webMap);
                }
            }
        };
        this.btn_action_map.setOnClickListener(mapListener);
        this.map_img.setOnClickListener(mapListener);

        // Action: Copy / Share
        this.btn_action_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String details = "RTO Office: " + rtoCode + " - " + rtoDistrict + "\n"
                        + "State: " + rtoState + "\n"
                        + "Address: " + rtoAddress + "\n"
                        + "Contact: " + rtoPhone;
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("RTO Details", details);
                if (clipboard != null) {
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(Task_OfficeDetailsActivity.this, "Office details copied to clipboard!", Toast.LENGTH_SHORT).show();
                }
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