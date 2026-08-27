package com.vehicle.information.trending.rtoexam.rto.Task_Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.SearchVehicleHistoryTableAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Adapter.VehicleDetailsTableAdapter;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.CustomLoaderScreen;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ExternalVehicleDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ExternalVehicleDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.SearchVehicleHistory;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetails;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetailsDatabaseModel;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.GlobalReferenceEngine;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.ResponseJuicer;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.VehicleDetailsLogger;
import com.vehicle.information.trending.rtoexam.rto.handlers.ScraperAsyncTask;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;

import org.json.JSONObject;


public class SearchVehicleDetailsLoaderActivity extends AppCompatActivity {
    private String actionName;
    private CustomLoaderScreen customLoaderScreen;
    private String registrationNo;
    private String type;
    private VehicleDetailsResponse vehicleDetailsResponse;
    public int counter = 0;
    public int externalCounter = 0;

    @Override
    // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_search_result_loader);
        this.registrationNo = getIntent().getStringExtra("REGISTRATION_NO");
        this.actionName = getIntent().getStringExtra("ACTION");
        this.type = getIntent().getStringExtra("TYPE");
        Log.d("VehicleDetailsAPI", "SearchVehicleDetailsLoaderActivity: onCreate | Registration No: " + this.registrationNo + " | Action: " + this.actionName + " | Type: " + this.type);
        if (!Utils.isNullOrEmpty(this.actionName) && this.actionName.equalsIgnoreCase("SAVE")) {
            try {
                SearchVehicleHistory searchVehicleHistory = new SearchVehicleHistory();
                searchVehicleHistory.setRegistrationNo(this.registrationNo);
                new SearchVehicleHistoryTableAdapter(this).insertSearchVehicleHistory(searchVehicleHistory, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.customLoaderScreen = (CustomLoaderScreen) findViewById(R.id.customLoader);
        managePageElements();
    }

    private void managePageElements() {
        String str = this.type;
        if (str == null || str.equalsIgnoreCase("RC")) {
            try {
                VehicleDetailsDatabaseModel readVehicleDetails = new VehicleDetailsTableAdapter(this).readVehicleDetails(this.registrationNo);
                if (readVehicleDetails == null || readVehicleDetails.getData() == null) {
                    startCustomLoader();
                    return;
                }
                this.vehicleDetailsResponse = (VehicleDetailsResponse) new Gson().fromJson(readVehicleDetails.getData(), VehicleDetailsResponse.class);
                Bundle bundle = new Bundle();
                bundle.putString("VIEW_VEHICLE_DETAILS", this.registrationNo + " LOCAL_DB");
                bundle.putString("content_type", "VEHICLE_DETAILS");
                showOrHideElements(true, false, false, "");
            } catch (Exception e) {
                if (this.vehicleDetailsResponse == null) {
                    startCustomLoader();
                } else {
                    showOrHideElements(true, false, false, "");
                }
            }
        } else {
            startCustomLoader();
        }
    }

    private void startCustomLoader() {
        this.customLoaderScreen.setVisibilityCustomLoaderScreen(0);
        if (!Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessPoint) && GlobalReferenceEngine.dataAccessPoint.equalsIgnoreCase("WEB")) {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.1
                @Override
                // com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.Callback
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.loadWebServerData(true);
                }
            });
        } else if (!Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessPoint) && GlobalReferenceEngine.dataAccessPoint.equalsIgnoreCase("EXTERNAL")) {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.2
                @Override
                // com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.Callback
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.loadExternalServerData(false);
                }
            });
        } else if (Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessPoint) || !GlobalReferenceEngine.dataAccessPoint.equalsIgnoreCase("LOCAL") || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceInitUrl) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceFinalUrl) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceHostUrl) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceField1) || Utils.isNullOrEmpty(GlobalReferenceEngine.localSourceField2)) {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.3
                @Override
                // com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.Callback
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                }
            });
        } else {
            this.customLoaderScreen.setCallback(new CustomLoaderScreen.Callback() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.4
                @Override
                // com.vehicle.information.trending.rtoexam.rto.widget.CustomLoaderScreen.Callback
                public final void start() {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                }
            });
        }
    }

    public void loadWebServerData(boolean z) {
        if (!Utils.isNetworkConnected(this)) {
            Log.e("VehicleDetailsAPI", "loadWebServerData() -> No network connection!");
            showOrHideElements(false, false, false, "");
        } else {
            Log.d("VehicleDetailsAPI", "loadWebServerData() -> Fetching vehicle details for Reg No: " + this.registrationNo);
            TaskHandler.newInstance().fetchVehicleDetails(this, this.registrationNo, false, z, false, new TaskHandler.ResponseHandler<JSONObject>() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.5
                @Override
                // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.ResponseHandler
                public void onError(String str) {
                    Log.e("VehicleDetailsAPI", "loadWebServerData() -> onError: " + str);
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                }

                public void onResponse(JSONObject jSONObject) {
                    Log.d("VehicleDetailsAPI", "loadWebServerData() -> onResponse: " + jSONObject);
                    SearchVehicleDetailsLoaderActivity.this.manipulateJsonResponse(jSONObject);
                }
            });
        }
    }

    public void manipulateJsonResponse(JSONObject jSONObject) {
        String concat;
        try {
            this.vehicleDetailsResponse = (VehicleDetailsResponse) new Gson().fromJson(jSONObject.toString(), VehicleDetailsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = this.registrationNo + " INTERNAL_SERVER ";
        VehicleDetailsResponse vehicleDetailsResponse = this.vehicleDetailsResponse;
        if (vehicleDetailsResponse == null) {
            concat = str.concat("500 ").concat("Null Response");
        } else {
            concat = str.concat(String.valueOf(vehicleDetailsResponse.getStatusCode())).concat(" ").concat(this.vehicleDetailsResponse.getStatusMessage());
        }
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_VEHICLE_DETAILS", concat);
        bundle.putString("content_type", "VEHICLE_DETAILS");
        VehicleDetailsResponse vehicleDetailsResponse2 = this.vehicleDetailsResponse;
        if (vehicleDetailsResponse2 == null || vehicleDetailsResponse2.getStatusCode() != 200) {
            VehicleDetailsResponse vehicleDetailsResponse3 = this.vehicleDetailsResponse;
            if (vehicleDetailsResponse3 == null || vehicleDetailsResponse3.getStatusCode() <= 200 || this.vehicleDetailsResponse.getStatusCode() >= 500 || this.vehicleDetailsResponse.getStatusCode() == 404) {
                VehicleDetailsResponse vehicleDetailsResponse4 = this.vehicleDetailsResponse;
                if (vehicleDetailsResponse4 == null || vehicleDetailsResponse4.getStatusCode() != 404) {
                    loadLocalSourceData();
                } else if (this.vehicleDetailsResponse.isExtra() && this.externalCounter < 1 && !Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessUrl) && !Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessKey) && !Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessParams)) {
                    loadExternalServerData(true);
                } else if (this.externalCounter == 1) {
                    loadLocalSourceData();
                } else {
                    showOrHideElements(true, false, true, "");
                }
            } else {
                loadLocalSourceData();
            }
        } else if (this.vehicleDetailsResponse.getDetails() == null) {
            showOrHideElements(true, false, true, "");
        } else {
            try {
                new VehicleDetailsTableAdapter(this).saveVehicleDetails(this.registrationNo, jSONObject.toString());
                SearchVehicleHistoryTableAdapter searchVehicleHistoryTableAdapter = new SearchVehicleHistoryTableAdapter(this);
                SearchVehicleHistory searchVehicleHistoryByDetails = searchVehicleHistoryTableAdapter.getSearchVehicleHistoryByDetails(this.registrationNo, true);
                if (searchVehicleHistoryByDetails == null) {
                    searchVehicleHistoryByDetails = new SearchVehicleHistory();
                    searchVehicleHistoryByDetails.setRegistrationNo(this.registrationNo);
                }
                searchVehicleHistoryByDetails.setName(this.vehicleDetailsResponse.getDetails().getOwnerName());
                searchVehicleHistoryTableAdapter.insertSearchVehicleHistory(searchVehicleHistoryByDetails, true);
                showOrHideElements(true, false, false, "");
            } catch (Exception e2) {
                loadLocalSourceData();
            }
        }
    }

    public void manipulateExternalResponse(ExternalVehicleDetailsResponse externalVehicleDetailsResponse) {
        String concat;
        ExternalVehicleDetails result = externalVehicleDetailsResponse.getResult();
        String str = this.registrationNo + " EXTERNAL_SERVER ";
        if (result == null) {
            concat = str.concat("500 ").concat("Null Response");
        } else if (result.isEmptyResponse()) {
            concat = str.concat("404 ").concat("Vehicle Info Not Found");
        } else {
            concat = str.concat(String.valueOf(externalVehicleDetailsResponse.getStatusCode())).concat(" ").concat(externalVehicleDetailsResponse.getStatusMessage());
        }
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_VEHICLE_DETAILS", concat);
        bundle.putString("content_type", "VEHICLE_DETAILS");
        if (externalVehicleDetailsResponse.getStatusCode() != 200 || result == null) {
            if (externalVehicleDetailsResponse.getStatusCode() > 200 && externalVehicleDetailsResponse.getStatusCode() < 500 && externalVehicleDetailsResponse.getStatusCode() != 404) {
                loadLocalSourceData();
            } else if (externalVehicleDetailsResponse.getStatusCode() == 404) {
                showOrHideElements(true, false, true, "");
            } else {
                loadLocalSourceData();
            }
        } else if (result.isEmptyResponse()) {
            showOrHideElements(true, false, true, "");
        } else {
            try {
                VehicleDetailsLogger.logVehicleDetails(this, result.convertInto());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                VehicleDetailsTableAdapter vehicleDetailsTableAdapter = new VehicleDetailsTableAdapter(this);
                this.vehicleDetailsResponse = new VehicleDetailsResponse(200, "Success", result.convertInto());
                vehicleDetailsTableAdapter.saveVehicleDetails(this.registrationNo, new Gson().toJson(this.vehicleDetailsResponse, VehicleDetailsResponse.class));
                SearchVehicleHistoryTableAdapter searchVehicleHistoryTableAdapter = new SearchVehicleHistoryTableAdapter(this);
                SearchVehicleHistory searchVehicleHistoryByDetails = searchVehicleHistoryTableAdapter.getSearchVehicleHistoryByDetails(this.registrationNo, true);
                if (searchVehicleHistoryByDetails == null) {
                    searchVehicleHistoryByDetails = new SearchVehicleHistory();
                    searchVehicleHistoryByDetails.setRegistrationNo(this.registrationNo);
                }
                searchVehicleHistoryByDetails.setName(result.getOwnerName());
                searchVehicleHistoryTableAdapter.insertSearchVehicleHistory(searchVehicleHistoryByDetails, true);
                showOrHideElements(true, false, false, "");
            } catch (Exception e2) {
                loadLocalSourceData();
            }
        }
    }

    public void loadExternalServerData(boolean z) {
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, false, "");
            return;
        }
        if (z) {
            this.externalCounter++;
        }
        TaskHandler.newInstance().fetchVehicleDetails((Context) this, this.registrationNo, GlobalReferenceEngine.dataAccessParams.split(" "), false, new TaskHandler.ResponseHandler<String>() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.6
            @Override
            // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.ResponseHandler
            public void onError(String str) {
                SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
            }

            public void onResponse(String str) {
                if (Utils.isNullOrEmpty(GlobalReferenceEngine.dataAccessRouter) || GlobalReferenceEngine.dataAccessRouter.equalsIgnoreCase("EXTERNAL")) {
                    SearchVehicleDetailsLoaderActivity.this.fetchVehicleDetails(str);
                    return;
                }
                ExternalVehicleDetailsResponse responseJuice = ResponseJuicer.responseJuice(str);
                if (responseJuice == null) {
                    SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                } else {
                    SearchVehicleDetailsLoaderActivity.this.manipulateExternalResponse(responseJuice);
                }
            }
        });
    }

    public void fetchVehicleDetails(String str) {
        if (!Utils.isNetworkConnected(this)) {
            showOrHideElements(false, false, false, "");
        } else {
            TaskHandler.newInstance().fetchVehicleDetails((Context) this, this.registrationNo, str, false, new TaskHandler.ResponseHandler<JSONObject>() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.7
                @Override
                // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.ResponseHandler
                public void onError(String str2) {
                    ExternalVehicleDetailsResponse responseJuice = ResponseJuicer.responseJuice(str2);
                    if (responseJuice == null) {
                        SearchVehicleDetailsLoaderActivity.this.loadLocalSourceData();
                    } else {
                        SearchVehicleDetailsLoaderActivity.this.manipulateExternalResponse(responseJuice);
                    }
                }

                public void onResponse(JSONObject jSONObject) {
                    SearchVehicleDetailsLoaderActivity.this.manipulateJsonResponse(jSONObject);
                }
            });
        }
    }

    public void loadLocalSourceData() {
        this.counter++;
        String[] splitRegistrationNo = Utils.splitRegistrationNo(this.registrationNo);
        new ScraperAsyncTask(this, "", new ScraperAsyncTask.IResponseCallback() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.8
            @Override
            // com.vehicle.information.trending.rtoexam.rto.handlers.ScraperAsyncTask.IResponseCallback
            public void onNotFound() {
                SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.8.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchVehicleDetailsLoaderActivity.this.showOrHideElements(true, false, true, "");
                    }
                });
            }

            @Override
            // com.vehicle.information.trending.rtoexam.rto.handlers.ScraperAsyncTask.IResponseCallback
            public void onError(final String str) {
                SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.8.2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchVehicleDetailsLoaderActivity searchVehicleDetailsLoaderActivity = SearchVehicleDetailsLoaderActivity.this;
                        if (searchVehicleDetailsLoaderActivity.counter >= 2 || searchVehicleDetailsLoaderActivity.externalCounter > 0) {
                            searchVehicleDetailsLoaderActivity.showOrHideElements(true, true, false, str);
                        } else {
                            searchVehicleDetailsLoaderActivity.loadWebServerData(false);
                        }
                    }
                });
            }

            @Override
            // com.vehicle.information.trending.rtoexam.rto.handlers.ScraperAsyncTask.IResponseCallback
            public void onResponse(final VehicleDetails vehicleDetails) {
                SearchVehicleDetailsLoaderActivity.this.runOnUiThread(new Runnable() { // from class: com.vehicle.information.trending.rtoexam.rto.SearchVehicleDetailsLoaderActivity.8.3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SearchVehicleDetailsLoaderActivity.this.manipulateResponse(vehicleDetails);
                    }
                });
            }
        }).execute(splitRegistrationNo[0], splitRegistrationNo[1]);
    }

    public void manipulateResponse(VehicleDetails vehicleDetails) {
        String concat;
        String str = this.registrationNo + " LOCAL_SOURCE ";
        if (vehicleDetails == null) {
            concat = str.concat("500 ").concat("Null Response");
        } else if (vehicleDetails.isEmptyResponse()) {
            concat = str.concat("404 ").concat("Vehicle Info Not Found");
        } else {
            concat = str.concat("200 ").concat("Success");
        }
        Bundle bundle = new Bundle();
        bundle.putString("VIEW_VEHICLE_DETAILS", concat);
        bundle.putString("content_type", "VEHICLE_DETAILS");
        if (vehicleDetails == null || vehicleDetails.isEmptyResponse()) {
            showOrHideElements(true, false, true, "");
            return;
        }
        try {
            VehicleDetailsLogger.logVehicleDetails(this, vehicleDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.vehicleDetailsResponse = new VehicleDetailsResponse(200, "Success", vehicleDetails);
            new VehicleDetailsTableAdapter(this).saveVehicleDetails(this.registrationNo, new Gson().toJson(this.vehicleDetailsResponse, VehicleDetailsResponse.class));
            SearchVehicleHistoryTableAdapter searchVehicleHistoryTableAdapter = new SearchVehicleHistoryTableAdapter(this);
            SearchVehicleHistory searchVehicleHistoryByDetails = searchVehicleHistoryTableAdapter.getSearchVehicleHistoryByDetails(this.registrationNo, true);
            if (searchVehicleHistoryByDetails == null) {
                searchVehicleHistoryByDetails = new SearchVehicleHistory();
                searchVehicleHistoryByDetails.setRegistrationNo(this.registrationNo);
            }
            searchVehicleHistoryByDetails.setName(vehicleDetails.getOwnerName());
            searchVehicleHistoryTableAdapter.insertSearchVehicleHistory(searchVehicleHistoryByDetails, true);
            showOrHideElements(true, false, false, "");
        } catch (Exception e2) {
            loadWebServerData(false);
        }
    }

    public void showOrHideElements(boolean z, boolean z2, boolean z3, String str) {
        Intent intent;
        CustomLoaderScreen customLoaderScreen = this.customLoaderScreen;
        if (customLoaderScreen != null && customLoaderScreen.isLoadingStarted()) {
            this.customLoaderScreen.finishLoading();
        }
        if (!Utils.isNullOrEmpty(this.type) && this.type.equalsIgnoreCase("INSURANCE")) {
            intent = new Intent(this, InsuranceDetailsActivity.class);
        } else if (Utils.isNullOrEmpty(this.type) || !this.type.equalsIgnoreCase("FINANCE")) {
            intent = new Intent(this, VehicleDetailsActivity.class);
        } else {
            intent = new Intent(this, FinanceDetailsActivity.class);
        }
        intent.putExtra("REGISTRATION_NO", this.registrationNo);
        intent.putExtra("ACTION", this.actionName);
        intent.putExtra("TYPE", this.type);
        intent.putExtra("VEHICLE_DETAILS_DATA", this.vehicleDetailsResponse);
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
        finish();
    }
}
