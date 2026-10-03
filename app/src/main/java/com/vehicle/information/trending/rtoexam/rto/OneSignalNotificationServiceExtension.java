package com.vehicle.information.trending.rtoexam.rto;

import android.content.Context;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationReceivedEvent;
import com.onesignal.OneSignal;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_NotificationModel;
import com.vehicle.information.trending.rtoexam.rto.Task_utils.Task_NotificationStorage;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("unused")
public class OneSignalNotificationServiceExtension implements OneSignal.OSRemoteNotificationReceivedHandler {

    @Override
    public void remoteNotificationReceived(Context context, OSNotificationReceivedEvent notificationReceivedEvent) {
        OSNotification notification = notificationReceivedEvent.getNotification();

        try {
            String title = notification.getTitle();
            String body = notification.getBody();
            JSONObject additionalData = notification.getAdditionalData();

            if (title != null && body != null) {
                // Determine notification type from additional data (default to GENERAL)
                String type = "GENERAL";
                if (additionalData != null && additionalData.has("type")) {
                    type = additionalData.optString("type", "GENERAL");
                }

                // Format current date
                String currentDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a", Locale.getDefault()).format(new Date());

                // Create and save the model
                Task_NotificationModel model = new Task_NotificationModel(
                        notification.getNotificationId(), // unique ID
                        title,
                        body,
                        currentDate,
                        type,
                        false
                );
                
                if (additionalData != null && additionalData.has("action")) {
                    model.setActionData(additionalData.optString("action"));
                }

                Task_NotificationStorage.addNotification(context, model);
                Log.d("OneSignal_Save", "Notification saved to local DB: " + title);
            }
        } catch (Exception e) {
            Log.e("OneSignal_Save", "Error saving notification: " + e.getMessage());
        }

        // Show the notification to the user in the system tray
        notificationReceivedEvent.complete(notification);
    }
}
