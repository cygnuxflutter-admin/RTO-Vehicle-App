package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarServiceCentersResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CarServiceCentersResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CarServiceCentersResponse> responseHandler;

    public CarServiceCentersResponseHandler(TaskHandler.ResponseHandler<CarServiceCentersResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CarServiceCentersResponse) new Gson().fromJson(jSONObject.toString(), CarServiceCentersResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
