package com.doudou.facerecongnition.net;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.doudou.facerecongnition.application.MyApplication;

import java.util.Map;

/**
 * Created by wangjiajing on 2016/7/21.
 */
public class VolleyRequest {
    public static StringRequest stringRequest;
    public  static Context context;
    public static void RequsetGet(Context mContext,String url,String tag,VolleyInterface vif){
        MyApplication.getHttpQuens().cancelAll(tag);
        stringRequest  = new StringRequest(Request.Method.GET,url, vif.loadListener(),vif.errorListener());
        stringRequest.setTag(tag);
        MyApplication.getHttpQuens().add(stringRequest);
        MyApplication.getHttpQuens().start();
    }
    public static void RequestPost(Context context, String url, String tag, final Map<String,String>params, VolleyInterface vif){
        MyApplication.getHttpQuens().cancelAll(tag);
        stringRequest = new StringRequest(url,vif.loadListener(),vif.errorListener()){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setTag(tag);
        MyApplication.getHttpQuens().add(stringRequest);
        MyApplication.getHttpQuens().start();
    }
}
