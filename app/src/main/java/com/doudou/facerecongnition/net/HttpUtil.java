package com.doudou.facerecongnition.net;

import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.util.MyHttpUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpUtil {
    /**
     * 表单提交文件和普通参数
     * @param url
     * @param data
     * @param fileParaName
     * @param fileName
     * @param para
     * @param listener
     */
    public static void post4file(final String url, final byte[] data, final String fileParaName, final String fileName, final LinkedHashMap<String, String> para, final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
               JSONObject json = MyHttpUtil.upload(url, data, fileParaName, fileName, para);
               if (json == null || Integer.parseInt(json.get("code").toString()) != 0){
                   listener.onError(new Exception("网络请求出错！"));
               }else {
                   listener.onFinish(json);
               }
            }
        }).start();

    }

    /**
     * 普通表单提交post
     * @param url
     * @param para
     * @param listener
     */
    public static void post(final String url, final LinkedHashMap<String, Object> para, final HttpCallBackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = MyHttpUtil.post(url, para);
                if (jsonObject == null || Integer.parseInt(jsonObject.get("code").toString()) != 0){
                    listener.onError(new Exception("请求出错"));
                }else {
                    listener.onFinish(jsonObject);
                }
            }
        }).start();
    }
}
