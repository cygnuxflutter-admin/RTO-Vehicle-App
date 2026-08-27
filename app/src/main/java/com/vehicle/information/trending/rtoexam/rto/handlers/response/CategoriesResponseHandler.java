package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.CategoriesResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CategoriesResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CategoriesResponse> responseHandler;

    public CategoriesResponseHandler(TaskHandler.ResponseHandler<CategoriesResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CategoriesResponse) new Gson().fromJson(jSONObject.toString(), CategoriesResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
