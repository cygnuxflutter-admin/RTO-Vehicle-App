package com.vehicle.information.trending.rtoexam.rto.handlers.request;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;


public class RequestErrorListener implements Response.ErrorListener {
    private RequestLoader requestLoader;

    public RequestErrorListener(RequestLoader requestLoader2) {
        this.requestLoader = requestLoader2;
    }

    @Override // com.android.volley.Response.ErrorListener
    public void onErrorResponse(VolleyError volleyError) {
        Log.e("VehicleDetailsAPI", "RequestErrorListener: API Request Failed -> " + volleyError);
        ProgressDialog progressDialog;
        RequestLoader requestLoader = this.requestLoader;
        if (requestLoader.isProgressDialogShowing && Utils.isActivityFinished(requestLoader.context) && (progressDialog = this.requestLoader.progressDialog) != null && progressDialog.isShowing()) {
            RequestLoader requestLoader2 = this.requestLoader;
            requestLoader2.mInstance.cancelProgressDialog(requestLoader2.progressDialog);
        }
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null) {
            int i = networkResponse.statusCode;
            Log.e("VehicleDetailsAPI", "HTTP Response Status Code: " + i);
            if (networkResponse.data != null) {
                try {
                    Log.e("VehicleDetailsAPI", "Error Response Body: " + new String(networkResponse.data));
                } catch (Exception e) {
                }
            }
            try {
                Log.e(this.requestLoader.getClass().getSimpleName(), "onErrorResponse: ");
            } catch (Exception e) {
            }
            String simpleName = this.requestLoader.getClass().getSimpleName();
            Log.d(simpleName, "onErrorResponse: " + volleyError);
            RequestLoader requestLoader3 = this.requestLoader;
            TaskHandler.JsonResponseHandler jsonResponseHandler = requestLoader3.jsonResponseHandler;
            if (jsonResponseHandler != null) {
                jsonResponseHandler.onError(requestLoader3.context.getString(R.string.no_info));
                return;
            }
            TaskHandler.ResponseHandler<String> responseHandler = requestLoader3.responseHandler;
            if (responseHandler != null) {
                responseHandler.onError(requestLoader3.context.getString(R.string.no_info));
                return;
            }
            return;
        }
        RequestLoader requestLoader4 = this.requestLoader;
        TaskHandler.JsonResponseHandler jsonResponseHandler2 = requestLoader4.jsonResponseHandler;
        if (jsonResponseHandler2 != null) {
            jsonResponseHandler2.onError(requestLoader4.context.getString(R.string.no_info));
            return;
        }
        TaskHandler.ResponseHandler<String> responseHandler2 = requestLoader4.responseHandler;
        if (responseHandler2 != null) {
            responseHandler2.onError(requestLoader4.context.getString(R.string.no_info));
        }
    }
}
