package com.doudou.facerecongnition.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.adapter.MyFragmentPageAdapter;
import com.doudou.facerecongnition.constant.GlobalVar;
import com.doudou.facerecongnition.entity.Teacher;
import com.doudou.facerecongnition.fragment.FaceIdentifyFragment;
import com.doudou.facerecongnition.fragment.SettingFragment;
import com.doudou.facerecongnition.fragment.UserManagerFragment;
import com.doudou.facerecongnition.util.XFUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;

/**
*主界面
*@author 豆豆
*时间:
*/
public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    private NotificationManager notificationManager;//通知栏管理类

    private ViewPager mViewpager;
    private int tabImageRes[] = {R.drawable.sel_menu_customer_manager, R.drawable.sel_menu_assigned,
            R.drawable.sel_menu_address};

    private String tabText[] = {"管理", "识别", "设置"};

    private ArrayList<TextView> mTvTabItems = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private FaceIdentifyFragment faceFrg;
    private UserManagerFragment userFrg;
    private SettingFragment setFrg;

    private MyFragmentPageAdapter adapter;

    TextView tvFace;
    TextView tvUserManager;
    TextView tvSetting;

    Button btnManager;
    Button btnUser;
    Button btnSetting;

    Teacher teacher;

    private int currentItem=0;
    private int lasItem;

    private long existTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teacher = getManagerInfo(getIntent());
        initview();
    }

    private Teacher getManagerInfo(Intent intent){
        return (Teacher) intent.getSerializableExtra("teacher");
    }

    private void transferObjectToFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putSerializable("teacher", teacher);
        fragment.setArguments(bundle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction()== KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-existTime>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出应用",Toast.LENGTH_LONG).show();
                existTime = System.currentTimeMillis();
            } else {
                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                //finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initview() {
        initActionBar();
        tvFace = (TextView) findViewById(R.id.tvFace);
        tvUserManager = (TextView) findViewById(R.id.tvUserManager);
        tvSetting = (TextView) findViewById(R.id.tvSetting);

        mTvTabItems.add(tvFace);
        mTvTabItems.add(tvUserManager);
        mTvTabItems.add(tvSetting);

        this.initViewpager();

        for (int i=0; i<mTvTabItems.size(); i++){

            final TextView tvCurrentItem = mTvTabItems.get(i);
            Drawable drawable = getResources().getDrawable(tabImageRes[i]);
            int imageLenght = this.getResources().getDimensionPixelSize(R.dimen.spacing_large);
            drawable.setBounds(0, 0, imageLenght, imageLenght);
            tvCurrentItem.setCompoundDrawables(null,drawable,null,null);

            tvCurrentItem.setText(tabText[i]);
            tvCurrentItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (tvCurrentItem.getId()){
                        case R.id.tvFace:
                            mViewpager.setCurrentItem(0,false);
                            break;
                        case R.id.tvUserManager:
                            mViewpager.setCurrentItem(1,false);
                            break;
                        case R.id.tvSetting:
                            mViewpager.setCurrentItem(2,false);
                            break;
                    }
                }
            });


        }
        mTvTabItems.get(0).setSelected(true);
    }


    private  void initViewpager(){
        mViewpager = (ViewPager) findViewById(R.id.mViewpager);
        userFrg = new UserManagerFragment();
        faceFrg = new FaceIdentifyFragment();
        setFrg = new SettingFragment();

        transferObjectToFragment(userFrg);
        transferObjectToFragment(faceFrg);
        transferObjectToFragment(setFrg);

        mFragments.add(userFrg);
        mFragments.add(faceFrg);
        mFragments.add(setFrg);

        adapter = new MyFragmentPageAdapter(getSupportFragmentManager(),mFragments);

        mViewpager.setAdapter(adapter);
        mViewpager.setCurrentItem(0);
        mViewpager.addOnPageChangeListener(this);
        //设置缓存页面为3，即全部页面，非可暂时避免ViewPager预加载带来的问题
        mViewpager.setOffscreenPageLimit(3);
        switchFragment(0);

    }

    /**
     * 切换3个Fragment时的操作
     * @param index
     */

    private void switchFragment(int index) {
        clearSelection();
        switch (index) {
            case 0:
                this.setActionBarTitle("管理");
                this.hideButtonBack();
                this.hideButtonRight();
                this.hideButtonCenter();
                tvFace.setTextColor(Color.parseColor("#08c29d"));
                break;
            case 1:
                this.setActionBarTitle("识别");
                this.hideButtonBack();
                this.hideButtonRight();
                this.hideButtonCenter();
                tvUserManager.setTextColor(Color.parseColor("#08c29d"));
                break;
            case 2:
                this.setActionBarTitle("设置");
                this.hideButtonBack();
                this.hideButtonRight();
                this.hideButtonCenter();
                tvSetting.setTextColor(Color.parseColor("#08c29d"));
                break;

            default:
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        lasItem = currentItem;
        currentItem = position;
        switchFragment(currentItem);
        mTvTabItems.get(currentItem).setSelected(true);
        mTvTabItems.get(lasItem).setSelected(false);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    /**
     * 初始化下方导航textView字体颜色
     */
    private void clearSelection() {
        tvFace.setTextColor(Color.parseColor("#7a7b83"));
        tvUserManager.setTextColor(Color.parseColor("#7a7b83"));
        tvSetting.setTextColor(Color.parseColor("#7a7b83"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}
