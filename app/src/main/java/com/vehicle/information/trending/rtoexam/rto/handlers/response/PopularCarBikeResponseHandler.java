package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.PopularCarBikeResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.PopularCarBikeResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;

import org.json.JSONObject;


public class PopularCarBikeResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<PopularCarBikeResponse> responseHandler;

    public PopularCarBikeResponseHandler(TaskHandler.ResponseHandler<PopularCarBikeResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((PopularCarBikeResponse) new Gson().fromJson(jSONObject.toString(), PopularCarBikeResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
