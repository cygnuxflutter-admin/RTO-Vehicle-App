package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeBrandsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.BikeBrandsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class BikeBrandsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<BikeBrandsResponse> responseHandler;

    public BikeBrandsResponseHandler(TaskHandler.ResponseHandler<BikeBrandsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((BikeBrandsResponse) new Gson().fromJson(jSONObject.toString(), BikeBrandsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
