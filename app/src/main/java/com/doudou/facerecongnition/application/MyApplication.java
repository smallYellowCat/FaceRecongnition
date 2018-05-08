package com.doudou.facerecongnition.application;

import android.app.Application;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.leakcanary.LeakCanary;


public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    public static RequestQueue queues;

    /** 屏幕宽度(px) */
    public static int screenWidth;
    /** 屏幕高度(px) */
    public static int screenHeight;

    /** Application Context */
    private static Application CONTEXT;

    public static Application getInstance() {
        return CONTEXT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = this;

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        queues = Volley.newRequestQueue(getApplicationContext());

        LeakCanary.install(this);
    }

    public  static RequestQueue getHttpQuens(){
        return queues;
    }
}
