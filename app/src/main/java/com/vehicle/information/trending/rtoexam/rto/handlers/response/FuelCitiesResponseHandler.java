package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.FuelCityResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class FuelCitiesResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<FuelCityResponse> responseHandler;

    public FuelCitiesResponseHandler(TaskHandler.ResponseHandler<FuelCityResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((FuelCityResponse) new Gson().fromJson(jSONObject.toString(), FuelCityResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
