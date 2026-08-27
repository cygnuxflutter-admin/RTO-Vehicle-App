package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_FuelAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_FuelModel;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_Constant;

import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalReferenceEngine;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_NativeAdUtil;
import com.vehicle.information.trending.rtoexam.rto.MyApplication;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import com.onesignal.OneSignal;

public class Task_StartActivity extends AppCompatActivity implements GlobalReferenceEngine.Callback{

    private static final String TAG = "123";
    ArrayList<Task_FuelModel> FuelList;
    String cityId;
    String cityName;
    public OutputStream outputStream;
    TextView textView8, textView9;
    RecyclerView fuelRec;
    boolean doubleBackToExitPressedOnce = false;
    private Task_PreferenceClass taskPreferenceClass;
    RelativeLayout rl_collapsible,rl_banner_ad;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_start_activity);
        if (Build.VERSION.SDK_INT >= 23) {
            checkAndRequestPermissions();
        }
       // NativeLoad();
        //InterstitialLoad();

        taskPreferenceClass = new Task_PreferenceClass(this);
        rl_collapsible = findViewById(R.id.rl_collapsible);
        rl_banner_ad = findViewById(R.id.rl_banner_ad);
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);

       /* if (taskPreferenceClass.getInt("NativeAdShow") == 1) {
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
        } else {
            native_banner_ad_container.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
        }*/

        if (taskPreferenceClass.getInt("StartScreen_AD", 0) == 1) {
            rl_collapsible.setVisibility(View.VISIBLE);
            findViewById(R.id.ads).setVisibility(View.GONE);
            rl_banner_ad.setVisibility(View.GONE);
            Task_LoadAds.loadCollapsibleBanner(this,"top", findViewById(R.id.CollapsibleContainer), rl_collapsible,findViewById(R.id.shimmer_view_CollapsibleContainer));
        } else if (taskPreferenceClass.getInt("StartScreen_AD", 0) == 2) {
            rl_collapsible.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
            rl_banner_ad.setVisibility(View.VISIBLE);
            Task_LoadAds.loadAdmobBannerAd(this, findViewById(R.id.rl_banner_ad));
        } else if (taskPreferenceClass.getInt("StartScreen_AD", 0) == 3) {
            rl_collapsible.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.VISIBLE);
            rl_banner_ad.setVisibility(View.GONE);
            Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
        } else {
            rl_collapsible.setVisibility(View.GONE);
            findViewById(R.id.ads).setVisibility(View.GONE);
            rl_banner_ad.setVisibility(View.GONE);
        }


        this.FuelList = new ArrayList<>();
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        fuelRec = findViewById(R.id.fuel_rec);
        SharedPreferences sharedPreferences = getSharedPreferences(Task_Constant.MY_PREFS_NAME, 0);
        this.cityName = sharedPreferences.getString("cityName", "Kolkata");
        this.cityId = sharedPreferences.getString("cityId", "4");
        textView8.setText(this.cityName);
        new GetData().execute(new String[0]);
        this.textView9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MyApplication.showInterstitialAd(Task_StartActivity.this, () -> Next_FuelCityActivity() );

              //  startActivity(new Intent(Task_StartActivity.this, Task_FuelCityActivity.class));
              //  ShowFunUAds();
            }
        });

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(Task_StartActivity.this);
        OneSignal.setAppId("70979d73-f8a7-4936-b329-b14e18bb16d3");
        OneSignal.promptForPushNotifications();

    }

    private void Next_FuelCityActivity() {
        startActivity(new Intent(Task_StartActivity.this, Task_FuelCityActivity.class));
    }

    @Override
    public void onConfigLoaded() {

    }

    private class GetData extends AsyncTask<String, String, String> {
        StringBuilder gold22kp;
        StringBuilder gold24kp;

        private GetData() {
            this.gold24kp = new StringBuilder();
            this.gold22kp = new StringBuilder();
        }


        public String doInBackground(String[] strArr) {
            try {
                Elements select = Jsoup.connect("https://www.mypetrolprice.com/" + Task_StartActivity.this.cityId + "/Fuel-prices-in-Kolkata").get().select("div.OuterDiv");
                int size = select.size();
                for (int i = 0; i < size; i++) {
                    Task_StartActivity.this.FuelList.add(new Task_FuelModel(select.eq(i).select("div.UCFuelName").text(), select.eq(i).select("div.Italic").text(), select.eq(i).select("span.day").text(), select.eq(i).select("span.month").text(), select.eq(i).select("span.year").text(), select.eq(i).select("div.fnt27").text(), select.eq(i).select("div.fnt18").text(), select.eq(i).select("div.UCFuelName").text()));
                }
                return null;
            } catch (IOException unused) {
                return null;
            }
        }


        public void onPostExecute(String str) {
            super.onPostExecute(str);
            fuelRec.setLayoutManager(new LinearLayoutManager(Task_StartActivity.this, RecyclerView.HORIZONTAL, false));
            RecyclerView recyclerView = fuelRec;
            Task_StartActivity getTaskStartActivity = Task_StartActivity.this;
            recyclerView.setAdapter(new Task_FuelAdapter(getTaskStartActivity, getTaskStartActivity.FuelList));
            fuelRec.setVisibility(View.VISIBLE);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.privacy:
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getString(R.string.privacy))));
                return;
            case R.id.rate :
                StoreLink();
                return;
            case R.id.share:
                ShareApp();
                return;
            case R.id.start:
                MyApplication.showInterstitialAd(Task_StartActivity.this, () -> startActivity(new Intent(Task_StartActivity.this, Task_MainActivity.class)));
                return;
            default:
                return;
        }
    }

    private boolean checkAndRequestPermissions() {
        int checkSelfPermission = ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE");
        int checkSelfPermission2 = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        ArrayList arrayList = new ArrayList();
        if (checkSelfPermission2 != 0) {
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (checkSelfPermission != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        ActivityCompat.requestPermissions(this, (String[]) arrayList.toArray(new String[arrayList.size()]), 1);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i != 6 || iArr[0] == 0 || Build.VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            return;
        }
        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 6);
    }

    public void StoreLink() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        } catch (ActivityNotFoundException unused) {
            Toast.makeText(this, "You don't have Google Play installed", Toast.LENGTH_LONG).show();
        }
    }

    private void ShareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(intent, "Share Link"));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "double tap to exit!", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);

    }

    @Override
    public void onResume() {
        super.onResume();
        GlobalReferenceEngine.setCallback(Task_StartActivity.this);
        GlobalReferenceEngine.updateConfig();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
