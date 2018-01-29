package com.doudou.facerecongnition.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
*自定义fragment适配器
*@author 豆豆
*时间:
*/
public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list = null;
    private FragmentManager mFragmentManager;
    private OnReloadListener mListener;
    private LoadTeamListener loadTeamListener;


    public MyFragmentPageAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }


    /**
     * 重新设置页面内容
     * @param items
     */
    public void setPagerItems(ArrayList<Fragment> items) {
        if (items != null) {
            for (int i = 0; i < list.size(); i++) {
                mFragmentManager.beginTransaction().remove(list.get(i)).commit();
            }
            list = items;
        }
    }
    public void setOnReloadListener(OnReloadListener listener) {
        this.mListener = listener;
    }

    /**
     *回调接口
     */
    public interface OnReloadListener {
        void onReload();
    }

    /**
     * 加载团队通讯录的回调接口
     */
    public interface LoadTeamListener {
        void loadTeam();
    }
}
