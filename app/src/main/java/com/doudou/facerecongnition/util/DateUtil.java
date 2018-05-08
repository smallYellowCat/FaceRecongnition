package com.doudou.facerecongnition.util;

import android.support.annotation.NonNull;

import java.security.Timestamp;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateUtil {

    private DateUtil() {}

    /**
     * 获取当前时间字符串，时间格式：yyyy-MM-dd HH:mm:ss
     *
     * @return string of date
     */
    public static String getCurrentDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 将时间戳转换成时间字符串
     *
     * @param timestamp 时间戳，以毫秒为单位
     * @return 默认时间字符串格式：yyyy-MM-dd HH:mm:ss
     */
    public static String timestampToString(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dateFormat.format(date);
    }

    /**
     * 根据指定的时间格式，将时间戳转换成时间字符串
     *
     * @param timestamp  时间戳，以毫秒为单位
     * @param dateFormat 时间格式
     * @return 时间字符串
     */
    public static String timestampToString(long timestamp, @NonNull SimpleDateFormat dateFormat) {
        Date date = new Date(timestamp);
        return dateFormat.format(date);
    }

    /**
     * 将字符串转换成时间戳
     *
     * @param strDate 时间字符串，时间格式必须为：yyyy-MM-dd HH:mm:ss
     * @return 时间戳，以毫秒为单位，如果转换失败则返回-1
     */
    public static long StringToTimestamp(@NonNull String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }

    /**
     * 根据指定的时间格式将字符串转换成时间戳
     *
     * @param strDate    时间字符串
     * @param dateFormat 时间字符串对应的格式
     * @return 时间戳，以毫秒为单位，如果转换失败则返回-1
     */
    public static long StringToTimestamp(@NonNull String strDate, @NonNull SimpleDateFormat dateFormat) {
        Date date;
        try {
            date = dateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return date.getTime();
    }
}
