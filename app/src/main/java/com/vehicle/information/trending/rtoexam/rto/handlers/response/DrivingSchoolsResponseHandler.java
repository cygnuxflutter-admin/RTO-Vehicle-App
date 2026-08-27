package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.DrivingSchoolsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class DrivingSchoolsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<DrivingSchoolsResponse> responseHandler;

    public DrivingSchoolsResponseHandler(TaskHandler.ResponseHandler<DrivingSchoolsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((DrivingSchoolsResponse) new Gson().fromJson(jSONObject.toString(), DrivingSchoolsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
