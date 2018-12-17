package com.library.apple.food;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MySingleton1 {
    private static MySingleton1 mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private MySingleton1(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();


    }

    public static synchronized MySingleton1 getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton1(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(JsonObjectRequest jsonArrayRequest) {
        getRequestQueue().add(jsonArrayRequest);
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}