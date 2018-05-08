package com.doudou.facerecongnition.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
*网络操作工具类
*@author 豆豆
*时间:
*/
public class MyHttpUtil {

    private static final int CACHE_SIZE = 1024;
    private static final String TAG = "uploadFile";
    private static final int TIME_OUT = 10*10000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";

    private interface ContentType{
        String FORM = "application/x-www-form-urlencoded";
        String FILE = "multipart/form-data";
        String JSON = "application/json";
        String IMAGE = "image/*";
    }

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
            connection = getConnection(path, "GET", 10, false, null, ContentType.FORM);
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
            connection = getConnection(path, "POST", 10, true, params, ContentType.FORM);
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
     * 获取指定url的图片资源返回bitmap
     * @param url 图片路径
     * @return bitmap
     */
    public static Bitmap getBitmapByUrl(String url){
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            connection = getConnection(url, "GET", 10, false, null, ContentType.FORM);
            connection.connect();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null){
                connection.disconnect();
            }
        }
        return bitmap;
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
    private static HttpURLConnection getConnection(String path, String method, int timeOut, boolean haveParam, LinkedHashMap<String, Object> params, String contentType) throws IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setConnectTimeout(timeOut * 1024);
        connection.setRequestProperty("Charset", "UTF-8");
        connection.setDoOutput(haveParam);
        connection.setDoInput(true);
        connection.setRequestProperty("Content-Type", contentType);
        if (haveParam && params != null && params.size() > 0){
            StringBuffer paramsStr = new StringBuffer();
            Iterator it = params.entrySet().iterator();
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            while(it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                paramsStr.append(key).append("=").append(value).append("&");

            }

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



    public static JSONObject upload(String host, byte[] data, String fileParaName, String fileName, Map<String,String> params){
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";  
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        JSONObject json = null;
        try {
            URL url = new URL(host);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setRequestMethod("POST"); //请求方式  
            conn.setRequestProperty("Charset", CHARSET);//设置编码  
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            conn.setDoInput(true); //允许输入流  
            conn.setDoOutput(true); //允许输出流  
            conn.setUseCaches(false); //不允许使用缓存  

                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam=conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(LINE_END);
                if(params!=null){//根据格式，开始拼接文本参数  
                    for(Map.Entry<String,String> entry:params.entrySet()){
                        sb.append(PREFIX).append(BOUNDARY).append(LINE_END);//分界符  
                        sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
                        sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
                        sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
                        sb.append(LINE_END);
                        sb.append(entry.getValue());
                        sb.append(LINE_END);//换行！  
                    }
                }
                sb.append(PREFIX);//开始拼接文件参数  
                sb.append(BOUNDARY); sb.append(LINE_END);
                /**
                 * 这里重点注意： 
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
                 * filename是文件的名字，包含后缀名的 比如:abc.png 
                 */
                sb.append("Content-Disposition: form-data; name=\""+fileParaName+"\"; filename=\""+fileName+"\""+LINE_END);
                sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);
                sb.append(LINE_END);
                //写入文件数据  
                dos.write(sb.toString().getBytes());
                /*InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                long totalbytes = file.length();
                long curbytes = 0;
                Log.i("cky","total="+totalbytes);
                int len = 0;
                while((len=is.read(bytes))!=-1){
                    curbytes += len;
                    dos.write(bytes, 0, len);
                    listener.onProgress(curbytes,1.0d*curbytes/totalbytes);
                }
                is.close();*/
                dos.write(data);
                dos.write(LINE_END.getBytes());//一定还有换行
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();

                json = getJSONObject(conn);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }




}
