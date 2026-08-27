package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleValuationDataResponse;

import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class VehicleValuationDataResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<VehicleValuationDataResponse> responseHandler;

    public VehicleValuationDataResponseHandler(TaskHandler.ResponseHandler<VehicleValuationDataResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((VehicleValuationDataResponse) new Gson().fromJson(jSONObject.toString(), VehicleValuationDataResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
