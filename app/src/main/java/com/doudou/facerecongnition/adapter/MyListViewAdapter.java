package com.doudou.facerecongnition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.entity.KeTang;

import java.util.List;

/**
*自定义列表视图适配器
*@author 豆豆
*时间:
*/
public class MyListViewAdapter extends BaseAdapter {
    private List<KeTang> list;
    private Context context;

    public MyListViewAdapter(List<KeTang> list, Context context){
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.manager_list, parent,false);
            viewHolder = new ViewHolder();
            viewHolder.KT_Name = convertView.findViewById(R.id.KTName);
            viewHolder.startTime = convertView.findViewById(R.id.startTime);
            viewHolder.endTime = convertView.findViewById(R.id.endTime);
            viewHolder.detail = convertView.findViewById(R.id.detail);
            viewHolder.delete = convertView.findViewById(R.id.delete);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.KT_Name.setText(list.get(position).getKTName());
        viewHolder.startTime.setText("开始时间：" + list.get(position).getStartTime());
        viewHolder.endTime.setText("结束时间：" + list.get(position).getEndTime());

        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView KT_Name;
        TextView startTime;
        TextView endTime;
        TextView detail;
        TextView delete;
    }
}
