package com.doudou.facerecongnition.util;

/**
*字符串工具类
*@author 豆豆
*时间:
*/
public class StringUtil {
    public static boolean isEmpty(String string, int minLen, int maxLen){
        if (string == null){
            return true;
        }
        //去除空格, 制表符，换页符等空白字符
        string = string.replaceAll("\\s*", "");
        if (string .equals("") || string.length() == 0){
            return true;
        }
        if (minLen > 0 && maxLen <= 0 && string.length() < minLen){
            return true;
        }else if (minLen <= 0 && maxLen > 0 && string.length() > maxLen){
            return true;
        }else if (minLen == maxLen && minLen > 0 && string.length() != minLen){
            return true;
        }
        return false;
    }
}
