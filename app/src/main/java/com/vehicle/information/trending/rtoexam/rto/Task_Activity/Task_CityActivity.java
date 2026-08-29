package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_ListAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_DataBase.Task_DBHelper;
import com.vehicle.information.trending.rtoexam.rto.Task_DataBase.Task_RTODataProvider;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_CitiesModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.io.IOException;
import java.util.ArrayList;

public class Task_CityActivity extends AppCompatActivity {
    public static ArrayList<Task_CitiesModel> arrlist;
    ListView lv_cities;
    Task_ListAdapter cityAdapter;
    SQLiteDatabase myDB;
    String str_states_name;

    TextView tv_state_title;
    TextView tv_state_subtitle;
    EditText et_search_city;
    ImageView iv_clear_search_city;
    LinearLayout ll_empty_state;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_offices_city);

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
                Task_CityActivity.this.onBackPressed();
            }
        });

        this.str_states_name = getIntent().getStringExtra("states_name");
        if (this.str_states_name == null) {
            this.str_states_name = "RTO Offices";
        }

        this.tv_state_title = findViewById(R.id.tv_state_title);
        this.tv_state_subtitle = findViewById(R.id.tv_state_subtitle);
        this.et_search_city = findViewById(R.id.et_search_city);
        this.iv_clear_search_city = findViewById(R.id.iv_clear_search_city);
        this.ll_empty_state = findViewById(R.id.ll_empty_state);
        this.lv_cities = findViewById(R.id.lv_cities);

        this.tv_state_title.setText(this.str_states_name);
        this.tv_state_subtitle.setText("Find RTO codes & office addresses in " + this.str_states_name);

        fill_category(this.str_states_name);

        this.et_search_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString();
                if (cityAdapter != null) {
                    cityAdapter.filter(query);
                    if (iv_clear_search_city != null) {
                        iv_clear_search_city.setVisibility(query.length() > 0 ? View.VISIBLE : View.GONE);
                    }
                    if (ll_empty_state != null) {
                        ll_empty_state.setVisibility(cityAdapter.getCount() == 0 ? View.VISIBLE : View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        this.iv_clear_search_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_city.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void fill_category(String str) {
        arrlist = Task_RTODataProvider.getRTOOfficesForState(this, str);

        this.cityAdapter = new Task_ListAdapter(this, arrlist, this.str_states_name);
        this.lv_cities.setAdapter(this.cityAdapter);

        if (this.ll_empty_state != null) {
            this.ll_empty_state.setVisibility(arrlist.isEmpty() ? View.VISIBLE : View.GONE);
        }

        this.lv_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                Task_CitiesModel selectedModel = cityAdapter.getItem(i);
                int originalPos = arrlist.indexOf(selectedModel);
                if (originalPos < 0) originalPos = i;

                Intent intent = new Intent(Task_CityActivity.this, Task_OfficeDetailsActivity.class);
                intent.putExtra("states_name", Task_CityActivity.this.str_states_name);
                intent.putExtra("position", originalPos);
                intent.putExtra("code", selectedModel.getCode());
                intent.putExtra("district", selectedModel.getDistrict());
                intent.putExtra("address", selectedModel.getAddress());
                intent.putExtra("phone", selectedModel.getPhone());
                intent.putExtra("field2", selectedModel.getField2());
                intent.putExtra("field3", selectedModel.getField3());
                intent.putExtra("field5", selectedModel.getField5());

                MyApplication.showInterstitialAd(Task_CityActivity.this, () -> Task_CityActivity.this.startActivity(intent));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.myDB != null && this.myDB.isOpen()) {
            this.myDB.close();
        }
    }
}