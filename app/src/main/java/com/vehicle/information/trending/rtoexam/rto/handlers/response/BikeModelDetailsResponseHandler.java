package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeModelDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class BikeModelDetailsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<BikeModelDetailsResponse> responseHandler;

    public BikeModelDetailsResponseHandler(TaskHandler.ResponseHandler<BikeModelDetailsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((BikeModelDetailsResponse) new Gson().fromJson(jSONObject.toString(), BikeModelDetailsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
