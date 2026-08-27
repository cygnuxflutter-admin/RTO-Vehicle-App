package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.ResaleValueVehicleDataResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class ResaleValueVehicleDataResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<ResaleValueVehicleDataResponse> responseHandler;

    public ResaleValueVehicleDataResponseHandler(TaskHandler.ResponseHandler<ResaleValueVehicleDataResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((ResaleValueVehicleDataResponse) new Gson().fromJson(jSONObject.toString(), ResaleValueVehicleDataResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
