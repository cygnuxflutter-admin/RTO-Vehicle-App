package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_ListAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_DataBase.Task_DBHelper;
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
    SQLiteDatabase myDB;
    String str_field2;
    String str_field3;
    String str_field4;
    String str_field5;
    String str_states_name;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_offices_city);
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
                Task_CityActivity.this.onBackPressed();
            }
        });
        this.str_states_name = getIntent().getStringExtra("states_name");
        this.lv_cities = (ListView) findViewById(R.id.lv_cities);
        fill_category(this.str_states_name);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("Range")
    private void fill_category(String str) {
        Task_DBHelper m_rtoTaskDBHelper = new Task_DBHelper(this);
        try {
            m_rtoTaskDBHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            m_rtoTaskDBHelper.close();
            throw th;
        }
        m_rtoTaskDBHelper.close();
        try {
            this.myDB = m_rtoTaskDBHelper.openDataBase();
            arrlist = new ArrayList<>();
            SQLiteDatabase sQLiteDatabase = this.myDB;
            Cursor rawQuery = sQLiteDatabase.rawQuery("select * from rto_data where field2 LIKE '%" + str + "%'", null);
            if (rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                do {
                    this.str_field2 = rawQuery.getString(rawQuery.getColumnIndex("field2"));
                    this.str_field3 = rawQuery.getString(rawQuery.getColumnIndex("field3"));
                    this.str_field4 = rawQuery.getString(rawQuery.getColumnIndex("field4"));
                    this.str_field5 = rawQuery.getString(rawQuery.getColumnIndex("field5"));
                    Task_CitiesModel m_rtoCities = new Task_CitiesModel();
                    m_rtoCities.setField2(this.str_field2);
                    m_rtoCities.setField3(this.str_field3);
                    m_rtoCities.setField4(this.str_field4);
                    m_rtoCities.setField5(this.str_field5);
                    arrlist.add(m_rtoCities);
                } while (rawQuery.moveToNext());

            }
        } catch (SQLException unused) {
        }
        this.lv_cities.setAdapter((android.widget.ListAdapter) new Task_ListAdapter(this, arrlist));
        this.lv_cities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {

                Intent intent = new Intent(Task_CityActivity.this, Task_OfficeDetailsActivity.class);
                intent.putExtra("states_name", Task_CityActivity.this.str_states_name);
                intent.putExtra("position", i);
//                Task_CityActivity.this.startActivity(intent);
                MyApplication.showInterstitialAd(Task_CityActivity.this, () -> Task_CityActivity.this.startActivity(intent));

            }
        });
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }


}
