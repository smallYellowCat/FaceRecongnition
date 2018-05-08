package com.doudou.facerecongnition.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.adapter.usermanager.MyListViewAdapter;
import com.doudou.facerecongnition.constant.GlobalVar;
import com.doudou.facerecongnition.entity.Course;
import com.doudou.facerecongnition.entity.Teacher;
import com.doudou.facerecongnition.net.HttpCallBackListener;
import com.doudou.facerecongnition.net.HttpUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class UserManagerFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Teacher teacher;
    private TextView tv;
    private ListView listView;
    private List<Course> courses = new ArrayList<>();
    private MyListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_manager, container, false);
        listView = view.findViewById(R.id.list);
        tv = view.findViewById(R.id.tv);

        adapter = new MyListViewAdapter(courses,getContext());
        initData();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    private void initData() {
        teacher = getManagerInfo();
        LinkedHashMap<String, Object> para = new LinkedHashMap<>();
        para.put("teacherId", teacher.getId().toString());
        HttpUtil.post(GlobalVar.myServer + "/course/getCourseList", para, new HttpCallBackListener() {
                    @Override
                    public void onFinish(JSONObject response) {
                        if(((JSONObject)response.get("data")).isEmpty()){
                            handler.sendEmptyMessage(0);
                        }else {
                            List<Course> list = JSON.parseArray(((JSONObject)response.get("data")).get("courseList").toString(), Course.class);
                            if (list != null && list.size() > 0){
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = list;
                                handler.sendMessage(msg);
                            }else {
                                handler.sendEmptyMessage(0);
                            }
                        }

                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }


    private Teacher getManagerInfo() {
        return (Teacher) getArguments().getSerializable("teacher");
    }

    @Override
    public void onClick(View v) {

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    tv.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    tv.setVisibility(View.INVISIBLE);
                    courses.clear();
                    courses.addAll((Collection<? extends Course>) msg.obj);
                    adapter.notifyDataSetChanged();
                    break;

            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
