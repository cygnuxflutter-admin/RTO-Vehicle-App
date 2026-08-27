package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.SearchChallanHistoryTableAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.CustomLoaderScreen;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ChallanDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.SearchChallanHistory;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;


public class SearchResultLoaderActivity extends AppCompatActivity {
    private String actionName;
    public ChallanDetailsResponse challanDetailsResponse;
    private CustomLoaderScreen customLoaderScreen;
    public String registrationNo;
    private String searchType;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_result_loader);
        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.searchType = getIntent().getStringExtra("SEARCH_TYPE");
        String stringExtra = getIntent().getStringExtra("ACTION");
        this.actionName = stringExtra;
        if (!Utils.isNullOrEmpty(stringExtra) && this.actionName.equalsIgnoreCase("SAVE")) {
            try {
                SearchChallanHistory searchChallanHistory = new SearchChallanHistory();
                searchChallanHistory.setRegistrationNo(this.registrationNo);
                searchChallanHistory.setSearchType(this.searchType);
                new SearchChallanHistoryTableAdapter(this).insertSearchChallanHistory(searchChallanHistory, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        CustomLoaderScreen customLoaderScreen = (CustomLoaderScreen) findViewById(R.id.customLoader);
        this.customLoaderScreen = customLoaderScreen;
        customLoaderScreen.setVisibilityCustomLoaderScreen(0);
        this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchResultLoaderActivity.1
            @Override
            // com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.widget.CustomLoaderScreen.Callback
            public final void start() {
                SearchResultLoaderActivity.this.loadServerData();
            }
        });
    }

    public void loadServerData() {
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, false, "");
        } else {
            TaskHandler.newInstance().fetchChallanDetails(this, this.registrationNo, true, false, new TaskHandler.ResponseHandler<ChallanDetailsResponse>() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.SearchResultLoaderActivity.2
                @Override
                // com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.handlers.TaskHandler.ResponseHandler
                public void onError(String str) {
                    SearchResultLoaderActivity searchResultLoaderActivity = SearchResultLoaderActivity.this;
                    searchResultLoaderActivity.showOrHideElements(true, true, false, searchResultLoaderActivity.getString(R.string.no_challan_info));
                }

                public void onResponse(ChallanDetailsResponse challanDetailsResponse) {
                    String concat;
                    String str = SearchResultLoaderActivity.this.registrationNo + " INTERNAL_SERVER ";
                    if (challanDetailsResponse == null) {
                        concat = str.concat("500 ").concat("Null Response");
                    } else {
                        concat = str.concat(String.valueOf(challanDetailsResponse.getStatusCode())).concat(" ").concat(challanDetailsResponse.getStatusMessage());
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("VIEW_CHALLAN_DETAILS", concat);
                    bundle.putString("content_type", "CHALLAN_DETAILS");
                    if (challanDetailsResponse == null || challanDetailsResponse.getStatusCode() != 200) {
                        if (challanDetailsResponse != null && challanDetailsResponse.getStatusCode() > 200 && challanDetailsResponse.getStatusCode() < 500 && challanDetailsResponse.getStatusCode() != 404) {
                            SearchResultLoaderActivity.this.showOrHideElements(true, true, false, challanDetailsResponse.getStatusMessage());
                        } else if (challanDetailsResponse == null || challanDetailsResponse.getStatusCode() != 404) {
                            SearchResultLoaderActivity searchResultLoaderActivity = SearchResultLoaderActivity.this;
                            searchResultLoaderActivity.showOrHideElements(true, true, false, searchResultLoaderActivity.getString(R.string.no_challan_info));
                        } else {
                            SearchResultLoaderActivity.this.showOrHideElements(true, true, false, "");
                        }
                    } else if (challanDetailsResponse.getDetails() == null || challanDetailsResponse.getDetails().size() <= 0) {
                        SearchResultLoaderActivity.this.showOrHideElements(true, false, true, challanDetailsResponse.getStatusMessage());
                    } else {
                        SearchResultLoaderActivity searchResultLoaderActivity2 = SearchResultLoaderActivity.this;
                        searchResultLoaderActivity2.challanDetailsResponse = challanDetailsResponse;
                        searchResultLoaderActivity2.showOrHideElements(true, false, false, "");
                    }
                }
            });
        }
    }

    public void showOrHideElements(boolean z, boolean z2, boolean z3, String str) {
        CustomLoaderScreen customLoaderScreen = this.customLoaderScreen;
        if (customLoaderScreen != null && customLoaderScreen.isLoadingStarted()) {
            this.customLoaderScreen.finishLoading();
        }
        Intent intent = new Intent(this, ChallanDetailsActivity.class);
        intent.putExtra("REGISTRATION_NO", this.registrationNo);
        intent.putExtra("SEARCH_TYPE", this.searchType);
        intent.putExtra("ACTION", this.actionName);
        intent.putExtra("CHALLAN_DETAILS_DATA", this.challanDetailsResponse);
        if (!z) {
            intent.putExtra("data_fetch_status", "no_internet");
        } else if (z3) {
            intent.putExtra("data_fetch_status", "no_data_available");
            intent.putExtra("data_fetch_status_message", str);
        } else if (z2) {
            intent.putExtra("data_fetch_status", "error");
            intent.putExtra("data_fetch_status_message", str);
        } else {
            intent.putExtra("data_fetch_status", "data_available");
        }
        startActivity(intent);
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
