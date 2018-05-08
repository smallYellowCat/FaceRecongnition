package com.doudou.facerecongnition.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/***
 *
 *对volley进行二次封装
 * **/
public  abstract class VolleyInterface {
    private Response.Listener<String> listener;
    private Response.ErrorListener  errorListener;
    public Context context;
    public VolleyInterface(Context context) {
        this.context = context;
    }

    public VolleyInterface(Context context, Response.Listener<String>listener,
                           Response.ErrorListener errorListener) {
        this.context = context;
        this.listener = listener;
        this.errorListener = errorListener;
    }
    public abstract  void onMySuccess(String result);
    public abstract void onMyError(VolleyError error);
    public Response.Listener<String> loadListener(){
        listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                onMySuccess(response);

            }
        };
                return listener;
    }
    public Response.ErrorListener errorListener(){
        errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onMyError(error);

            }
        };
        return  errorListener;
    }
}
