package com.vehicle.information.trending.rtoexam.rto.Task_ListenerData;

import android.content.Context;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Task_CustomRequestQueues {
    private static Task_CustomRequestQueues mInstance;
    Context context;
    private RequestQueue requestQueue;

    private Task_CustomRequestQueues(Context context) {
        this.context = context;
    }

    public static synchronized Task_CustomRequestQueues getInstance(Context context) {
        Task_CustomRequestQueues m_rtoTaskCustomRequestQueues;
        synchronized (Task_CustomRequestQueues.class) {
            synchronized (Task_CustomRequestQueues.class) {
                synchronized (Task_CustomRequestQueues.class) {
                    synchronized (Task_CustomRequestQueues.class) {
                        synchronized (Task_CustomRequestQueues.class) {
                            if (mInstance == null) {
                                mInstance = new Task_CustomRequestQueues(context);
                            }
                            m_rtoTaskCustomRequestQueues = mInstance;
                        }
                        return m_rtoTaskCustomRequestQueues;
                    }
                }
            }
        }
    }

    private RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            this.requestQueue = Volley.newRequestQueue(this.context);
        }
        return this.requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String str) {
        if (TextUtils.isEmpty(str)) {
            str = "R_CustomRequestQueue";
        }
        request.setTag(str);
        getRequestQueue().add(request);
    }
}
