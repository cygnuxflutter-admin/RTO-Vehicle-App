package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarModelDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CarModelDetailsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CarModelDetailsResponse> responseHandler;

    public CarModelDetailsResponseHandler(TaskHandler.ResponseHandler<CarModelDetailsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CarModelDetailsResponse) new Gson().fromJson(jSONObject.toString(), CarModelDetailsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
