package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.Task_SymbolAdpter;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;

public class Task_SymbolDetailActivity extends AppCompatActivity {

    Task_SymbolAdpter rtoSymbolDet_rcyAdp;
    RecyclerView rto_list_recycler;
    String str_pass_value;

    int[] cautionary_img_ary = {R.drawable.caution_1, R.drawable.caution_2, R.drawable.caution_3, R.drawable.caution_4, R.drawable.caution_5, R.drawable.caution_6, R.drawable.caution_7, R.drawable.caution_8, R.drawable.caution_9, R.drawable.caution_10, R.drawable.caution_11, R.drawable.caution_12, R.drawable.caution_13, R.drawable.caution_14, R.drawable.caution_15, R.drawable.caution_16, R.drawable.caution_17, R.drawable.caution_18, R.drawable.caution_19, R.drawable.caution_20, R.drawable.caution_21};
    String[] cautionary_text_ary = {"Right Hair Pin Bend", "Left Hair Pin Bend", "Right Hand Curve", "Left Hand Curve", "Left Reverse Bend", "Right Reverse Bend", "Side Road Left", "Side Road Right", "T-intersection", "Major Road Ahead", "Staggered Intersection", "Staggered Intersection", "Cross Road Ahead", "Right Y-Intersection", "Left Y-Intersection", "Two Way Traffic", "Y-Intersection", "Roundabout Ahead", "Gap In Median", "Pedestrian Crossing", "Narrow Bridge Ahead"};

    int[] drivingrules_img_ary = {R.drawable.ic_drive_1, R.drawable.ic_drive_2, R.drawable.ic_drive_3, R.drawable.ic_drive_4, R.drawable.ic_drive_5};
    String[] drivingrules_text_ary = {"I intend to move in to the left or turn left.", "I intend to move out of the right or changing the lane or turn right.", "I intend to stop.", "I intend to slow down.", "Indicating the car following you to over take."};

    int[] informatory_img_ary = {R.drawable.ic_informatory_1, R.drawable.ic_informatory_2, R.drawable.ic_informatory_3, R.drawable.ic_informatory_4, R.drawable.ic_informatory_5, R.drawable.ic_informatory_6, R.drawable.ic_informatory_7, R.drawable.ic_informatory_8, R.drawable.ic_informatory_9, R.drawable.ic_informatory_10, R.drawable.ic_informatory_11, R.drawable.ic_informatory_12, R.drawable.ic_informatory_13, R.drawable.ic_informatory_14};
    String[] informatory_text_ary = {"Public telephone", "Petrol pump", "Hospital", "Eating Place", "Light Refreshment", "No Through Road", "No Through Side Road", "First Aid Post", "Park This Side", "Parking Both Sides", "Parking Lot Bikes", "Parking Lot Cycles", "Parking Lot Taxis", "Parking Lot Auto"};

    int[] mandatory_img_ary = {R.drawable.ic_mandtory_1, R.drawable.ic_mandtory_2, R.drawable.ic_mandtory_3, R.drawable.ic_mandtory_4, R.drawable.ic_mandtory_5, R.drawable.ic_mandtory_6, R.drawable.ic_mandtory_7, R.drawable.ic_mandtory_8, R.drawable.ic_mandtory_9, R.drawable.ic_mandtory_10, R.drawable.ic_mandtory_11, R.drawable.ic_mandtory_12, R.drawable.ic_mandtory_13, R.drawable.ic_mandtory_14, R.drawable.ic_mandtory_15, R.drawable.ic_mandtory_16, R.drawable.ic_mandtory_17, R.drawable.ic_mandtory_18, R.drawable.ic_mandtory_19, R.drawable.ic_mandtory_20, R.drawable.ic_mandtory_21, R.drawable.ic_mandtory_22, R.drawable.ic_mandtory_23, R.drawable.ic_mandtory_24, R.drawable.ic_mandtory_25};
    String[] mandatory_text_ary = {"Speed Limit", "No Entry", "One Way Sign, Entry allowed", "Right Turn Prohibited", "Left Turn Prohibited", "One Way, Entry Prohibited", "Load Limit", "Vehicle Prohibited in Both Directions", "Horn Prohibited", "U-Turn Prohibited", "Overtaking Prohibited", "No Parking", "Width Limit", "Height Limit", "No Stopping or Standing", "Restriction Ends", "Stop", "Compulsory Turn Right", "Compulsory Turn Left", "Compulsory Ahead Only", "Compulsory Keep Left", "Compulsory Ahead or Turn Left", "Compulsory Ahead or Turn Right", "Compulsory Sound Horn", "Give Way"};

    int[] roadsignals_img_ary = {R.drawable.ic_road_1, R.drawable.ic_road_2, R.drawable.ic_road_3, R.drawable.ic_road_4, R.drawable.ic_road_5, R.drawable.ic_road_6, R.drawable.ic_road_7, R.drawable.ic_road_8, R.drawable.ic_road_9, R.drawable.ic_road_10, R.drawable.ic_road_11, R.drawable.ic_road_12};
    String[] roadsignals_text_ary = {"Centre Line Marking For A Two Lane Road", "Lane Line And Broken Centre Line", "Centre Barrier Line Marking For A Four Lane Road", "Centre Barrier Line Marking For A Six Lane Road", "Double White/Yellow Lines:\nUsed where visibility is restricted in both directions.", "Combination Of Solid And Broken Lines:\nIf the line on your side is broken, you may cross or straddle it. OverTake - but only if it is safe to do so.\nIf the line on your side is continious you must not cross or straddle it.", "Stop Line:\nA stop line is a single solid transverse line painted before the intersecting edge of the road junction/ intersection.", "Give Way Line:\nThe give way line is usually a double dotted line marked transversely at junctions.", "Border or Edge Lines:\nThese are continuous lines at the edge of the carriageway and mark the limits of the main carriageway upto which a driver can safely venture.", "Parking Prohibited Lines:\nA solid continuous yellow line painted on the kerb or edge of the carriageway along with a \"No-parking\" sign indicates the extent of no-parking area.", "Yellow Box Junctions or Keep Clear:\nThese are yellow crossed diagonal lines within the box. The vehicles should cross it only if they have a clear space available ahead of the yellow box. In this marked area vehicles must not stop even briefly.", "Pedestrian Crossings\nThese are alternate black and white stripes painted parallel to the road generally known as zebra crossing. Pedestrians must cross only at the point where these lines are provided and when the signal is in their favour at controlled crossings. You must stop and give way to pedestrians at these crossings."};

    int[] trafficpolicesignals_img_ary = {R.drawable.traffic_police_1, R.drawable.traffic_police_2, R.drawable.traffic_police_3, R.drawable.traffic_police_4, R.drawable.traffic_police_5, R.drawable.traffic_police_6, R.drawable.traffic_police_7, R.drawable.traffic_police_8, R.drawable.traffic_police_9};
    String[] trafficpolicesignals_text_ary = {"To stop vehicles approaching simultaneously from front and behind", "To allow vehicles coming from right and turning right by stopping traffic approaching from the left", "To beckon the vehicles approaching from right", "To beckon the vehicles approaching from left", "To stop vehicles approaching from left and waiting to turn right", "To stop vehicles coming from front", "To stop vehicles approaching from behind", "To stop vehicles approaching from right to allow vehicles from the left to turn right", "Warning signal closing all vehicles"};

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setStatusBarColor(Color.parseColor("#1E40AF"));
        setContentView(R.layout.task_activity_symbolic_detail);

        RelativeLayout rl_ad = findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
            if (taskPreferenceClass.getInt("BannerAdShow") == 1) {
                Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                if (rl_ad != null) rl_ad.setVisibility(View.GONE);
                View rlBanner = findViewById(R.id.rlBanner);
                if (rlBanner != null) rlBanner.setVisibility(View.GONE);
            }
        }

        ImageView ivBack = findViewById(R.id.iv_back);
        if (ivBack != null) {
            ivBack.setOnClickListener(view -> onBackPressed());
        }

        this.str_pass_value = getIntent().getStringExtra("passvalue");
        if (this.str_pass_value == null) {
            this.str_pass_value = "Mandatory";
        }

        TextView tvHeaderTitle = findViewById(R.id.tv_header_title);
        if (tvHeaderTitle != null) {
            tvHeaderTitle.setText(this.str_pass_value);
        }

        if (this.str_pass_value.equals("Mandatory")) {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.mandatory_text_ary, this.mandatory_img_ary);
        } else if (this.str_pass_value.equals("Cautionary")) {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.cautionary_text_ary, this.cautionary_img_ary);
        } else if (this.str_pass_value.equals("Informatory")) {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.informatory_text_ary, this.informatory_img_ary);
        } else if (this.str_pass_value.equals("Road & Signals")) {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.roadsignals_text_ary, this.roadsignals_img_ary);
        } else if (this.str_pass_value.equals("Driving Rules")) {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.drivingrules_text_ary, this.drivingrules_img_ary);
        } else if (this.str_pass_value.equals("Traffic Police Signals")) {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.trafficpolicesignals_text_ary, this.trafficpolicesignals_img_ary);
        } else {
            this.rtoSymbolDet_rcyAdp = new Task_SymbolAdpter(this.mandatory_text_ary, this.mandatory_img_ary);
        }

        this.rto_list_recycler = findViewById(R.id.rto_list_recycler);
        this.rto_list_recycler.setHasFixedSize(true);
        this.rto_list_recycler.setLayoutManager(new LinearLayoutManager(this));
        this.rto_list_recycler.setItemAnimator(new DefaultItemAnimator());
        this.rto_list_recycler.setAdapter(this.rtoSymbolDet_rcyAdp);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}