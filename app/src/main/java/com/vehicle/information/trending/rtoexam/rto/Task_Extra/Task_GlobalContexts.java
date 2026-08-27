package com.vehicle.information.trending.rtoexam.rto.Task_Extra;

import android.content.Context;


public class Task_GlobalContexts {
    private static Task_GlobalContexts mInstance;
    private Context context;

    private Task_GlobalContexts(Context context) {
        if (this.context == null) {
            this.context = context;
        }
    }

    public static void initialize(Context context) {
        if (mInstance == null) {
            mInstance = new Task_GlobalContexts(context);
        }
    }

    public static Task_GlobalContexts getInstance() {
        return mInstance;
    }

    public Context getContext() {
        return this.context;
    }
}
