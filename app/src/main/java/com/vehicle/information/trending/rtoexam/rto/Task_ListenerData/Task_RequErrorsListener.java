package com.vehicle.information.trending.rtoexam.rto.Task_ListenerData;

import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_RtoUtil;
import com.vehicle.information.trending.rtoexam.rto.R;


public class Task_RequErrorsListener implements Response.ErrorListener {
    Task_RequestLoaders requestLoader;

    public Task_RequErrorsListener(Task_RequestLoaders m_rtoTaskRequestLoaders) {
        this.requestLoader = m_rtoTaskRequestLoaders;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e("VehicleDetailsAPI", "Task_RequErrorsListener: API Error Received -> " + volleyError);
        if (volleyError != null && volleyError.networkResponse != null) {
            Log.e("VehicleDetailsAPI", "Status code: " + volleyError.networkResponse.statusCode);
        }
        if (this.requestLoader.isProgressDialogShowing && Task_RtoUtil.isActivityFinished(this.requestLoader.context) && this.requestLoader.progressDialog != null && this.requestLoader.progressDialog.isShowing()) {
            this.requestLoader.mInstance.cancelProgressDialog(this.requestLoader.progressDialog);
        }
        if (volleyError.networkResponse != null) {
            String simpleName = this.requestLoader.getClass().getSimpleName();
            Log.d(simpleName, "onErrorResponse: " + volleyError);
            if (this.requestLoader.jsonResponseHandler != null) {
                this.requestLoader.jsonResponseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
            } else if (this.requestLoader.responseHandler != null) {
                this.requestLoader.responseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
            }
        } else if (this.requestLoader.jsonResponseHandler != null) {
            this.requestLoader.jsonResponseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
        } else if (this.requestLoader.responseHandler != null) {
            this.requestLoader.responseHandler.onError(this.requestLoader.context.getString(R.string.no_info));
        }
    }
}
