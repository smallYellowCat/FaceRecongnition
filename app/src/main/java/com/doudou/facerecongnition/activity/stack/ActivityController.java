package com.doudou.facerecongnition.activity.stack;

import android.support.v7.app.AppCompatActivity;
import com.doudou.facerecongnition.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
*Activity管理类
*@author 豆豆
*时间:
*/
public class ActivityController {
    private List<AppCompatActivity> activities = new ArrayList<>();
    private static ActivityController instance;

    private ActivityController(){}

    public synchronized  static ActivityController getInstance(){
        if (null == instance){
            instance = new ActivityController();
        }
        return instance;
    }

    public void addActivity(AppCompatActivity activity){
            activities.add(activity);
    }

    public void removeActivity(AppCompatActivity activity){
        activities.remove(activity);
    }

    public void outSign(){
        for (AppCompatActivity activity : activities){
            if (activity != null){
                activity.finish();
                activity = null;
            }
        }
    }


}
