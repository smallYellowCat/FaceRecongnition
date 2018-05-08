package com.doudou.facerecongnition.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.doudou.facerecongnition.R;

import static com.doudou.facerecongnition.activity.stack.ActivityController.getInstance;


public class BaseActivity extends AppCompatActivity {

    /**
     * True if the activity have a custom action bar via use the method {@link #initActionBar},
     * false otherwise.
     */
    protected boolean haveActionBar = false;

    // action bar components
    private TextView tvTitle;
    private TextView tvBack;
    private ImageView ivBack;
    private Button btnRight;
    private Button btnCenterLeft;
    private Button btnCenterRight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getInstance().removeActivity(this);
    }

    /**
     * Initial the action bar with a custom view and specific title.
     */
    protected void initActionBar(String title) {
        initActionBar();
        setActionBarTitle(title);
    }

    /**
     * Initial the action bar with a custom view.
     * 初始化action bar
     */
    protected void initActionBar() {
        ActionBar mActionBar = getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setCustomView(R.layout.action_bar);//添加自定义的view
            mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            mActionBar.setDisplayShowCustomEnabled(true);
            View view = mActionBar.getCustomView();
            Toolbar parent = (Toolbar) view.getParent(); //first get parent toolbar of current action bar
            parent.setContentInsetsAbsolute(0, 0); // set padding programmatically to 0dp

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvBack = (TextView) view.findViewById(R.id.tvBack);
            btnRight = (Button) view.findViewById(R.id.btnRight);
            btnCenterLeft = (Button) view.findViewById(R.id.btnCenterLeft);
            btnCenterRight = (Button) view.findViewById(R.id.btnCenterRight);
            ivBack = (ImageView) view.findViewById(R.id.ivBack);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tvBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            haveActionBar = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 在activity进入后台时关闭输入法
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            View view = getCurrentFocus();
            if (view != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * Set the back button click listener on action bar.
     * 设置action bar 回退按钮监听事件
     *
     * @param listener View.OnClickListener.
     */
    protected void setBackOnclick(View.OnClickListener listener) {
        if (haveActionBar) {
            ivBack.setOnClickListener(listener);
            tvBack.setOnClickListener(listener);
        }
    }
    
    /**
     * Set the action bar title.
     * 设置 action bar 标题
     *
     * @param title title content
     */
    protected void setActionBarTitle(String title) {
        if (haveActionBar && title != null) {
            if (title.length() > 6) {
                title = title.substring(0, 5);
                title += "..";
            }
            tvTitle.setText(title);
        }
    }

    /**
     * Set the action bar backText
     * 设置action bar 回退文本
     *
     * @param backText
     */
    protected void setBackText(String backText) {
        if (haveActionBar && backText != null) {
            tvBack.setText(backText);
        }
    }

    /**
     * Hide the back button on action bar.
     * 隐藏action bar 回退按钮
     *
     */
    protected void hideButtonBack() {
        if (haveActionBar) {
            ivBack.setVisibility(View.GONE);
        }
    }

    /**
     * show the right button on action bar.
     * 显示action bar 右边按钮
     */
    protected void showButtonRight(){
        if (haveActionBar) {
            btnRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hide the tight button on action bar
     * 隐藏action bar 右侧功能按钮
     *
     */
    protected void hideButtonRight() {
        if (haveActionBar) {
            btnRight.setVisibility(View.GONE);
        }
    }

    /**
     * hide the btnCenterLeft and btnCenterRight on action bar
     * 隐藏action bar中心通讯录团队和客户按钮
     *
     */
    protected void hideButtonCenter() {
        if (haveActionBar) {
            btnCenterLeft.setVisibility(View.GONE);
            btnCenterRight.setVisibility(View.GONE);
        }
    }

    /**
     * show the btnCenterLeft and btnCenterRight on action bar
     * 显示action bar中心通讯录团队和客户按钮
     */
    protected void showButtonCenter() {
        if (haveActionBar) {
            btnCenterLeft.setVisibility(View.VISIBLE);
            btnCenterRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * get the btnCenterLeft
     * 获得action bar中心通讯录团队按钮
     */
    protected  Button getButtonCenterLeft() {
        return btnCenterLeft;
    }

    /**
     * get the btnCenterRight
     * 获得action bar中心通讯录客户按钮
     */
    protected  Button getButtonCenterRight() {
        return btnCenterRight;
    }

    /**
     * get the btnright
     * 获得actiong bar右边的按钮
     */
    protected Button getButtonRight(){
        return btnRight;
    }

    /**
     * Set the tight button click listener on action bar.
     * 设置action bar 右侧功能按钮监听事件
     *
     * @param listener View.OnClickListener.
     */
    protected void setButtonRightOnclick(View.OnClickListener listener) {
        if (haveActionBar) {
            btnRight.setOnClickListener(listener);
        }
    }

    /**
     * Set the right button background on action bar.
     *设置action bar 右侧功能按钮背景
     * @param resId background resource id.
     */
    protected void setButtonRightBackground(int resId) {
        if (haveActionBar) {
            btnRight.setBackgroundResource(resId);
        }
    }

    /**
     * set the rihgt button background on action bar.
     * 设置action bar 右侧功能按钮文本
     * @param btnRightText
     */
    protected void setButtonRightText(String btnRightText) {
        if (haveActionBar) {
            btnRight.setText(btnRightText);
        }
    }
}
