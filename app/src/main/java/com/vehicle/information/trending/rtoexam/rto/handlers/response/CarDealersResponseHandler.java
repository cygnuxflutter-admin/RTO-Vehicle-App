package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarDealersResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CarDealersResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CarDealersResponse> responseHandler;

    public CarDealersResponseHandler(TaskHandler.ResponseHandler<CarDealersResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CarDealersResponse) new Gson().fromJson(jSONObject.toString(), CarDealersResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
