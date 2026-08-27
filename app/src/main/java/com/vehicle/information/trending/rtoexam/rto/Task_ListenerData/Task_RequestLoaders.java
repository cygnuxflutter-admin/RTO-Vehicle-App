package com.vehicle.information.trending.rtoexam.rto.Task_ListenerData;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.Map;
import org.json.JSONObject;


public class Task_RequestLoaders {
    public Context context;
    public boolean isProgressDialogShowing;
    public Task_TaskHandler.JsonResponseHandler jsonResponseHandler;
    public Task_TaskHandler mInstance;
    Map<String, Object> params;
    public ProgressDialog progressDialog;
    int requestMethod;
    public String requestUrl;
    public Task_TaskHandler.ResponseHandler<String> responseHandler;
    String tag;

    public Task_RequestLoaders(Task_TaskHandler m_rtoTaskTaskHandler, int i, Map<String, Object> map, String str, boolean z, Context context, ProgressDialog progressDialog, Task_TaskHandler.JsonResponseHandler jsonResponseHandler, String str2) {
        this.mInstance = m_rtoTaskTaskHandler;
        this.requestMethod = i;
        this.params = map;
        this.requestUrl = str;
        this.isProgressDialogShowing = z;
        this.context = context;
        this.progressDialog = progressDialog;
        this.jsonResponseHandler = jsonResponseHandler;
        this.tag = str2;
    }

    public void request() {
        JSONObject jSONObject;
        StringBuilder sb = new StringBuilder();
        if (this.requestMethod == 0) {
            for (Map.Entry<String, Object> entry : this.params.entrySet()) {
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(this.mInstance.encodeString((String) entry.getValue()));
            }
            jSONObject = null;
        } else {
            jSONObject = new JSONObject(this.params);
        }
        JSONObject jSONObject2 = jSONObject;
        StringBuilder sb2 = new StringBuilder();
        sb2.append(this.requestUrl);
        sb2.append(sb.length() == 0 ? "" : "?" + ((Object) sb));

        Log.d("VehicleDetailsAPI", "--------------------------------------------------");
        Log.d("VehicleDetailsAPI", "Task_RequestLoaders: API Request Initiated");
        Log.d("VehicleDetailsAPI", "Method: " + (this.requestMethod == 0 ? "GET" : "POST"));
        Log.d("VehicleDetailsAPI", "URL: " + sb2.toString());
        Log.d("VehicleDetailsAPI", "Params/Body: " + (jSONObject2 != null ? jSONObject2.toString() : (this.params != null ? this.params.toString() : "null")));
        Log.d("VehicleDetailsAPI", "Tag: " + this.tag);
        Log.d("VehicleDetailsAPI", "--------------------------------------------------");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(this.requestMethod, sb2.toString(), jSONObject2, new Task_JSONRespondListeners(this), new Task_RequErrorsListener(this));
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(250000, 1, 1.0f));
        Task_CustomRequestQueues.getInstance(this.context).addToRequestQueue(jsonObjectRequest, this.tag);
    }
}
