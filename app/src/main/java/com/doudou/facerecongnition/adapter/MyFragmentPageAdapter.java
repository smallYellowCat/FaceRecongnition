package com.doudou.facerecongnition.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
*自定义fragment适配器
*@author 豆豆
*时间:
*/
public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {return null;

    }

    @Override
    public int getCount() {
        return 0;
    }
}
