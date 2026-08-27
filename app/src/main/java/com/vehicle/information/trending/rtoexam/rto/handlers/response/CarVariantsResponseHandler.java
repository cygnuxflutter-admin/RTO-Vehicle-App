package com.vehicle.information.trending.rtoexam.rto.handlers.response;

//import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.CarVariantsResponse;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;
import com.google.gson.Gson;

import org.json.JSONObject;


public class CarVariantsResponseHandler implements TaskHandler.JsonResponseHandler {
    private TaskHandler.ResponseHandler<CarVariantsResponse> responseHandler;

    public CarVariantsResponseHandler(TaskHandler.ResponseHandler<CarVariantsResponse> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.JsonResponseHandler
    public void onResponse(JSONObject jSONObject) {
        try {
            this.responseHandler.onResponse((CarVariantsResponse) new Gson().fromJson(jSONObject.toString(), CarVariantsResponse.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
