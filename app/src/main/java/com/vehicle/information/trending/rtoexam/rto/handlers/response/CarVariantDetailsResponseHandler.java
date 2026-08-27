package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarVariantDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CarVariantDetailsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CarVariantDetailsResponse> responseHandler;

    public CarVariantDetailsResponseHandler(TaskHandler.ResponseHandler<CarVariantDetailsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CarVariantDetailsResponse) new Gson().fromJson(jSONObject.toString(), CarVariantDetailsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
