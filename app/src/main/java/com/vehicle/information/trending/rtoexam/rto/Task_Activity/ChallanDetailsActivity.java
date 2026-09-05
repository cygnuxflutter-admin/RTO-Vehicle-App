package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.ChallanDetailsRecyclerViewAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ChallanDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ChallanDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalTracker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;

import java.util.List;


public class ChallanDetailsActivity extends AllBaseActivity {
    public String actionName;
    private CardView btnCheckVehicleDetails;
    private LinearLayout contentLayout;
    private CardView cvVehicleDetails;
    private String dataFetchStatus;
    private String dataFetchStatusMessage;
    private View emptyLayout;
    private RecyclerView recyclerView;
    public String registrationNo;
    private ChallanDetailsResponse response;
    private String searchType;
    private TextView txvChallanDisclaimer;
    private TextView txvErrorHeader;
    private TextView txvErrorMessage;
    private Task_PreferenceClass taskPreferenceClass;
    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_challan_details);

        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.searchType = getIntent().getStringExtra("SEARCH_TYPE");
        this.actionName = getIntent().getStringExtra("ACTION");
        this.dataFetchStatus = getIntent().getStringExtra("data_fetch_status");
        this.dataFetchStatusMessage = getIntent().getStringExtra("data_fetch_status_message");
        this.response = (ChallanDetailsResponse) getIntent().getSerializableExtra("CHALLAN_DETAILS_DATA");

        Task_PreferenceClass taskPreferenceClass = new Task_PreferenceClass(this);
        RelativeLayout rl_ad = this.findViewById(R.id.rl_ad);
        if (Task_NetworkUtils.isNetworkAvailable(this)) {
            if (taskPreferenceClass.getInt("BannerAdShow") == 1){
            Task_LoadAds.loadAdmobBannerAd(this, rl_ad);
            } else {
                rl_ad.setVisibility(View.GONE);
            findViewById(R.id.rlBanner).setVisibility(View.GONE);
            }
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ((TextView) toolbar.findViewById(R.id.action_bar_title)).setText(!Utils.isNullOrEmpty(this.registrationNo) ? this.registrationNo : "");
        this.emptyLayout = findViewById(R.id.emptyLayout);
        this.contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        this.txvChallanDisclaimer = (TextView) findViewById(R.id.txvChallanDisclaimer);
        this.txvErrorHeader = (TextView) findViewById(R.id.oopsTv);
        this.txvErrorMessage = (TextView) findViewById(R.id.noResultTv);
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.cvVehicleDetails = (CardView) findViewById(R.id.cvVehicleDetails);
        this.btnCheckVehicleDetails = (CardView) findViewById(R.id.btnCheckVehicleDetails);
        managePageElements();
    }

    private void managePageElements() {
        if (Utils.isNullOrEmpty(this.dataFetchStatus)) {
            startSearchResultLoaderActivity();
        } else if (this.dataFetchStatus.equalsIgnoreCase("no_internet")) {
            showOrHideElements(false, false, getString(R.string.app_internet_msg));
        } else if (this.dataFetchStatus.equalsIgnoreCase("no_data_available")) {
            showOrHideElements(true, false, this.dataFetchStatusMessage);
            this.txvErrorHeader.setVisibility(View.GONE);
            this.txvChallanDisclaimer.setVisibility(View.VISIBLE);
        } else if (this.dataFetchStatus.equalsIgnoreCase("error")) {
            showOrHideElements(true, false, this.dataFetchStatusMessage);
        } else {
            ChallanDetailsResponse challanDetailsResponse = this.response;
            if (challanDetailsResponse == null || challanDetailsResponse.getDetails() == null || this.response.getDetails().isEmpty()) {
                showOrHideElements(true, false, this.dataFetchStatusMessage);
                return;
            }
            handleResponse(this.response.getDetails());
            showOrHideElements(true, true, "");
        }
    }

    private void startSearchResultLoaderActivity() {
        this.dataFetchStatus = "";
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, getString(R.string.app_internet_msg));
            return;
        }
        Intent intent = new Intent(this, SearchResultLoaderActivity.class);
        intent.putExtra("REGISTRATION_NO", this.registrationNo);
        intent.putExtra("SEARCH_TYPE", this.searchType);
        intent.putExtra("ACTION", this.actionName);
        startActivity(intent);
        finish();
    }

    private void handleResponse(List<ChallanDetails> list) {
        if (list == null) {
            showOrHideElements(true, false, getString(R.string.error_message));
            return;
        }
        showOrHideCheckVehicleDetailsUI();
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        this.recyclerView.setNestedScrollingEnabled(false);
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(new ChallanDetailsRecyclerViewAdapter(this, list));
        showOrHideElements(true, true, "");
    }

    private void showOrHideCheckVehicleDetailsUI() {
        if (Utils.isNullOrEmpty(this.searchType) || !this.searchType.equalsIgnoreCase("RC")) {
            this.cvVehicleDetails.setVisibility(View.GONE);
            return;
        }
        this.cvVehicleDetails.setVisibility(View.VISIBLE);
        this.btnCheckVehicleDetails.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.ChallanDetailsActivity.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (Utils.isNullOrEmpty(ChallanDetailsActivity.this.actionName) || !ChallanDetailsActivity.this.actionName.equalsIgnoreCase("SAVE")) {
                    Intent intent = new Intent(ChallanDetailsActivity.this, SearchVehicleDetailsLoaderActivity.class);
                    intent.putExtra("REGISTRATION_NO", ChallanDetailsActivity.this.registrationNo);
                    intent.putExtra("ACTION", "SAVE");
                    ChallanDetailsActivity.this.startActivity(intent);
                    return;
                }
                ChallanDetailsActivity.this.onBackPressed();
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void showOrHideElements(boolean z, boolean z2, String str) {
        int i = 0;
        if (this.txvErrorHeader.getVisibility() == View.GONE) {
            this.txvErrorHeader.setVisibility(View.VISIBLE);
        }
        if (str != null && !str.isEmpty()) {
            this.txvErrorMessage.setText(str);
        }
        this.emptyLayout.setVisibility((!z || !z2) ? View.VISIBLE : View.GONE);
        LinearLayout linearLayout = this.contentLayout;
        if (!z || !z2) {
            i = 8;
        }
        linearLayout.setVisibility(i);
        TextView textView = this.txvChallanDisclaimer;
        if (!z || z2) {
        }
        textView.setVisibility(View.GONE);
    }

    public void shareTo3rdPartyApps() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", getString(R.string.share_challan_detail));
        startActivity(Intent.createChooser(intent, "Share with"));
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

//    @Override // android.app.Activity
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.share_details, menu);
//        return true;
//    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        } else if (itemId != R.id.action_share) {
            super.onOptionsItemSelected(menuItem);
            return true;
        } else {
            GlobalTracker.from(this).sendSelectButtonEvent(GlobalTracker.BUTTON_SHARE_CHALLAN_DETAILS);
            shareTo3rdPartyApps();
            return true;
        }
    }
}
