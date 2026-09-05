package com.vehicle.information.trending.rtoexam.rto.Task_utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;

import com.vehicle.information.trending.rtoexam.rto.R;
import com.vehicle.information.trending.rtoexam.rto.Task_Activity.Task_SplashScreenActivity;

public class Task_AlarmReceiver extends BroadcastReceiver {
    
    private static final String CHANNEL_ID = "EXPIRY_REMINDER_CHANNEL";
    
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("TYPE");
        String vehicleNumber = intent.getStringExtra("VEHICLE_NUMBER");
        
        if (type == null) type = "Document";
        if (vehicleNumber == null) vehicleNumber = "";
        
        String customMessage = intent.getStringExtra("MESSAGE");
        String title = type + " Expiry Reminder";
        String message = customMessage != null ? customMessage : ("Your " + type + " for vehicle " + vehicleNumber + " is expiring today. Please renew it to avoid penalties.");
        
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Expiry Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Reminders for PUC and Insurance Expiry");
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        
        Intent mainIntent = new Intent(context, Task_SplashScreenActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), mainIntent, flags);
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher1)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
                
        if (notificationManager != null) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }

        // Store in local Notification Inbox as unread
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMM, hh:mm a", java.util.Locale.getDefault());
            String dateStr = sdf.format(new java.util.Date());
            String notifId = "alarm_" + System.currentTimeMillis();
            com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_NotificationModel model =
                    new com.vehicle.information.trending.rtoexam.rto.Task_Model.Task_NotificationModel(
                            notifId,
                            title,
                            message,
                            dateStr,
                            type,
                            false
                    );
            Task_NotificationStorage.addNotification(context, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
