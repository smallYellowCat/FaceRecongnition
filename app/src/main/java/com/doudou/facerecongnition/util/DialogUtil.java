package com.doudou.facerecongnition.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.doudou.facerecongnition.R;


import java.util.Calendar;

public final class DialogUtil {

    private DialogUtil() {
    }

    /**
     * 弹出选择日期和具体时间的弹窗
     *
     * @param ctx    Context
     * @param tvDate 需要将日期显示在上面的TextView，时间的格式为 yyyy-MM-dd HH:mm
     */
    public static void showDateTimePickerDialog(Context ctx, final TextView tvDate) {
        String currentDate = DateUtil.getCurrentDate().substring(0, 16); // TODO 当前日期
        Calendar calendar = Calendar.getInstance();

        // 获得dialog布局
        View dialogView = LayoutInflater.from(ctx).inflate(R.layout.dialog_date_time, null);

        //　初始化选择日期dialog
        final DatePicker datePicker = (DatePicker) dialogView.findViewById(R.id.dpDate);
        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), null);

        // 初始化选择时间的dialog
        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.tpTime);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        // 创建dialog并显示
        new AlertDialog.Builder(ctx)
                .setTitle(currentDate)
                .setView(dialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // 将时间格式化为 yyyy-MM-dd HH:mm 的字符串
                        int month = datePicker.getMonth() + 1;
                        int dayOfMonth = datePicker.getDayOfMonth();
                        int hour = timePicker.getCurrentHour();
                        int minute = timePicker.getCurrentMinute();
                        String currentDate = datePicker.getYear() + "-" +
                                ((month < 10) ? ("0" + month) : month) + "-" +
                                ((dayOfMonth < 10) ? ("0" + dayOfMonth) : dayOfMonth) + " " +
                                ((hour < 10) ? ("0" + hour) : hour) + ":" +
                                ((minute < 10) ? ("0" + minute) : minute);

                        tvDate.setText(currentDate); // TODO 设置时间
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
