package cn.htd.marketcenter.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /*
     * 判断2个日期区间是否符合要求（没有交集）
     */
    public static Boolean isHaveNoIntersection(Date startTime, Date endTime, Date usedStartTime, Date usedEndTime) {
        if (startTime.getTime() >= usedEndTime.getTime()) {
            return true;
        } else if (endTime.getTime() <= usedStartTime.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得指定日期的前后天数的日期
     *
     * @param targetDt
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDay(Date targetDt, int specifiedDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDt);
        cal.add(Calendar.DATE, specifiedDay);
        return cal.getTime();
    }
}
