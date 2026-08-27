package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeDealersResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class BikeDealersResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<BikeDealersResponse> responseHandler;

    public BikeDealersResponseHandler(TaskHandler.ResponseHandler<BikeDealersResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((BikeDealersResponse) new Gson().fromJson(jSONObject.toString(), BikeDealersResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
