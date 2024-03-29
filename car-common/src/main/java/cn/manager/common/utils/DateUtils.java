package cn.manager.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ljc
 * @version 0.1.0
 * @description: ok
 */
public class DateUtils {
    /**
     * ljc
     * 年月日转化成 -> 年月日 时分秒
     * example:
     * change( 2024-01-01 ) to ( 2024-01-01 23:59:59 )
     *
     * @param date
     * @return
     */
    public static Date getDateDetail(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 23);//时
        calendar.add(Calendar.MINUTE, 59);//时
        calendar.add(Calendar.SECOND, 59);//秒

        return calendar.getTime();
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 日期相减 获取毫秒
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Integer dateSecondDiff(Date beginDate, Date endDate) {
        //开始时间或结束时间为空 返回0L 毫秒
        if (beginDate == null || endDate == null) {
            return 0;
        }
        long date1ms = beginDate.getTime();
        long date2ms = endDate.getTime();
        return Math.toIntExact((date2ms - date1ms) / 1000);
    }

    /**
     * 转换秒->小时(只要超过一小时, 默认加一)
     * <p>
     * ps: 如果秒数超过一小时但未达到两小时，则按两小时计算
     * 例子: 一小时零一秒 默认俩小时
     */
    public static int convertSecondsToHours(int seconds) {
        int hours = seconds / 3600; // 计算基本小时数

        // 如果秒数超过一小时但未达到两小时，则按两小时计算
        if (seconds % 3600 > 0) {
            hours++;
        }

        return hours;
    }


}
