package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.TrafficSignalResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class TrafficSignalsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<TrafficSignalResponse> responseHandler;

    public TrafficSignalsResponseHandler(TaskHandler.ResponseHandler<TrafficSignalResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((TrafficSignalResponse) new Gson().fromJson(jSONObject.toString(), TrafficSignalResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
