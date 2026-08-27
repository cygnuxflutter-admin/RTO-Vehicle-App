package com.vehicle.information.trending.rtoexam.rto.handlers.response;

import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;


public class ExternalVehicleDetailsResponseHandler implements TaskHandler.ResponseHandler<String> {
    private TaskHandler.ResponseHandler<String> responseHandler;

    public ExternalVehicleDetailsResponseHandler(TaskHandler.ResponseHandler<String> responseHandler2) {
        this.responseHandler = responseHandler2;
    }

    @Override // com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler.ResponseHandler
    public void onError(String str) {
        this.responseHandler.onError(str);
    }

    public void onResponse(String str) {
        try {
            this.responseHandler.onResponse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
