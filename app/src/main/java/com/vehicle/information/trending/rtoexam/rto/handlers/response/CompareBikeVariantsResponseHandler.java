package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.CompareBikeVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CompareBikeVariantsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CompareBikeVariantsResponse> responseHandler;

    public CompareBikeVariantsResponseHandler(TaskHandler.ResponseHandler<CompareBikeVariantsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CompareBikeVariantsResponse) new Gson().fromJson(jSONObject.toString(), CompareBikeVariantsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
