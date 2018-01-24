package com.doudou.facerecongnition.util;


import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
*网络操作工具类
*@author 豆豆
*时间:
*/
public class MyHttpUtil {

    private static final int CACHE_SIZE = 1024;

    /**
     * 发起get请求
     * @param path url
     * @return jsonObject
     * @throws IOException 可能发生的io异常
     */
    public static JSONObject get(String path) {
        JSONObject jsonData = null;
        HttpURLConnection connection = null;
        try {
            connection = getConnection(path, "GET", 10, false, null);
            jsonData = getJSONObject(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return jsonData;
    }


    /**
     * 发起post请求
     * @param path url
     * @param params
     * @return
     * @throws MalformedURLException
     */
    public static JSONObject post(String path, LinkedHashMap<String, Object> params) {
        JSONObject json = null;
        HttpURLConnection connection = null;
        try {
            connection = getConnection(path, "POST", 10, true, params);
            json = getJSONObject(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return json;
    }

    /**
     *获取httpurlconnection
     * @param path url
     * @param method 请求方法
     * @param timeOut 超时时间，单位s
     * @param haveParam 是否传参
     * @param params 参数表
     * @return 返回 connection
     * @throws IOException
     */
    private static HttpURLConnection getConnection(String path, String method, int timeOut, boolean haveParam, LinkedHashMap<String, Object> params) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(timeOut * 1024);
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setDoOutput(haveParam);
        connection.setDoInput(true);
        if (haveParam && params != null && params.size() > 0){
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            StringBuffer paramsStr = new StringBuffer();
            Iterator it = params.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                paramsStr.append(key).append("=").append(value).append("&");
            }

            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(paramsStr.substring(0, paramsStr.length()-1));
            osw.flush();
            osw.close();
        }
        return connection;
    }

    private static JSONObject getJSONObject(HttpURLConnection connection) throws IOException {
        JSONObject jsonData = null;
        connection.connect();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            System.out.println("============连接成功============");
            InputStream in = connection.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] data = new byte[CACHE_SIZE];
            int length = 0;
            while((length = in.read(data)) != -1){
                baos.write(data, 0, length);
            }
            in.close();
            jsonData = JSONObject.parseObject(baos.toString());
        }
        return jsonData;
    }


}
