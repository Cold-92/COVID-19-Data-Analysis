package org.cold92.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String[] timeArr = simpleDateFormat.format(date).split("-");
        String time = timeArr[0] + "年" + timeArr[1] + "月" + timeArr[2] + "日";
        return time;
    }
}
