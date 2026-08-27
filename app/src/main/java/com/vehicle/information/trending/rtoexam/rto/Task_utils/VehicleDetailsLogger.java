package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.app.Activity;
import android.os.Bundle;

//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.datamodels.VehicleDetails;
//import com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.handlers.TaskHandler;
import com.google.gson.Gson;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.VehicleDetails;
import com.vehicle.information.trending.rtoexam.rto.handlers.TaskHandler;

import org.json.JSONObject;

import java.util.HashMap;


public class VehicleDetailsLogger {
    public static void logVehicleDetails(Activity activity, final VehicleDetails vehicleDetails) {
        if (GlobalReferenceEngine.isLogServerData && Utils.isNetworkConnected(activity) && vehicleDetails != null && !vehicleDetails.isEmptyResponse()) {
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("registrationNo", vehicleDetails.getRegistrationNo());
                hashMap.put("details", new Gson().toJson(vehicleDetails));
                TaskHandler.newInstance().pushVehicleDetails(activity, hashMap, new TaskHandler.ResponseHandler<JSONObject>() { // from class: com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.helpers.VehicleDetailsLogger.1
                    @Override // com.vehicle.information.trending.rtoexam.rto.rtovehicleinformation.handlers.TaskHandler.ResponseHandler
                    public void onError(String str) {
                        Bundle bundle = new Bundle();
                        bundle.putString(GlobalTracker.EVENT_LOG_VEHICLE_DETAILS, "Error in logging vehicle details: " + VehicleDetails.getRegistrationNo());
                    }

                    public void onResponse(JSONObject jSONObject) {
                        Bundle bundle = new Bundle();
                        bundle.putString(GlobalTracker.EVENT_LOG_VEHICLE_DETAILS, "Success in logging vehicle details: " + VehicleDetails.getRegistrationNo());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
