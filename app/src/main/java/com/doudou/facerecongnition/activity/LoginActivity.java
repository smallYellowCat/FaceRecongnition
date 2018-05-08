package com.doudou.facerecongnition.activity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.application.MyApplication;
import com.doudou.facerecongnition.constant.GlobalVar;
import com.doudou.facerecongnition.entity.Teacher;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
*登录页
*@author 豆豆
*时间:
*/
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private Button loginBt;
    private String userName;
    private String password;
    private EditText etUserName;
    private EditText etPassword;
    private TextView register;

    private long existTime = 0;
    private NotificationManager notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hide the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        loginBt = (Button) findViewById(R.id.btnLogin);
        register = (TextView) findViewById(R.id.tvRegister);

        loginBt.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                loginBt.setEnabled(false);
                userName = etUserName.getText().toString();
                password = etPassword.getText().toString();
                if (userName == null || password == null || userName.length() != 6 ||
                        password.length() < 6 || password.length() > 16){
                    Toast.makeText(this, "用户名或密码错误请检查！", Toast.LENGTH_SHORT).show();
                }else {
                    loginBt.setText("登陆中......");
                    loginBt.setEnabled(false);
                    //组装参数
                    LinkedHashMap param = new LinkedHashMap();
                    param.put("account", userName);
                    param.put("password", password);
                    //发起请求
                    final JSONObject jsonObject = new JSONObject(param);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                            GlobalVar.myServer + "/teacher/login", jsonObject, new Response.Listener<org.json.JSONObject>() {
                        @Override
                        public void onResponse(org.json.JSONObject response) {
                            try {
                                if (Integer.parseInt(response.get("code").toString()) == 0){
                                    //跳转主界面
                                    Intent intent = new Intent();
                                    intent.setClass(LoginActivity.this, MainActivity.class);

                                    Teacher teacher = new Teacher();
                                    JSONObject json = response.getJSONObject("data").getJSONObject("teacher");
                                    teacher.setId(Integer.parseInt(json.get("id").toString()));
                                    teacher.setAccount(json.get("account").toString());
                                    teacher.setName(json.get("name").toString());
                                    teacher.setPhoneNumber(json.get("phoneNumber").toString());
                                    if (json.has("imageName")  && json.has("imagePath")){
                                        teacher.setImageName(json.get("imageName").toString());
                                        teacher.setImagePath(json.get("imagePath").toString());
                                    }



                                    intent.putExtra("teacher", teacher);
                                    startActivity(intent);
                                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    loginBt.setEnabled(true);
                                    loginBt.setText("登陆");
                                    Toast.makeText(LoginActivity.this, "帐号密码错误！", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            loginBt.setEnabled(true);
                            loginBt.setText("登陆");
                            Toast.makeText(LoginActivity.this, "登录失败！网络错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                    jsonObjectRequest.setTag("login_post");
                    MyApplication.getHttpQuens().add(jsonObjectRequest);

                }
                loginBt.setEnabled(true);
                break;
            case R.id.tvRegister:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }


    }


    @Override
    protected void onStop() {
        super.onStop();
        MyApplication.getHttpQuens().cancelAll("login_post");
    }
}
