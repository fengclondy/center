package cn.htd.promotion.cpc.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
    public static final String YMD = "yyyyMMdd";
    public static final String YYDDMMHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD_SLASH = "yyyy/MM/dd";
    public static final String YMD_DASH = "yyyy-MM-dd";
    public static final String YMD_DASH_WITH_TIME = "yyyy-MM-dd H:m";
    public static final String YDM_SLASH = "yyyy/dd/MM";
    public static final String YDM_DASH = "yyyy-dd-MM";
    public static final String HM = "HHmm";
    public static final String HM_COLON = "HH:mm";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static DateFormat dfInt = new SimpleDateFormat("yyyyMMdd");

    private static DateFormat dfIntYearMonth = new SimpleDateFormat("yyyyMM");

    private static DateFormat dfIntSS = new SimpleDateFormat("yyyyMMddHHmmss");

    private static DateFormat dfsss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    private static long time24Hour = 24 * 60 * 60 * 1000;

    private static long time9Days = 24 * 60 * 60 * 1000 * 9;

    private static final Map<String, DateFormat> DFS = new HashMap<String, DateFormat>();

    public static DateFormat getFormat(String pattern) {
        DateFormat format = DFS.get(pattern);
        if (format == null) {
            format = new SimpleDateFormat(pattern);
            DFS.put(pattern, format);
        }
        return format;
    }
    /*
     * 获取系统当前时间
     */
    public static Timestamp getSystemTime() {
        Date dt = new Date();
        String nowTime = df.format(dt);
        java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
        return buydate;
    }

    /*
     * 获取当前时间的九天前时间
     */
    public static Timestamp getDaysTime(int date) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.add(Calendar.DAY_OF_MONTH, date);
        String nowTime = df.format(now.getTime());
        java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
        return buydate;
    }

    /*
     * 获取当前时间的n分钟后的时间
     */
    public static Timestamp getMinutesLaterTime(int minutes) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.add(Calendar.MINUTE, minutes);
        String nowTime = df.format(now.getTime());
        java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
        return buydate;
    }

    /*
     * 获取三个月前时间
     */
    public static Timestamp getMonthTime(int month) {
        Date dt = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(dt);
        now.add(Calendar.MONTH, month);
        Date threeMonthAgoDate = now.getTime();
        String nowTime = df.format(threeMonthAgoDate);
        java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
        return buydate;
    }

    public static String getDay() {
        Date dt = new Date();
        String nowTime = dfInt.format(dt);
        return nowTime;
    }

    /*
     * 获取当前年月
     */
    public static String getYearMonth() {
        Date dt = new Date();
        String nowTime = dfIntYearMonth.format(dt);
        return nowTime;
    }

    // 获取当前日期-到秒
    public static String getDaySS() {
        Date dt = new Date();
        String nowTime = dfIntSS.format(dt);
        return nowTime;
    }

    // 判断时间是否超过24小时
    public static boolean judgmentDate(String date1, String date2) throws Exception {
        java.util.Date start = dfIntSS.parse(date1);
        java.util.Date end = dfIntSS.parse(date2);
        long cha = end.getTime() - start.getTime();
        double result = cha * 1.0 / (1000 * 60 * 60);
        System.out.println(result);
        if (result <= 24) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean judgment24Date(String date1) throws Exception {
        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        String nowday = year + month + day;
        LOGGER.info("页面传进来的时间内容:" + date1 + " 当前时间:" + nowday);
        if (date1.substring(0, 8).equals(nowday)) {
            return true;
        }
        return false;
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static String getCurrentDate() {
        Calendar now = Calendar.getInstance();
        Date d = new Date();
        String dateNowStr = dfsss.format(d);
        return dateNowStr;
    }

    // time 格式 2017-03-22 11:28:20
    public static Date getDateBySpecificDate(String time) {
        Date date = null;
        try {
            date = df.parse(time);
        } catch (ParseException e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            LOGGER.error("MessageId:{} 调用方法DateUtil.getDateBySpecificDate出现异常{}", w.toString());
        }
        return date;
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

    /**
     * 根据特定时间取得当前日期的某时某分某秒
     *
     * @param targetTime
     * @return
     */
    public static Date getNowDateSpecifiedTime(Date targetTime) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        cal.setTime(targetTime);
        cal.set(year, month, day);
        return cal.getTime();
    }

    /*
     * 将Date转成java.sql.Timestamp 入库用
     */
    public static Timestamp getDaysSqlTime(Date date) {
        String nowTime = df.format(date.getTime());
        java.sql.Timestamp buydate = java.sql.Timestamp.valueOf(nowTime);
        return buydate;
    }

    // 获取上月第一天的日期
    public static String getLastMonthFirstDay() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        String firstday;
        // 获取前月的第一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        firstday = format.format(cale.getTime());
        return firstday;
    }

    // 获取上月的最后一天
    public static String getLastMonthLastDay() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cale = Calendar.getInstance();
        String lastDay;
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastDay = format.format(cale.getTime());
        return lastDay;
    }

    // 获取上月的日期
    public static String getLastMonth() {
        // 获取当月第一天和最后一天
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Calendar cale = Calendar.getInstance();
        String lastDay;
        // 获取前月的最后一天
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        lastDay = format.format(cale.getTime());
        return lastDay;
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return getFormat(YYDDMMHHMMSS).format(date);
    }

    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return getFormat(pattern).format(date);
    }

    public static void main(String[] args) throws Exception {
//		System.out.println(Integer.valueOf(getLastMonth()));
//		 String date1="20161129153852"; //
//		 System.out.println("20161201200101".substring(0,8));
//		 // String date2="20161116150201";
//		 // System.out.println(getCurrentDate());
//		 System.out.println(isValidDate("20170228"));
//		 try {
//		 if(judgment24Date(date1)){
//		 System.out.println("可用");
//		
//		 }else{
//		 System.out.println("已过期");
//		 }
//		 } catch (Exception e) {
//		 // TODO: handle exception
//		 }
    }
}
