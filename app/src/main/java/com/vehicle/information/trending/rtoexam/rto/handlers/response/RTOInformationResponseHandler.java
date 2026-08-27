package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.RTOInformationResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class RTOInformationResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<RTOInformationResponse> responseHandler;

    public RTOInformationResponseHandler(TaskHandler.ResponseHandler<RTOInformationResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((RTOInformationResponse) new Gson().fromJson(jSONObject.toString(), RTOInformationResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
