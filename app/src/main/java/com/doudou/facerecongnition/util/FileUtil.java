package com.doudou.facerecongnition.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtil {
    public static byte[] getBytes(String filePath){
        byte[] data = null;
        try {
            FileInputStream fin = new FileInputStream(filePath);
            if (fin != null){
                data = new byte[fin.available()];
                fin.read(data);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
