package com.doudou.facerecongnition.application;

import android.app.Application;
import android.content.Context;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化sdk
        SpeechUtility speechUtility = SpeechUtility.createUtility(mContext, SpeechConstant.APPID +"=5a6d4848");

    }

    public static Context getmContext1(){
        return mContext;
    }
}
