package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_VehicleDocumentModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Task_ReminderStorage {
    private static final String PREF_NAME = "vehicle_documents_vault";
    private static final String KEY_DOCS = "saved_vehicles_list";

    public static List<Task_VehicleDocumentModel> getSavedVehicles(Context context) {
        List<Task_VehicleDocumentModel> list = new ArrayList<>();
        SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String jsonStr = sp.getString(KEY_DOCS, "[]");

        try {
            JSONArray arr = new JSONArray(jsonStr);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                list.add(new Task_VehicleDocumentModel(
                        obj.optString("id", String.valueOf(System.currentTimeMillis())),
                        obj.optString("vehicleNumber", ""),
                        obj.optString("vehicleName", ""),
                        obj.optString("insuranceExpiry", ""),
                        obj.optString("pucExpiry", ""),
                        obj.optString("serviceDueDate", "")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Add sample vehicle if empty for first time user
        if (list.isEmpty()) {
            list.add(new Task_VehicleDocumentModel("sample_1", "GJ 05 AB 1234", "My Honda City", "15 Dec 2026", "24 Nov 2026", "10 Jan 2027"));
            saveVehicles(context, list);
        }

        return list;
    }

    public static void saveVehicles(Context context, List<Task_VehicleDocumentModel> list) {
        try {
            JSONArray arr = new JSONArray();
            for (Task_VehicleDocumentModel item : list) {
                JSONObject obj = new JSONObject();
                obj.put("id", item.getId());
                obj.put("vehicleNumber", item.getVehicleNumber());
                obj.put("vehicleName", item.getVehicleName());
                obj.put("insuranceExpiry", item.getInsuranceExpiry());
                obj.put("pucExpiry", item.getPucExpiry());
                obj.put("serviceDueDate", item.getServiceDueDate());
                arr.put(obj);
            }
            SharedPreferences sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            sp.edit().putString(KEY_DOCS, arr.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addVehicle(Context context, Task_VehicleDocumentModel model) {
        List<Task_VehicleDocumentModel> list = getSavedVehicles(context);
        list.add(0, model);
        saveVehicles(context, list);
    }

    public static void deleteVehicle(Context context, String id) {
        List<Task_VehicleDocumentModel> list = getSavedVehicles(context);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                list.remove(i);
                break;
            }
        }
        saveVehicles(context, list);
    }
}