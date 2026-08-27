package com.vehicle.information.trending.rtoexam.rto.Task_Extra;

import android.app.Activity;
import android.content.Context;
import android.os.Build;


public class Task_RtoUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String formatString(String str) {
        return isNullOrEmpty(str) ? "" : str.replaceAll("[^A-Za-z0-9]", "");
    }

    public static boolean isActivityFinished(Context context) {
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT < 17) {
                return !((Activity) context).isFinishing();
            }
            Activity activity = (Activity) context;
            return (activity.isFinishing() || activity.isDestroyed()) ? false : true;
        }
        return false;
    }
}
