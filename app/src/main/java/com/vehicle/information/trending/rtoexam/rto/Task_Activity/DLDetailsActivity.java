package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;


public class DLDetailsActivity extends AllBaseActivity {
    public static Activity setting_activity;
    TextView j;
    TextView k;
    TextView l;
    TextView m;
    TextView n;
    TextView o;
    TextView p;
    TextView q;
    TextView r;
    Button s;
    Button t;
    TextView u;
    TextView v;
    TextView w;
    public ArrayList<String> arrayListDemo = new ArrayList<>();
    public ArrayList<String> arrayListDemo2 = new ArrayList<>();
    public ArrayList<String> cov_cat_array = new ArrayList<>();
    public ArrayList<String> cov_issue_date_array = new ArrayList<>();
    public ArrayList<String> vehicle_class_array = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dl_details);


        ((ImageView) findViewById(R.id.img_back)).setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DLDetailsActivity.this.onBackPressed();
            }
        });

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


        setting_activity = this;
        this.arrayListDemo = getIntent().getExtras().getStringArrayList("ARRAY");
        this.arrayListDemo2 = getIntent().getExtras().getStringArrayList("ARRAY2");
        this.u = (TextView) findViewById(R.id.title_txt);
        this.q = (TextView) findViewById(R.id.lic_status);
        this.r = (TextView) findViewById(R.id.name);
        this.o = (TextView) findViewById(R.id.issue_date);
        this.p = (TextView) findViewById(R.id.last_trans_at);
        this.l = (TextView) findViewById(R.id.dl_validity);
        this.v = (TextView) findViewById(R.id.tran_validity);
        this.j = (TextView) findViewById(R.id.cov_cat);
        this.w = (TextView) findViewById(R.id.vehicle_class);
        this.k = (TextView) findViewById(R.id.cov_issue_date);
        this.m = (TextView) findViewById(R.id.haz_valid);
        this.n = (TextView) findViewById(R.id.hill_valid);
        this.t = (Button) findViewById(R.id.share_details);
        this.s = (Button) findViewById(R.id.search_another);
        this.q.setText(this.arrayListDemo.get(0).toString());
        this.r.setText(this.arrayListDemo.get(1).toString());
        this.o.setText(this.arrayListDemo.get(2).toString());
        this.p.setText(this.arrayListDemo.get(3).toString());
        this.l.setText(this.arrayListDemo.get(4).toString());
        this.v.setText(this.arrayListDemo.get(5).toString());
        this.m.setText(this.arrayListDemo.get(6).toString());
        this.n.setText(this.arrayListDemo.get(7).toString());
        this.u.setText(this.arrayListDemo.get(8).toString());
        for (int i = 0; i < this.arrayListDemo2.size(); i++) {
            if (i == 0 || i == 3 || i == 6 || i == 9 || i == 12 || i == 15 || i == 18) {
                this.cov_cat_array.add(this.arrayListDemo2.get(i));
            } else if (i == 1 || i == 4 || i == 7 || i == 10 || i == 13 || i == 16) {
                this.vehicle_class_array.add(this.arrayListDemo2.get(i));
            } else {
                this.cov_issue_date_array.add(this.arrayListDemo2.get(i));
            }
        }
        String str = "";
        String str2 = "";
        for (int i2 = 0; i2 < this.cov_cat_array.size(); i2++) {
            str2 = str2 + this.cov_cat_array.get(i2) + ",";
        }
        this.j.setText(removeLastChar(str2));
        String str3 = "";
        for (int i3 = 0; i3 < this.vehicle_class_array.size(); i3++) {
            str3 = str3 + this.vehicle_class_array.get(i3) + ",";
        }
        this.w.setText(removeLastChar(str3));
        for (int i4 = 0; i4 < this.cov_issue_date_array.size(); i4++) {
            str = str + this.cov_issue_date_array.get(i4) + ",";
        }
        this.k.setText(removeLastChar(str));
        this.t.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                StringBuilder sb = new StringBuilder();
                sb.append("Current Status - ");
                sb.append(DLDetailsActivity.this.q.getText());
                sb.append("\n\nHolder's Name - ");
                sb.append(DLDetailsActivity.this.r.getText());
                sb.append("\n\nDate Of Issue: - ");
                sb.append(DLDetailsActivity.this.o.getText().toString());
                sb.append("\n\nLast Transaction At: - ");
                sb.append(DLDetailsActivity.this.p.getText().toString());
                sb.append("\n\nNon-Transport Validity- ");
                sb.append(DLDetailsActivity.this.l.getText().toString());
                sb.append("\n\nTransport Validity - ");
                sb.append(DLDetailsActivity.this.v.getText().toString());
                sb.append("\n\nCOV Category - ");
                sb.append(DLDetailsActivity.this.j.getText().toString());
                sb.append("\n\nClass Of Vehicle - ");
                sb.append(DLDetailsActivity.this.w.getText().toString());
                sb.append("\n\nCOV Issue Date - ");
                sb.append(DLDetailsActivity.this.k.getText().toString());
                sb.append("\n\nHazardous Valid Till: - ");
                sb.append(DLDetailsActivity.this.m.getText().toString());
                sb.append("\n\nHill Valid Till: - ");
                sb.append(DLDetailsActivity.this.n.getText().toString());
                sb.append("\nhttps://play.google.com/store/apps/details?id=" + DLDetailsActivity.this.getPackageName());
                int i5 = DLDetailsActivity.this.getApplicationInfo().labelRes;
                Intent intent = new Intent("android.intent.action.SEND");
                intent.addFlags(67108864);
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.SUBJECT", DLDetailsActivity.this.getApplicationContext().getString(i5));
                DLDetailsActivity.this.getPackageName();
                intent.putExtra("android.intent.extra.TEXT", sb.toString());
                DLDetailsActivity.this.startActivity(Intent.createChooser(intent, "Share:"));
            }
        });
        this.s.setOnClickListener(new View.OnClickListener() {
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                DLDetailsActivity.this.onBackPressed();
            }
        });
    }

    public String removeLastChar(String str) {
        return !TextUtils.isEmpty(str) ? str.substring(0, str.length() - 1) : str;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
