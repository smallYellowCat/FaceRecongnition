package com.doudou.facerecongnition.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.util.MyHttpUtil;

import java.util.LinkedHashMap;

/**
*登录页
*@author 豆豆
*时间:
*/
public class LoginActivity extends Activity implements View.OnClickListener {

    private Button loginBt;
    private String userName;
    private String password;
    private EditText etUserName;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        loginBt = findViewById(R.id.btnLogin);

        loginBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        loginBt.setEnabled(false);
        userName = etUserName.getText().toString();
        password = etPassword.getText().toString();
        if (userName == null || password == null || userName.length() != 6 ||
                password.length() < 6 || password.length() > 16){
            Toast.makeText(this, "用户名或密码错误请检查！", Toast.LENGTH_LONG).show();
        }else {
            loginBt.setText("登陆中......");
            //组装参数
            LinkedHashMap param = new LinkedHashMap();
            param.put("userName", userName);
            param.put("password", password);
            //发起请求
            JSONObject jsonObject = MyHttpUtil.post("", param);
            if (jsonObject.get("code") == 0){

            }else {
                loginBt.setEnabled(true);
                Toast.makeText(this, "登录失败！", Toast.LENGTH_LONG).show();

            }
        }

    }
}
