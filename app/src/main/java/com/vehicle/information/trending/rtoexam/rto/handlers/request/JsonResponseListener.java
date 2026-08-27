package com.vehicle.information.trending.rtoexam.rto.handlers.request;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Response;

import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;

import org.json.JSONObject;


public class JsonResponseListener implements Response.Listener<JSONObject> {
    private RequestLoader requestLoader;

    public JsonResponseListener(RequestLoader requestLoader2) {
        this.requestLoader = requestLoader2;
    }

    public void onResponse(JSONObject jSONObject) {
        Log.d("VehicleDetailsAPI", "JsonResponseListener: API Response Success -> " + (jSONObject != null ? jSONObject.toString() : "null"));
        ProgressDialog progressDialog;
        RequestLoader requestLoader = this.requestLoader;
        if (requestLoader.isProgressDialogShowing && Utils.isActivityFinished(requestLoader.context) && (progressDialog = this.requestLoader.progressDialog) != null && progressDialog.isShowing()) {
            RequestLoader requestLoader2 = this.requestLoader;
            requestLoader2.mInstance.cancelProgressDialog(requestLoader2.progressDialog);
        }
        this.requestLoader.jsonResponseHandler.onResponse(jSONObject);
    }
}
