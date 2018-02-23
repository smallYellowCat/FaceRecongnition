package com.doudou.facerecongnition.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.doudou.facerecongnition.R;

public class SettingFragment extends Fragment implements View.OnClickListener {

    /**头像**/
    private ImageView headPhoto;
    /**帐号*/
    private TextView account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    private void initView(){

    }
}
