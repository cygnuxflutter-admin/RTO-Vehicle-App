package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.ChallanDetailsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class ChallanDetailsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<ChallanDetailsResponse> responseHandler;

    public ChallanDetailsResponseHandler(TaskHandler.ResponseHandler<ChallanDetailsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((ChallanDetailsResponse) new Gson().fromJson(jSONObject.toString(), ChallanDetailsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
