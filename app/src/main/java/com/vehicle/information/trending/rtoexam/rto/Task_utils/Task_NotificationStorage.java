package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_NotificationModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Task_NotificationStorage {

    private static final String PREF_NAME = "rto_notification_prefs";
    private static final String KEY_NOTIFICATIONS = "saved_notifications_list";
    private static final String KEY_CLEANED_DEMO = "cleaned_demo_notifications_v1";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static List<Task_NotificationModel> getNotifications(Context context) {
        cleanupDemoIfNeeded(context);
        List<Task_NotificationModel> list = new ArrayList<>();
        String json = getPrefs(context).getString(KEY_NOTIFICATIONS, "[]");
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Task_NotificationModel model = new Task_NotificationModel(
                        obj.optString("id", ""),
                        obj.optString("title", ""),
                        obj.optString("message", ""),
                        obj.optString("date", ""),
                        obj.optString("type", "GENERAL"),
                        obj.optBoolean("isRead", false)
                );
                model.setActionData(obj.optString("actionData", ""));
                list.add(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveNotifications(Context context, List<Task_NotificationModel> list) {
        try {
            JSONArray arr = new JSONArray();
            for (Task_NotificationModel item : list) {
                JSONObject obj = new JSONObject();
                obj.put("id", item.getId());
                obj.put("title", item.getTitle());
                obj.put("message", item.getMessage());
                obj.put("date", item.getDate());
                obj.put("type", item.getType());
                obj.put("isRead", item.isRead());
                obj.put("actionData", item.getActionData());
                arr.put(obj);
            }
            getPrefs(context).edit().putString(KEY_NOTIFICATIONS, arr.toString()).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addNotification(Context context, Task_NotificationModel model) {
        List<Task_NotificationModel> list = getNotifications(context);
        // Prepend new notification to top
        list.add(0, model);
        saveNotifications(context, list);
    }

    public static void markAsRead(Context context, String notificationId) {
        List<Task_NotificationModel> list = getNotifications(context);
        for (Task_NotificationModel item : list) {
            if (item.getId().equals(notificationId)) {
                item.setRead(true);
                break;
            }
        }
        saveNotifications(context, list);
    }

    public static void markAllAsRead(Context context) {
        List<Task_NotificationModel> list = getNotifications(context);
        for (Task_NotificationModel item : list) {
            item.setRead(true);
        }
        saveNotifications(context, list);
    }

    public static boolean hasUnreadNotifications(Context context) {
        List<Task_NotificationModel> list = getNotifications(context);
        for (Task_NotificationModel item : list) {
            if (!item.isRead()) {
                return true;
            }
        }
        return false;
    }

    public static void clearAllNotifications(Context context) {
        getPrefs(context).edit().putString(KEY_NOTIFICATIONS, "[]").apply();
    }

    private static void cleanupDemoIfNeeded(Context context) {
        SharedPreferences prefs = getPrefs(context);
        if (!prefs.getBoolean(KEY_CLEANED_DEMO, false)) {
            // Clear any previously saved demo notifications on device
            prefs.edit().putString(KEY_NOTIFICATIONS, "[]")
                    .putBoolean(KEY_CLEANED_DEMO, true)
                    .apply();
        }
    }
}
