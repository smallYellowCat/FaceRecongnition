package com.doudou.facerecongnition.util;


import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
*base64工具类
*@author 豆豆
*时间:
*/
public class Base64Util {

    /**
     * convert bitmap to bytes
     * @param bitmap 位图t
     * @return byte array
     */
    public static byte[] bitmapToBytes(Bitmap bitmap){
        byte[] bytes = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null){
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                baos.flush();
                baos.close();

                bytes = baos.toByteArray();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (baos != null){
                try {
                    baos.flush();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bytes;
    }

    /**
     * bitmap to base64 string
     * @param bitmap 位图
     * @return base64 code string
     */
    public static String bitmapToBase64(Bitmap bitmap){
        String result = null;
        byte[] bytes = bitmapToBytes(bitmap);
        if (bytes != null && bytes.length > 0){
            result = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        return result;
    }

}
