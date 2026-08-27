package com.vehicle.information.trending.rtoexam.rto.Task_ListenerData;

import android.util.Log;
import com.android.volley.Response;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_RtoUtil;
import org.json.JSONObject;


public class Task_JSONRespondListeners implements Response.Listener<JSONObject> {
    Task_RequestLoaders requestLoader;

    public Task_JSONRespondListeners(Task_RequestLoaders m_rtoTaskRequestLoaders) {
        this.requestLoader = m_rtoTaskRequestLoaders;
    }

    @Override
    public void onResponse(JSONObject jSONObject) {
        Log.d("VehicleDetailsAPI", "Task_JSONRespondListeners: Success Response -> " + (jSONObject != null ? jSONObject.toString() : "null"));
        if (this.requestLoader.isProgressDialogShowing && Task_RtoUtil.isActivityFinished(this.requestLoader.context) && this.requestLoader.progressDialog != null && this.requestLoader.progressDialog.isShowing()) {
            this.requestLoader.mInstance.cancelProgressDialog(this.requestLoader.progressDialog);
        }
        this.requestLoader.jsonResponseHandler.onResponse(jSONObject);
    }
}
