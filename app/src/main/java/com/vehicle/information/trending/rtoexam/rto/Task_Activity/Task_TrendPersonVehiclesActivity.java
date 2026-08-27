package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_ConstantsCele;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_TrendPersonModel;
import com.facebook.ads.InterstitialAd;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_RtoUtil;
import com.vehicle.information.trending.rtoexam.rto.MyApplication;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalTracker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task_TrendPersonVehiclesActivity extends AppCompatActivity {

    Celebrity_Adapter adapter;
    private InterstitialAd interstitialAd;
    private List<Task_TrendPersonModel> personList;
    private String personType;
    RecyclerView recycler_view;
    private Task_PreferenceClass taskPreferenceClass;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.task_activity_tp_vehicle);
       //AdsManager.getInstance().loadBanner(this);

        taskPreferenceClass = new Task_PreferenceClass(this);
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
                Task_TrendPersonVehiclesActivity.this.onBackPressed();
            }
        });
        this.recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        this.personType = getIntent().getStringExtra("PERSON_TYPE");
        ArrayList arrayList = new ArrayList();
        this.personList = arrayList;
        List<Task_TrendPersonModel> list = Task_ConstantsCele.trendingPersonsVehicles.get(this.personType);
        Objects.requireNonNull(list);
        arrayList.addAll(list);
        if (this.personType.equals("ACTORS")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_actors);
        } else if (this.personType.equals("ACTRESSES")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_actress);
        } else if (this.personType.equals("DANCERS")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_dancers);
        } else if (this.personType.equals("SINGERS")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_singers);
        } else if (this.personType.equals("SPORTS_PERSONS")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_sport_person);
        } else if (this.personType.equals("TYCOONS")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_mr_perfect);
        } else if (this.personType.equals("POLITICIANS")) {
            this.adapter = new Celebrity_Adapter(this, this.personList, Task_ConstantsCele.img_politician);
        }
        this.recycler_view.setLayoutManager(new LinearLayoutManager(this));
        this.recycler_view.setHasFixedSize(true);
        this.recycler_view.setAdapter(this.adapter);
    }


    @Override
    public void onDestroy() {
        InterstitialAd interstitialAd = this.interstitialAd;
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }




    public class Celebrity_Adapter extends RecyclerView.Adapter<Celebrity_Adapter.ViewHolder> {
        Activity activity;
        int[] img_celebrity;
        private List<Task_TrendPersonModel> personListt;

        public Celebrity_Adapter(Activity activity, List<Task_TrendPersonModel> list, int[] iArr) {
            this.activity = activity;
            this.personListt = list;
            this.img_celebrity = iArr;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_row_search_vehicles_history_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.txvName.setText(this.personListt.get(i).getPersonName());
            viewHolder.txvRegNo.setText(this.personListt.get(i).getRegistrationNo());
            viewHolder.iv_celebrity.setImageResource(this.img_celebrity[i]);
        }

        @Override
        public int getItemCount() {
            return this.personListt.size();
        }


        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView iv_celebrity;
            TextView txvName;
            TextView txvRegNo;

            public ViewHolder(View view) {
                super(view);
                view.setOnClickListener(this);
                this.txvRegNo = (TextView) view.findViewById(R.id.txvRegNo);
                this.txvName = (TextView) view.findViewById(R.id.txvName);
                this.iv_celebrity = (ImageView) view.findViewById(R.id.iv_celebrity);
            }

            @Override
            public void onClick(View view) {
                btnSearchVehicleDetailsClickListener("" + this.txvRegNo.getText().toString());
            }

            public void btnSearchVehicleDetailsClickListener(String str) {
                InputMethodManager inputMethodManager = (InputMethodManager) Task_TrendPersonVehiclesActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (!Task_TrendPersonVehiclesActivity.this.isNetworkConnected()) {
                    Toast.makeText(Task_TrendPersonVehiclesActivity.this, "You are not connected to internet!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String formatString = Task_RtoUtil.formatString(str);
                if (Task_RtoUtil.isNullOrEmpty(formatString) || formatString.length() <= 6) {
                    Toast.makeText(Task_TrendPersonVehiclesActivity.this, "Please enter the correct vehicle no!", Toast.LENGTH_SHORT).show();
                    return;
                }
               /* Intent intent = new Intent(Task_TrendPersonVehiclesActivity.this, SearchVehicleDetailsLoaderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(GlobalTracker.EVENT_VEHICLE_NO, formatString);
                bundle.putString("content_type", GlobalTracker.BUTTON);
                intent.putExtra("REGISTRATION_NO", formatString);
                intent.putExtra("ACTION", "SAVE");
                intent.putExtra("TYPE", "RC");*/
                Bundle bundle = new Bundle();
                bundle.putString("VEHICLE_NO", formatString);
                bundle.putString("content_type", "BUTTON");
                Intent intent = new Intent(Task_TrendPersonVehiclesActivity.this, Task_SearchVehicleDetailActivity.class);
                intent.putExtra("REGISTRATION_NO", formatString);
                intent.putExtra("ACTION", "SAVE");
                MyApplication.showInterstitialAd(Task_TrendPersonVehiclesActivity.this, () ->  Task_TrendPersonVehiclesActivity.this.startActivity(intent));
            }
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
