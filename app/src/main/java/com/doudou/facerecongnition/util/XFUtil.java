package com.doudou.facerecongnition.util;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.doudou.facerecongnition.entity.User;
import com.iflytek.cloud.*;
import org.json.JSONException;
import org.json.JSONObject;

public class XFUtil {

    /**身份验证对象*/
    private IdentityVerifier mIdVerifier;

    private static String TAG = "XFUtil";

    private  Context context;

    public XFUtil(Context context, IdentityVerifier mIdVerifier){
        this.context = context;
        this.mIdVerifier = mIdVerifier;
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
                    Toast.makeText(context, "注册成功", Toast.LENGTH_LONG).show();
                    //showTip("注册成功");
                }else {
                    //showTip(new SpeechError(ret).getPlainDescription(true));
                    Toast.makeText(context, new SpeechError(ret).getPlainDescription(true), Toast.LENGTH_LONG).show();
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
            /*if (null != mProDialog) {
                mProDialog.dismiss();
            }

            showTip(error.getPlainDescription(true));*/
            Toast.makeText(context, error.getPlainDescription(true), Toast.LENGTH_LONG).show();
        }

    };


    /**
     * register face information
     * @param user 待注册用户
     * @return 成功：true， 失败：false
     */
    public  boolean enroll(User user){
        boolean result = false;

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
        //while( !isDataFinished ){
            // 写入数据，data为图片的二进制数据
            mIdVerifier.writeData("ifr", params.toString(), user.getImageData(), 0, user.getImageData().length );
        //}
        mIdVerifier.stopWrite("ifr");
        return result;
    }



    /**
     * 人脸验证监听器
     */
    private IdentityListener mVerifyListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            Log.d(TAG, result.getResultString());

            /*if (null != mProDialog) {
                mProDialog.dismiss();
            }*/

            try {
                JSONObject object = new JSONObject(result.getResultString());
                Log.d(TAG,"object is: "+object.toString());
                String decision = object.getString("decision");

                if ("accepted".equalsIgnoreCase(decision)) {
                    //showTip("通过验证");
                    Toast.makeText(context, "验证通过", Toast.LENGTH_LONG).show();
                } else {
                    //showTip("验证失败");
                    Toast.makeText(context, "验证失败", Toast.LENGTH_LONG).show();
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
            /*if (null != mProDialog) {
                mProDialog.dismiss();
            }

            showTip(error.getPlainDescription(true));*/
            Toast.makeText(context, error.getPlainDescription(true), Toast.LENGTH_LONG).show();
        }

    };

    /**
     * 人脸鉴别监听器
     */
    public IdentityListener mSearchListener = new IdentityListener() {
        @Override
        public void onResult(IdentityResult result, boolean isLast) {
            Log.d(TAG, result.getResultString());
        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };

    protected void handleResult(IdentityResult result) {
        if (null == result) {
            return;
        }

        try {
            String resultStr = result.getResultString();
            JSONObject resultJson = new JSONObject(resultStr);
            if(ErrorCode.SUCCESS == resultJson.getInt("ret"))
            {
                // 提示鉴别成功
                Toast.makeText(context, "识别成功", Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(context, "鉴别失败", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
