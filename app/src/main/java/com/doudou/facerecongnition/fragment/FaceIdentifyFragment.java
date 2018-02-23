package com.doudou.facerecongnition.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.activity.CameraActivity;

/**
*
*@author 豆豆
*时间:
*/
public class FaceIdentifyFragment extends Fragment implements View.OnClickListener {

    private SurfaceView surfaceView;
    private Button bt_open_camera;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_identify, container, false);
        initView(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), CameraActivity.class);
        startActivity(intent);

    }

    private void initView(View view){
        surfaceView = view.findViewById(R.id.photo);
        bt_open_camera = view.findViewById(R.id.open_camera);
        bt_open_camera.setOnClickListener(this);
    }

    private void initFaceIndetity(Context context, String appID){

    }
}
