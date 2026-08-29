package com.vehicle.information.trending.rtoexam.rto.Task_DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_CitiesModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Task_RTODataProvider {

    public static ArrayList<Task_CitiesModel> getRTOOfficesForState(Context context, String stateName) {
        ArrayList<Task_CitiesModel> list = new ArrayList<>();
        if (stateName == null || stateName.trim().isEmpty()) {
            return list;
        }

        // 1. Try loading from clean JSON dataset first
        try {
            InputStream is = context.getAssets().open("rto_offices_india.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);
            JSONObject root = new JSONObject(jsonStr);

            // Look for matching state key (case-insensitive)
            String matchedKey = null;
            for (java.util.Iterator<String> it = root.keys(); it.hasNext(); ) {
                String key = it.next();
                if (key.equalsIgnoreCase(stateName.trim()) || stateName.toLowerCase().contains(key.toLowerCase()) || key.toLowerCase().contains(stateName.toLowerCase())) {
                    matchedKey = key;
                    break;
                }
            }

            if (matchedKey != null) {
                JSONArray arr = root.getJSONArray(matchedKey);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String code = obj.optString("code", "");
                    String district = obj.optString("district", "");
                    String state = obj.optString("state", stateName);
                    String address = obj.optString("address", "");
                    String phone = obj.optString("phone", "");

                    Task_CitiesModel model = new Task_CitiesModel();
                    model.setCode(code);
                    model.setDistrict(district);
                    model.setState(state);
                    model.setField2(code + " - " + district + " (" + state + ")");
                    model.setField3(address);
                    model.setField4("");
                    model.setField5(phone);
                    list.add(model);
                }
                if (!list.isEmpty()) {
                    return list;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2. Fallback to SQLite DB with smart deduplication & cleaning
        Task_DBHelper dbHelper = new Task_DBHelper(context);
        try {
            dbHelper.createDataBase();
        } catch (Exception ignored) {}

        try {
            SQLiteDatabase db = dbHelper.openDataBase();
            Cursor cursor = db.rawQuery("select * from rto_data where field2 LIKE '%" + stateName.replace("'", "''") + "%'", null);
            if (cursor != null && cursor.getCount() > 0) {
                Map<String, Task_CitiesModel> deduplicatedMap = new LinkedHashMap<>();

                cursor.moveToFirst();
                do {
                    int col2 = cursor.getColumnIndex("field2");
                    int col3 = cursor.getColumnIndex("field3");
                    int col4 = cursor.getColumnIndex("field4");
                    int col5 = cursor.getColumnIndex("field5");

                    String field2 = col2 >= 0 ? cursor.getString(col2) : "";
                    String field3 = col3 >= 0 ? cursor.getString(col3) : "";
                    String field4 = col4 >= 0 ? cursor.getString(col4) : "";
                    String field5 = col5 >= 0 ? cursor.getString(col5) : "";

                    if (field2 == null || field2.trim().isEmpty()) continue;

                    Task_CitiesModel model = new Task_CitiesModel();
                    model.setField2(cleanText(field2));
                    model.setField3(cleanText(field3));
                    model.setField4(cleanText(field4));
                    model.setField5(cleanText(field5));

                    String code = model.getCode();
                    String key = (!code.isEmpty() ? code : model.getField2()).trim().toUpperCase();

                    // If not added or if existing entry is shorter, update
                    if (!deduplicatedMap.containsKey(key)) {
                        deduplicatedMap.put(key, model);
                    }
                } while (cursor.moveToNext());
                cursor.close();

                list.addAll(deduplicatedMap.values());
            }
            dbHelper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private static String cleanText(String input) {
        if (input == null) return "";
        // Remove non-printable control characters or garbage
        String cleaned = input.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "").trim();
        return cleaned;
    }
}