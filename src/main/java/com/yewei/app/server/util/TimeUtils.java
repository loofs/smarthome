package com.yewei.app.server.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2017/3/26.
 */
public class TimeUtils {

    /**
     * 获取时间某单位数字
     * @param calendar
     * @param timeUnit
     * @return
     */
    public static int getTimeNum(Calendar calendar, TimeUnit timeUnit) {
        int result;
        switch (timeUnit) {
            case DAYS:
                result = calendar.get(Calendar.DAY_OF_YEAR);
                break;
            case HOURS:
                result = calendar.get(Calendar.HOUR_OF_DAY);
                break;
            case MINUTES:
                result = calendar.get(Calendar.MINUTE);
                break;
            case SECONDS:
                result = calendar.get(Calendar.SECOND);
                break;
            case MILLISECONDS:
                result = calendar.get(Calendar.MILLISECOND);
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }
}
