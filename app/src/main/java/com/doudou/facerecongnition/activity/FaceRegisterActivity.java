package com.doudou.facerecongnition.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.util.Base64Util;
import com.iflytek.cloud.*;
import org.json.JSONException;
import org.json.JSONObject;

public class FaceRegisterActivity extends BaseActivity implements View.OnClickListener {

    /**身份验证对象*/
    private static  IdentityVerifier mIdVerifier;
    private static String TAG = "FaceRegisterActivity.class";
    private EditText id;
    private ImageView face;
    private String authId;
    private Button button;
    private Button register;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_register);

        initActionBar();
        Intent intent = getIntent();

        button = (Button) findViewById(R.id.bt);
        id = (EditText) findViewById(R.id.id);
        face = (ImageView) findViewById(R.id.face);
        register = (Button) findViewById(R.id.register);

        /*user = new User();*/

        button.setOnClickListener(this);
        register.setOnClickListener(this);
        
        authId = intent.getStringExtra("ClassId");

        /*mIdVerifier = IdentityVerifier.createVerifier(FaceRegisterActivity.this, new InitListener() {
            @Override
            public void onInit(int errorCode) {
                if (ErrorCode.SUCCESS == errorCode) {
                    Toast.makeText(FaceRegisterActivity.this,"引擎初始化成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(FaceRegisterActivity.this,"引擎初始化失败，错误码：" + errorCode, Toast.LENGTH_LONG).show();
                }
            }
        });
*/

    }

    /**
     * 人脸注册监听器
     */
    private IdentityListener mEnrollListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            Log.d(TAG, result.getResultString());

            try {
                JSONObject object = new JSONObject(result.getResultString());
                int ret = object.getInt("ret");

                if (ErrorCode.SUCCESS == ret) {
                    //flag = false;
                    Toast.makeText(FaceRegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(FaceRegisterActivity.this, ""+new SpeechError(ret).getPlainDescription(true), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
        }

        @Override
        public void onError(SpeechError error) {
            //flag = true;
            Toast.makeText(FaceRegisterActivity.this, "注册失败: "+error.getPlainDescription(true), Toast.LENGTH_LONG).show();
        }

    };


   /* *//**
     * register face information
     * @return 成功：true， 失败：false
     *//*

      public boolean enroll(User user){
        boolean result = false;

        // 清空参数
        mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
        // 设置会话场景
        mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ifr");
        // 设置会话类型
        mIdVerifier.setParameter(SpeechConstant.MFV_SST, "enroll");
        // 设置用户id
        mIdVerifier.setParameter(SpeechConstant.AUTH_ID, user.getAuthId());
        // 设置监听器，开始会话
        mIdVerifier.startWorking(mEnrollListener);
        // 子业务执行参数，若无可以传空字符传
        StringBuffer params = new StringBuffer();
        // 写入数据，data为图片的二进制数据
        mIdVerifier.writeData("ifr", params.toString(), user.getImageData(), 0, user.getImageData().length );
        mIdVerifier.stopWrite("ifr");
        return result;
    }*/




    @Override
    public void onClick(View v) {

       /* switch (v.getId()){
            case R.id.bt:
                //点击了拍照按钮
                if (id.getText().toString() == null || id.getText().toString().length() != 4){
                    Toast.makeText(FaceRegisterActivity.this, "输入学号不能为空", Toast.LENGTH_LONG).show();
                }else {
                    authId = authId + id.getText().toString();
                    user.setAuthId(authId);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    }
                }
                break;
            case R.id.register:
                //点击注册按钮
                Toast.makeText(FaceRegisterActivity.this, "ddd", Toast.LENGTH_LONG).show();
                enroll(user);
                break;

        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //获取缩略图
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            face.setImageBitmap(imageBitmap);
            /*user.setImageData(Base64Util.bitmapToBytes(imageBitmap));*/
        }
    }
}
