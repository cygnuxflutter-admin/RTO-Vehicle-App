package com.vehicle.information.trending.rtoexam.rto.handlers.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Utils;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;

import org.json.JSONObject;
import org.jsoup.helper.HttpConnection;

import java.util.HashMap;
import java.util.Map;


public class RequestLoader {

    /* renamed from: a  reason: collision with root package name */
    Map<String, Object> f1276a;
    int b;
    String c;
    public Context context;
    public boolean isProgressDialogShowing;
    public TaskHandler.JsonResponseHandler jsonResponseHandler;
    public TaskHandler mInstance;
    public ProgressDialog progressDialog;
    public String requestUrl;
    public TaskHandler.ResponseHandler<String> responseHandler;

    public RequestLoader(TaskHandler taskHandler, int i, Map<String, Object> map, String str, boolean z, Context context2, ProgressDialog progressDialog2, TaskHandler.JsonResponseHandler jsonResponseHandler2, String str2) {
        this.mInstance = taskHandler;
        this.b = i;
        this.f1276a = map;
        this.requestUrl = str;
        this.isProgressDialogShowing = z;
        this.context = context2;
        this.progressDialog = progressDialog2;
        this.jsonResponseHandler = jsonResponseHandler2;
        this.c = str2;
    }

    public RequestLoader(TaskHandler taskHandler, int i, Map<String, Object> map, String str, boolean z, Context context2, ProgressDialog progressDialog2, TaskHandler.ResponseHandler<String> responseHandler2, String str2) {
        this.mInstance = taskHandler;
        this.b = i;
        this.f1276a = map;
        this.requestUrl = str;
        this.isProgressDialogShowing = z;
        this.context = context2;
        this.progressDialog = progressDialog2;
        this.responseHandler = responseHandler2;
        this.c = str2;
    }

    public void request() {
        JSONObject jSONObject;
        StringBuilder sb = new StringBuilder();
        if (this.b == 0) {
            for (Map.Entry<String, Object> entry : this.f1276a.entrySet()) {
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(this.mInstance.encodeString((String) entry.getValue()));
            }
            jSONObject = null;
        } else {
            jSONObject = new JSONObject(this.f1276a);
        }
        JSONObject jSONObject2 = jSONObject;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.requestUrl);
        sb2.append(sb.length() == 0 ? "" : "?" + ((Object) sb));

        Log.d("VehicleDetailsAPI", "--------------------------------------------------");
        Log.d("VehicleDetailsAPI", "RequestLoader: API Request Initiated");
        Log.d("VehicleDetailsAPI", "Method: " + (this.b == 0 ? "GET" : "POST"));
        Log.d("VehicleDetailsAPI", "URL: " + sb2.toString());
        Log.d("VehicleDetailsAPI", "Params/Body: " + (jSONObject2 != null ? jSONObject2.toString() : (this.f1276a != null ? this.f1276a.toString() : "null")));
        Log.d("VehicleDetailsAPI", "Tag: " + this.c);
        Log.d("VehicleDetailsAPI", "--------------------------------------------------");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(this.b, sb2.toString(), jSONObject2, new JsonResponseListener(this), new RequestErrorListener(this));
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(250000, 1, 1.0f));
        CustomRequestQueue.getInstance(this.context).addToRequestQueue(jsonObjectRequest, this.c);
    }

    public void requestExternal() {
        StringRequest stringRequest = new StringRequest(this.b, this.requestUrl, new Response.Listener() { // from class: com.vehicle.information.trending.rtoexam.rto.handlers.request.RequestLoader.1
            @Override // com.android.volley.Response.Listener
            public final void onResponse(Object obj) {
                RequestLoader.this.lambda$requestExternal$0$RequestLoader((String) obj);
            }
        }, new RequestErrorListener(this)) { // from class: com.vehicle.information.trending.rtoexam.rto.handlers.request.RequestLoader.2
            @Override // com.android.volley.Request
            public Map<String, String> getParams() {
                HashMap hashMap = new HashMap();
                for (Map.Entry<String, Object> entry : RequestLoader.this.f1276a.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        hashMap.put(entry.getKey(), (String) entry.getValue());
                    }
                }
                return hashMap;
            }

            @Override // com.android.volley.Request
            public Map<String, String> getHeaders() {
                HashMap hashMap = new HashMap();
                hashMap.put(HttpConnection.CONTENT_TYPE, "application/x-www-form-urlencoded");
                return hashMap;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(250000, 1, 1.0f));
        CustomRequestQueue.getInstance(this.context).addToRequestQueue(stringRequest, this.c);
    }

    public void lambda$requestExternal$0$RequestLoader(String str) {
        ProgressDialog progressDialog;
        if (this.isProgressDialogShowing && Utils.isActivityFinished(this.context) && (progressDialog = this.progressDialog) != null && progressDialog.isShowing()) {
            this.mInstance.cancelProgressDialog(this.progressDialog);
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putString("E_VEHICLE_DETAILS_PARAMS", this.f1276a.toString());
            bundle.putString("E_VEHICLE_DETAILS_RESPONSE", str);
            bundle.putString("content_type", "E_VEHICLE_DETAILS");
        } catch (Exception e) {
        }
        this.responseHandler.onResponse(str);
    }
}
