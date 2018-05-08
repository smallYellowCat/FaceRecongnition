package com.doudou.facerecongnition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.doudou.facerecongnition.R;

/**
*
*@author 豆豆
*时间:
*/
public class FaceIdentifyFragment extends Fragment implements View.OnClickListener {

    private SurfaceView surfaceView;
    private ListView list;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_identify, container, false);
       /* //请求服务器获取课程列表
        //JSONObject json = MyHttpUtil.post("", new LinkedHashMap<String, Object>());
        //将json数据解析成相应的对象
        //List mlist = json.getObject("data", List.class);
        List<Course> lists = new ArrayList<>();
        Iterator it = mlist.iterator();
        while (it.hasNext()){
            lists.add((Course) it.next());
        }
        list = view.findViewById(R.id.list);
        MyListViewAdapter adapter = new MyListViewAdapter(lists,getContext());
        list.setAdapter(adapter);*/

        return view;
    }

    @Override
    public void onClick(View v) {


    }




}
