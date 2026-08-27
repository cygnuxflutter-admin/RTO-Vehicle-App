package com.vehicle.information.trending.rtoexam.rto.Task_ListenerData;

import org.json.JSONObject;


public class Task_VehiclesDetailRespondHandler implements Task_TaskHandler.JsonResponseHandler {
    Task_TaskHandler.ResponseHandler<JSONObject> responseHandler;

    public Task_VehiclesDetailRespondHandler(Task_TaskHandler.ResponseHandler<JSONObject> responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse(jSONObject);
        } catch (Exception unused) {
        }
    }
}
