package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import static com.vehicle.information.trending.rtoexam.rto.Task_Activity.SearchVehicleActivity.showToast;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

//import com.vehicle.information.trending.rtoexam.rto.AdAdmob;
//import com.vehicle.information.trending.rtoexam.rto.R;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.datamodels.VehicleDetails;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.datamodels.VehicleDetailsResponse;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.helpers.GlobalTracker;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.helpers.ToastHelper;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.utils.Utils;
import com.squareup.picasso.Picasso;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_adManager.Task_LoadAds;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalTracker;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NetworkUtils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_PreferenceClass;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;


public class FinanceDetailsActivity extends AppCompatActivity {
    private String actionName;
    private Button btnAction;
    private LinearLayout contentLayout;
    private LinearLayout cvFinancier;
    private LinearLayout cvOwnership;
    private String dataFetchStatus;
    private String dataFetchStatusMessage;
    private View errorContainer;
    private ImageView errorImage;
    private String registrationNo;
    private VehicleDetailsResponse response;
    private FrameLayout tvValuateCon;
    private TextView txvFinancierValue;
    private TextView txvOwnerNameValue;
    private TextView txvOwnershipValue;
    private TextView txvRegistrationNoValue;
    private TextView txvSubTitle;
    private TextView txvTitle;
    public String type;

    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_finance_details);


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

        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.actionName = getIntent().getStringExtra("ACTION");
        this.type = getIntent().getStringExtra("TYPE");
        this.dataFetchStatus = getIntent().getStringExtra("data_fetch_status");
        this.dataFetchStatusMessage = getIntent().getStringExtra("data_fetch_status_message");
        this.response = (VehicleDetailsResponse) getIntent().getSerializableExtra("VEHICLE_DETAILS_DATA");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        ((TextView) toolbar.findViewById(R.id.action_bar_title)).setText(!Utils.isNullOrEmpty(this.registrationNo) ? this.registrationNo : "");
        this.errorContainer = findViewById(R.id.errorContainer);
        this.contentLayout = (LinearLayout) findViewById(R.id.contentLayout);
        this.errorImage = (ImageView) findViewById(R.id.errorImageView);
        this.txvTitle = (TextView) findViewById(R.id.titleTextView);
        this.txvSubTitle = (TextView) findViewById(R.id.subtitleTextView);
        this.btnAction = (Button) findViewById(R.id.actionButton);
        this.cvOwnership = (LinearLayout) findViewById(R.id.cvOwnership);
        this.cvFinancier = (LinearLayout) findViewById(R.id.cvFinancier);
        this.txvOwnerNameValue = (TextView) findViewById(R.id.ownerNameValue);
        this.txvOwnershipValue = (TextView) findViewById(R.id.ownershipValue);
        this.txvRegistrationNoValue = (TextView) findViewById(R.id.registrationNoValue);
        this.txvFinancierValue = (TextView) findViewById(R.id.financierValue);
        this.tvValuateCon = (FrameLayout) findViewById(R.id.tvValuateCon);
        managePageElements();
    }

    private void managePageElements() {
        if (Utils.isNullOrEmpty(this.dataFetchStatus)) {
            startSearchVehicleDetailsLoaderActivity(this.type);
        } else if (this.dataFetchStatus.equalsIgnoreCase("no_internet")) {
            showOrHideElements(false, false, getString(R.string.app_internet_msg));
            this.errorImage.setImageResource(R.drawable.wifi);
            this.txvTitle.setText(getString(R.string.txt_connection_error_title));
            this.txvSubTitle.setText(getString(R.string.no_network_message));
            this.btnAction.setText(getString(R.string.btn_retry));
            this.btnAction.setVisibility(View.VISIBLE);
            this.btnAction.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.FinanceDetailsActivity.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FinanceDetailsActivity financeDetailsActivity = FinanceDetailsActivity.this;
                    financeDetailsActivity.startSearchVehicleDetailsLoaderActivity(financeDetailsActivity.type);
                }
            });
        } else if (this.dataFetchStatus.equalsIgnoreCase("no_data_available")) {
            showOrHideElements(true, false, this.dataFetchStatusMessage);
            this.errorImage.setImageResource(R.drawable.empty_folder);
            this.txvTitle.setText(getString(R.string.oops));
            this.txvSubTitle.setText(getString(R.string.no_result_found_finance_info));
            this.btnAction.setText(getString(R.string.btn_go_back_search_again));
            this.btnAction.setVisibility(View.VISIBLE);
            this.btnAction.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.FinanceDetailsActivity.2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FinanceDetailsActivity.this.onBackPressed();
                }
            });
        } else if (this.dataFetchStatus.equalsIgnoreCase("error")) {
            showOrHideElements(true, false, this.dataFetchStatusMessage);
            if (Utils.isNullOrEmpty(this.dataFetchStatusMessage)) {
                this.txvTitle.setText(getString(R.string.oops));
                this.txvSubTitle.setText(getString(R.string.no_finance_info));
            } else {
                this.txvTitle.setText(getString(R.string.oops));
                this.txvSubTitle.setText(this.dataFetchStatusMessage);
            }
            this.errorImage.setImageResource(R.drawable.bug);
            this.btnAction.setText(getString(R.string.btn_try_again));
            this.btnAction.setVisibility(View.VISIBLE);
            this.btnAction.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.FinanceDetailsActivity.3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    FinanceDetailsActivity financeDetailsActivity = FinanceDetailsActivity.this;
                    financeDetailsActivity.startSearchVehicleDetailsLoaderActivity(financeDetailsActivity.type);
                }
            });
        } else {
            VehicleDetailsResponse vehicleDetailsResponse = this.response;
            if (vehicleDetailsResponse == null || vehicleDetailsResponse.getDetails() == null || Utils.isNullOrEmptyOrNA(this.response.getDetails().getFinancierName())) {
                showOrHideElements(true, false, this.dataFetchStatusMessage);
                this.errorImage.setImageResource(R.drawable.surprised);
                this.txvTitle.setText(getString(R.string.oops));
                this.txvSubTitle.setText(getString(R.string.no_result_found_finance_info));
                this.btnAction.setText(getString(R.string.btn_go_back_search_again));
                this.btnAction.setVisibility(View.VISIBLE);
                this.btnAction.setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.FinanceDetailsActivity.4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        FinanceDetailsActivity.this.onBackPressed();
                    }
                });
                return;
            }
            handleResponse(this.response.getDetails());
            showOrHideElements(true, true, "");
        }
    }

    public void startSearchVehicleDetailsLoaderActivity(String str) {
        this.dataFetchStatus = "";
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, getString(R.string.app_internet_msg));
            return;
        }
        Intent intent = new Intent(this, SearchVehicleDetailsLoaderActivity.class);
        intent.putExtra("REGISTRATION_NO", this.registrationNo);
        intent.putExtra("ACTION", this.actionName);
        intent.putExtra("TYPE", str);
        startActivity(intent);
        finish();
    }

    private void handleResponse(VehicleDetails vehicleDetails) {
        if (vehicleDetails == null) {
            showOrHideElements(true, false, getString(R.string.error_message));
            return;
        }
        setupCoverUi(vehicleDetails);
        this.txvOwnerNameValue.setText(vehicleDetails.getOwnerName());
        this.txvRegistrationNoValue.setText(vehicleDetails.getRegistrationNo());
        if (Utils.isNullOrEmpty(vehicleDetails.getOwnership())) {
            this.cvOwnership.setVisibility(View.GONE);
        } else {
            this.cvOwnership.setVisibility(View.VISIBLE);
            if (Utils.isNullOrEmpty(vehicleDetails.getOwnershipDesc())) {
                this.txvOwnershipValue.setText(vehicleDetails.getOwnership());
            } else {
                this.txvOwnershipValue.setText(vehicleDetails.getOwnershipDesc());
            }
        }
        if (Utils.isNullOrEmpty(vehicleDetails.getFinancierName())) {
            this.cvFinancier.setVisibility(View.GONE);
        } else {
            this.cvFinancier.setVisibility(View.VISIBLE);
            this.txvFinancierValue.setText(vehicleDetails.getFinancierName());
        }
        this.tvValuateCon.setVisibility(View.VISIBLE);
        findViewById(R.id.btnViewCompleteRC).setOnClickListener(new View.OnClickListener() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.FinanceDetailsActivity.5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FinanceDetailsActivity.this.startSearchVehicleDetailsLoaderActivity("RC");
            }
        });
        showOrHideElements(true, true, "");
    }

    private void setupCoverUi(VehicleDetails vehicleDetails) {
        ImageView imageView = (ImageView) findViewById(R.id.ivVehicleCoverImage);
        if (vehicleDetails.getVehicleInfo() == null) {
            imageView.setImageResource(vehicleDetails.identifyVehicleType());
        } else {
            Picasso.get().load(vehicleDetails.getVehicleInfo().getImageUrl()).placeholder(vehicleDetails.identifyVehicleType()).error(vehicleDetails.identifyVehicleType()).into(imageView);
        }
    }

    @SuppressLint("WrongConstant")
    private void showOrHideElements(boolean z, boolean z2, String str) {
        int i = 8;
        this.errorContainer.setVisibility((!z || !z2) ? View.VISIBLE : View.GONE);
        LinearLayout linearLayout = this.contentLayout;
        if (z && z2) {
            i = 0;
        }
        linearLayout.setVisibility(i);
    }

    public void shareTo3rdPartyApps() {
        VehicleDetailsResponse vehicleDetailsResponse = this.response;
        if (vehicleDetailsResponse == null || vehicleDetailsResponse.getDetails() == null) {
            showToast(this, getString(R.string.share_error), true);
            return;
        }
        VehicleDetails details = this.response.getDetails();
        String format = String.format(getString(R.string.share_finance_detail), details.getRegistrationNo(), details.getOwnerName());
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", format);
        startActivity(Intent.createChooser(intent, "Share with"));
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
            GlobalTracker.from(this).sendSelectButtonEvent(GlobalTracker.BUTTON_SHARE_FINANCE_DETAILS);
            shareTo3rdPartyApps();
            return true;
        }
    }
}
