package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.InitDataResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class InitDataResponseHandler implements TaskHandler.JsonResponseHandler {

    /* renamed from: a  reason: collision with root package name */
    TaskHandler.ResponseHandler<InitDataResponse> f1279a;

    public InitDataResponseHandler(TaskHandler.ResponseHandler<InitDataResponse> responseHandler2) {
        this.f1279a = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.f1279a.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.f1279a.onResponse((InitDataResponse) new Gson().fromJson(jSONObject.toString(), InitDataResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
