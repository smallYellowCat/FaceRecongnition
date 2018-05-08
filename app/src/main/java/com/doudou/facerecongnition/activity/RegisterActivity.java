package com.doudou.facerecongnition.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.constant.GlobalVar;
import com.doudou.facerecongnition.entity.Teacher;
import com.doudou.facerecongnition.util.MyHttpUtil;
import com.doudou.facerecongnition.util.StringUtil;


import java.util.LinkedHashMap;

/**
*注册页
*@author 豆豆
*时间:
*/
public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText edUserAccount;
    private EditText edTrueName;
    private EditText edPhoneNum;
    private EditText edInviteCode;
    private EditText edPassword;

    private Button register;

    private String userAccount;
    private String trueName;
    private String phoneNum;
    private String inviteCode;
    private String password;
    LinkedHashMap<String, Object> para = new LinkedHashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initActionBar();

        edUserAccount = (EditText) findViewById(R.id.etUserAccount);
        edTrueName = (EditText) findViewById(R.id.etTrueName);
        edPhoneNum = (EditText) findViewById(R.id.etPhone);
        edInviteCode = (EditText) findViewById(R.id.etInviteCode);
        edPassword = (EditText) findViewById(R.id.etPassword);

        register = (Button) findViewById(R.id.btnRegister);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:

                register.setEnabled(false);

                userAccount = edUserAccount.getText().toString();
                trueName = edTrueName.getText().toString();
                phoneNum = edPhoneNum.getText().toString();
                inviteCode = edInviteCode.getText().toString();
                password = edPassword.getText().toString();

                if (StringUtil.isEmpty(userAccount, 6, 0)){
                    Toast.makeText(this, "用户名错误，请检查！", Toast.LENGTH_SHORT).show();
                } else if (StringUtil.isEmpty(trueName, 0, 0)){
                    Toast.makeText(this, "请填写真实姓名！", Toast.LENGTH_SHORT).show();
                } else if (StringUtil.isEmpty(phoneNum, 11, 11)){
                    Toast.makeText(this, "请填写11位手机号！", Toast.LENGTH_SHORT).show();
                } else if (StringUtil.isEmpty(password, 6, 16)){
                    Toast.makeText(this, "请输入6~16位的密码！", Toast.LENGTH_SHORT).show();
                } else if (StringUtil.isEmpty(inviteCode, 4, 4)){
                    Toast.makeText(this, "请填4位邀请码！", Toast.LENGTH_SHORT).show();
                }else {
                    register.setText("...注册中......");


                    para.put("invitationCode", inviteCode);
                    para.put("password", password);
                    para.put("phoneNumber", phoneNum);
                    para.put("name", trueName);
                    para.put("account", userAccount);
                    new RegisterRequest().execute(para);

                }
                register.setEnabled(true);
                break;
        }
    }

    class RegisterRequest extends AsyncTask{
        JSONObject json;
        @Override
        protected Object doInBackground(Object[] objects) {
            //发起请求
            json = MyHttpUtil.post(GlobalVar.myServer+"/teacher/register", para);
            if (Integer.parseInt(json.get("code").toString()) == 0){
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
                return true;
            }else {
                return false;
            }

        }

        @Override
        protected void onPostExecute(Object o) {
            if (o instanceof Boolean && Boolean.getBoolean(o.toString())){
                Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(RegisterActivity.this, json.get("msg").toString(), Toast.LENGTH_SHORT).show();
                register.setText("注册");
            }
        }
    }

}
