package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;

import org.json.JSONObject;


public class LogDealersEnquiryResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<JSONObject> responseHandler;

    public LogDealersEnquiryResponseHandler(TaskHandler.ResponseHandler<JSONObject> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            TaskHandler.ResponseHandler<JSONObject> responseHandler = this.responseHandler;
            if (responseHandler != null) {
                responseHandler.onResponse(jSONObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
