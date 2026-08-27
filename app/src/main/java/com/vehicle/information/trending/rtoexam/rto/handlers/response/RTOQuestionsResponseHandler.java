package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.RTOQuestionResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class RTOQuestionsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<RTOQuestionResponse> responseHandler;

    public RTOQuestionsResponseHandler(TaskHandler.ResponseHandler<RTOQuestionResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((RTOQuestionResponse) new Gson().fromJson(jSONObject.toString(), RTOQuestionResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
