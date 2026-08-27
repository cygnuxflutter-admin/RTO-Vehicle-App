package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import com.google.gson.Gson;
import com.vehicle.information.trending.rtoexam.rto.Task_Extra.EncryptionHandler;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.ExternalVehicleDetailsResponse;

public class ResponseJuicer {
    public static ExternalVehicleDetailsResponse responseJuice(String str) {
        if (Utils.isNullOrEmpty(str)) {
            return null;
        }
        try {
            String decrypt = EncryptionHandler.decrypt(str, GlobalReferenceEngine.dataAccessKey);
            if (Utils.isNullOrEmpty(decrypt)) {
                return null;
            }
            return (ExternalVehicleDetailsResponse) new Gson().fromJson(decrypt, ExternalVehicleDetailsResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
