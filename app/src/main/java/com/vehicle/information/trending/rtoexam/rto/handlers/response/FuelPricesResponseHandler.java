package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.FuelPricesResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class FuelPricesResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<FuelPricesResponse> responseHandler;

    public FuelPricesResponseHandler(TaskHandler.ResponseHandler<FuelPricesResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((FuelPricesResponse) new Gson().fromJson(jSONObject.toString(), FuelPricesResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
