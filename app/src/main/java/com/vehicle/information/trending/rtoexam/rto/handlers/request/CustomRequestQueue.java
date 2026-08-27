package com.vehicle.information.trending.rtoexam.rto.handlers.request;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class CustomRequestQueue {
    private static CustomRequestQueue mInstance;
    private Context context;
    private RequestQueue requestQueue;

    private CustomRequestQueue(Context context2) {
        this.context = context2;
    }

    public static synchronized CustomRequestQueue getInstance(Context r2) {
        CustomRequestQueue customRequestQueue;
        synchronized (CustomRequestQueue.class) {
            synchronized (CustomRequestQueue.class) {
                if (mInstance == null) {
                    mInstance = new CustomRequestQueue(r2);
                }
                customRequestQueue = mInstance;
            }
            return customRequestQueue;
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
            str = "CustomRequestQueue";
        }
        request.setTag(str);
        getRequestQueue().add(request);
    }
}
