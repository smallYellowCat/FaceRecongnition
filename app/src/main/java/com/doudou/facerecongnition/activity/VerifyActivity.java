package com.doudou.facerecongnition.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.doudou.facerecongnition.R;
import com.iflytek.cloud.IdentityListener;
import com.iflytek.cloud.IdentityResult;
import com.iflytek.cloud.SpeechError;
import org.json.JSONException;
import org.json.JSONObject;

public class VerifyActivity extends BaseActivity {

    private static final String TAG = VerifyActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        initActionBar();
    }


    /**
     * 人脸验证监听器
     */
    private IdentityListener mVerifyListener = new IdentityListener() {

        @Override
        public void onResult(IdentityResult result, boolean islast) {
            Log.d(TAG, result.getResultString());

          /*  if (null != mProDialog) {
                mProDialog.dismiss();
            }*/

            try {
                JSONObject object = new JSONObject(result.getResultString());
                Log.d(TAG,"object is: "+object.toString());
                String decision = object.getString("decision");

                if ("accepted".equalsIgnoreCase(decision)) {
                    //showTip("通过验证");
                } else {
                    //showTip("验证失败");
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
           /* if (null != mProDialog) {
                //mProDialog.dismiss();
            }

            //showTip(error.getPlainDescription(true));*/
        }

    };
}
