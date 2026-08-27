package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeModelsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class BikeModelsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<BikeModelsResponse> responseHandler;

    public BikeModelsResponseHandler(TaskHandler.ResponseHandler<BikeModelsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((BikeModelsResponse) new Gson().fromJson(jSONObject.toString(), BikeModelsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
