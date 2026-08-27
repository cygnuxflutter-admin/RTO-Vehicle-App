package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class BikeVariantsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<BikeVariantsResponse> responseHandler;

    public BikeVariantsResponseHandler(TaskHandler.ResponseHandler<BikeVariantsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((BikeVariantsResponse) new Gson().fromJson(jSONObject.toString(), BikeVariantsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
