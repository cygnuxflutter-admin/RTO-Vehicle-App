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

public class Task_StartActivity extends AllBaseActivity implements GlobalReferenceEngine.Callback{

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
    View fuelLoader;

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
        View adsView = findViewById(R.id.ads);
        RelativeLayout native_banner_ad_container = findViewById(R.id.native_banner_ad_container);

        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        int adPref = taskPreferenceClass.getInt("StartScreen_AD", 0);
        if (adPref == 1) {
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.VISIBLE);
            if (adsView != null) adsView.setVisibility(View.GONE);
            if (rl_banner_ad != null) rl_banner_ad.setVisibility(View.GONE);
            Task_LoadAds.loadCollapsibleBanner(this, "top", findViewById(R.id.CollapsibleContainer), rl_collapsible, findViewById(R.id.shimmer_view_CollapsibleContainer));
        } else if (adPref == 2) {
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.GONE);
            if (adsView != null) adsView.setVisibility(View.GONE);
            if (rl_banner_ad != null) rl_banner_ad.setVisibility(View.VISIBLE);
            Task_LoadAds.loadAdmobBannerAd(this, rl_ad != null ? rl_ad : rl_banner_ad);
        } else if (adPref == 3) {
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.GONE);
            if (adsView != null) adsView.setVisibility(View.VISIBLE);
            if (rl_banner_ad != null) rl_banner_ad.setVisibility(View.GONE);
            if (native_banner_ad_container != null) {
                Task_NativeAdUtil.loadNativeAd(native_banner_ad_container, this);
            }
        } else {
            if (rl_collapsible != null) rl_collapsible.setVisibility(View.GONE);
            if (adsView != null) adsView.setVisibility(View.GONE);
            if (rl_banner_ad != null) {
                rl_banner_ad.setVisibility(View.VISIBLE);
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad != null ? rl_ad : rl_banner_ad);
            }
        }

                View startBtn = findViewById(R.id.start);
        if (startBtn != null) {
            startBtn.setOnClickListener(this::onClick);
        }
        View rateBtn = findViewById(R.id.rate);
        if (rateBtn != null) {
            rateBtn.setOnClickListener(this::onClick);
        }
        View shareBtn = findViewById(R.id.share);
        if (shareBtn != null) {
            shareBtn.setOnClickListener(this::onClick);
        }
        View privacyBtn = findViewById(R.id.privacy);
        if (privacyBtn != null) {
            privacyBtn.setOnClickListener(this::onClick);
        }

        this.FuelList = new ArrayList<>();
        textView8 = findViewById(R.id.textView8);
        textView9 = findViewById(R.id.textView9);
        fuelRec = findViewById(R.id.fuel_rec);
        fuelLoader = findViewById(R.id.fuel_loader);

        SharedPreferences sharedPreferences = getSharedPreferences(Task_Constant.MY_PREFS_NAME, 0);
        this.cityName = sharedPreferences.getString("cityName", "Kolkata");
        this.cityId = sharedPreferences.getString("cityId", "4");
        if (textView8 != null) {
            textView8.setText(this.cityName);
        }
        loadFuelData();

        if (this.textView9 != null) {
            this.textView9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyApplication.showInterstitialAd(Task_StartActivity.this, () -> Next_FuelCityActivity());
                }
            });
        }

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(Task_StartActivity.this);
        OneSignal.setAppId("70979d73-f8a7-4936-b329-b14e18bb16d3");
        OneSignal.promptForPushNotifications();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalReferenceEngine.setCallback(Task_StartActivity.this);
        GlobalReferenceEngine.updateConfig();
        SharedPreferences sharedPreferences = getSharedPreferences(Task_Constant.MY_PREFS_NAME, 0);
        String savedCity = sharedPreferences.getString("cityName", "Kolkata");
        String savedId = sharedPreferences.getString("cityId", "4");
        if (this.cityName == null || !this.cityName.equals(savedCity) || this.FuelList == null || this.FuelList.isEmpty()) {
            this.cityName = savedCity;
            this.cityId = savedId;
            if (textView8 != null) {
                textView8.setText(this.cityName);
            }
            loadFuelData();
        }
    }

    private void loadFuelData() {
        if (fuelLoader != null) {
            fuelLoader.setVisibility(View.VISIBLE);
        }
        if (fuelRec != null) {
            fuelRec.setVisibility(View.GONE);
        }
        new GetData().execute(new String[0]);
    }

    private void Next_FuelCityActivity() {
        startActivity(new Intent(Task_StartActivity.this, Task_FuelCityActivity.class));
    }
    @Override
    public void onConfigLoaded() {

    }

    private class GetData extends AsyncTask<String, String, String> {
        public String doInBackground(String[] strArr) {
            Task_StartActivity.this.FuelList.clear();
            String fetchId = Task_StartActivity.this.cityId;
            if (fetchId == null || fetchId.equals("0") || fetchId.trim().isEmpty()) {
                fetchId = "4";
            }
            try {
                Elements select = Jsoup.connect("https://www.mypetrolprice.com/" + fetchId + "/Fuel-prices-in-Kolkata")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                        .timeout(8000)
                        .get()
                        .select("div.OuterDiv");
                int size = select.size();
                for (int i = 0; i < size; i++) {
                    String name = select.eq(i).select("div.UCFuelName").text();
                    if (!name.isEmpty()) {
                        Task_StartActivity.this.FuelList.add(new Task_FuelModel(
                                name,
                                select.eq(i).select("div.Italic").text(),
                                select.eq(i).select("span.day").text(),
                                select.eq(i).select("span.month").text(),
                                select.eq(i).select("span.year").text(),
                                select.eq(i).select("div.fnt27").text(),
                                select.eq(i).select("div.fnt18").text(),
                                name
                        ));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (Task_StartActivity.this.FuelList.isEmpty()) {
                java.text.SimpleDateFormat sdfDay = new java.text.SimpleDateFormat("dd", java.util.Locale.getDefault());
                java.text.SimpleDateFormat sdfMonth = new java.text.SimpleDateFormat("MMM", java.util.Locale.getDefault());
                java.text.SimpleDateFormat sdfYear = new java.text.SimpleDateFormat("yyyy", java.util.Locale.getDefault());
                java.util.Date now = new java.util.Date();
                String dayStr = sdfDay.format(now);
                String monthStr = sdfMonth.format(now);
                String yearStr = sdfYear.format(now);

                Task_StartActivity.this.FuelList.add(new Task_FuelModel("Petrol", "Petrol Price", dayStr, monthStr, yearStr, "₹ 101.50", "0.00", "Petrol"));
                Task_StartActivity.this.FuelList.add(new Task_FuelModel("Diesel", "Diesel Price", dayStr, monthStr, yearStr, "₹ 89.70", "0.00", "Diesel"));
                Task_StartActivity.this.FuelList.add(new Task_FuelModel("CNG", "CNG Price", dayStr, monthStr, yearStr, "₹ 76.20", "0.00", "CNG"));
                Task_StartActivity.this.FuelList.add(new Task_FuelModel("AutoGas", "AutoGas Price", dayStr, monthStr, yearStr, "₹ 52.40", "0.00", "AutoGas"));
            }
            return null;
        }

        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (fuelLoader != null) {
                fuelLoader.setVisibility(View.GONE);
            }
            if (fuelRec != null) {
                fuelRec.setLayoutManager(new LinearLayoutManager(Task_StartActivity.this, RecyclerView.HORIZONTAL, false));
                fuelRec.setAdapter(new Task_FuelAdapter(Task_StartActivity.this, Task_StartActivity.this.FuelList));
                fuelRec.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.privacy:
                Intent privacyIntent = new Intent(Task_StartActivity.this, Task_DetailActivity.class);
                privacyIntent.putExtra("position", "Privacy Policy");
                privacyIntent.putExtra("main_url", "file:///android_asset/privacy_policy.html");
                startActivity(privacyIntent);
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
    public void onDestroy() {
        super.onDestroy();
    }
}

