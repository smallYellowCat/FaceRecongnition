package com.doudou.facerecongnition.activity;

import android.content.pm.PackageManager;
import android.graphics.*;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.doudou.facerecongnition.R;
import com.doudou.facerecongnition.util.Base64Util;
import com.doudou.facerecongnition.util.CameraPreview;
import com.doudou.facerecongnition.util.MyCamera;
import com.doudou.facerecongnition.util.XFUtil;
import com.iflytek.cloud.*;

import java.io.ByteArrayOutputStream;


public class CameraActivity extends BaseActivity implements View.OnClickListener {

    private Camera mCamera;
    private CameraPreview mPreview;
    private static final int CAMERA_OK = 0;
    private ImageView imageView;
    private Button bt;
    private XFUtil xfUtil;
    /**身份验证对象*/
    private static  IdentityVerifier mIdVerifier;
    private final String TAG = "CameraActivity.class";
    private boolean flag  = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initActionBar();
        Setting.setShowLog(true);

        imageView = (ImageView) findViewById(R.id.iv);
        bt = (Button) findViewById(R.id.button_capture);
        bt.setOnClickListener(this);
        /*检查相机权限*/
        if (Build.VERSION.SDK_INT>22){
            if (ContextCompat.checkSelfPermission(CameraActivity.this,
                    android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                //先判断有没有权限 ，没有就在这里进行权限的申请
                ActivityCompat.requestPermissions(CameraActivity.this,
                        new String[]{android.Manifest.permission.CAMERA},CAMERA_OK);

            }else {
                //说明已经获取到摄像头权限了 想干嘛干嘛
                mCamera = MyCamera.getCameraInstance();
            }
        }else {
            //这个说明系统版本在6.0之下，不需要动态获取权限。
            mCamera = MyCamera.getCameraInstance();

        }

        // Create our Preview view and set it as the content of our activity.
        if (mCamera != null) {
            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }


        mIdVerifier = IdentityVerifier.createVerifier(CameraActivity.this, new InitListener() {
            @Override
            public void onInit(int errorCode) {
                if (ErrorCode.SUCCESS == errorCode) {
                    Toast.makeText(CameraActivity.this,"引擎初始化成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(CameraActivity.this,"引擎初始化失败，错误码：" + errorCode, Toast.LENGTH_LONG).show();
                }
            }
        });
        xfUtil = new XFUtil(this, mIdVerifier);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_OK:
                if (grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    //这里已经获取到了摄像头的权限，想干嘛干嘛了可以
                    mCamera = MyCamera.getCameraInstance();
                }else {
                    //这里是拒绝给APP摄像头权限，给个提示什么的说明一下都可以。
                    Toast.makeText(CameraActivity.this,"请手动打开相机权限",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }


    private void getPreViewImage() {

        mCamera.setPreviewCallback(new Camera.PreviewCallback(){

            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Size size = camera.getParameters().getPreviewSize();
                try{
                    YuvImage image = new YuvImage(data, ImageFormat.NV21, size.width, size.height, null);
                    if(image!=null){
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        image.compressToJpeg(new Rect(0, 0, size.width, size.height), 80, stream);

                        Bitmap bmp = BitmapFactory.decodeByteArray(stream.toByteArray(), 0, stream.size());

                        //**********************
                        //因为图片会放生旋转，因此要对图片进行旋转到和手机在一个方向上
                        rotateMyBitmap(bmp);
                        //**********************************

                        stream.close();
                    }
                }catch(Exception ex){
                    Log.e("Sys","Error:"+ex.getMessage());
                }
            }
        });
    }

    public void rotateMyBitmap(Bitmap bmp){
        //*****旋转一下
        Matrix matrix = new Matrix();
        matrix.postRotate(90);

        Bitmap bitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);

        Bitmap nbmp2 = Bitmap.createBitmap(bmp, 0,0, bmp.getWidth(),  bmp.getHeight(), matrix, true);

        //*******显示一下
        imageView.setImageBitmap(nbmp2);



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_capture:
                //getPreViewImage();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
                Toast.makeText(CameraActivity.this, "抓取一帧图像！", Toast.LENGTH_LONG).show();
                mCamera.setPreviewCallback(null);
                break;
            default: break;
        }
    }



    Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){

                case 1:
                    if(flag){
                        getPreViewImage();
                        //btnGetBuffer.setText("开始图片1");
                        handler.sendEmptyMessageDelayed(1, 30000);

                    }else{
                        mCamera.setPreviewCallback(null);
                    }
                    break;
                case 2:
                    mCamera.setPreviewCallback(null);
                    handler.sendEmptyMessageDelayed(2, 5000);
                    break ;


            }

        };
    };

}
