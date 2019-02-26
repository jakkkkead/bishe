package com.wage.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.util.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by hasee on 2018/4/28.
 */
public class TimeUtils {
    private static final Logger logger = LoggerFactory.getLogger(TimeUtils.class);
    private static HashMap<String, String> dateRegFormat = new HashMap<String, String>();
    public static ThreadLocal<DateFormat> formatter = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static ThreadLocal<DateFormat> formatter2 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("MM-dd");
        }
    };
    public static ThreadLocal<DateFormat> dayFormatter = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    private static ThreadLocal<DateFormat> formatter4 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };
    public static ThreadLocal<DateFormat> formatter5 = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("MM/dd");
        }
    };
    static{
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*$",
                "yyyy-MM-dd-HH-mm-ss");//2014年3月12日 13时5分34秒，2014-03-12 12:05:34，2014/3/12 12:5:34
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+$",
                "yyyy-MM-dd");
        //2014年3月12日 13时5分34秒，2014-03-12 12:05:34，2014/3/12 12:5:34
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{1}\\D+\\d{1}$",
                "yyyy-M-d");//2014-1-1,
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{1}$",
                "yyyy-M");//2014-1
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{2}\\D+\\d{1}$",
                "yyyy-MM-d");//2014-11-1
        dateRegFormat.put(
                "^\\d{4}\\D+\\d{1}\\D+\\d{2}$",
                "yyyy-M-dd");//2014-1-12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$",
                "yyyy-MM-dd-HH-mm");//2014-03-12 12:05
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}\\D+\\d{2}$",
                "yyyy-MM-dd-HH");//2014-03-12 12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}\\D+\\d{2}$", "yyyy-MM-dd");//2014-03-12
        dateRegFormat.put("^\\d{4}\\D+\\d{2}$", "yyyy-MM");//2014-03
        dateRegFormat.put("^\\d{4}$", "yyyy");//2014
        dateRegFormat.put("^\\d{14}$", "yyyyMMddHHmmss");//20140312120534
        dateRegFormat.put("^\\d{12}$", "yyyyMMddHHmm");//201403121205
        dateRegFormat.put("^\\d{10}$", "yyyyMMddHH");//2014031212
        dateRegFormat.put("^\\d{8}$", "yyyyMMdd");//20140312
        dateRegFormat.put("^\\d{6}$", "yyyyMM");//201403
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$",
                "yyyy-MM-dd-HH-mm-ss");//13:05:34 拼接当前日期
        dateRegFormat.put("^\\d{2}\\s*:\\s*\\d{2}$", "yyyy-MM-dd-HH-mm");//13:05 拼接当前日期
        dateRegFormat.put("^\\d{2}\\D+\\d{1,2}\\D+\\d{1,2}$", "yy-MM-dd");//14.10.18(年.月.日)
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}$", "yyyy-dd-MM");//30.12(日.月) 拼接当前年份
        dateRegFormat.put("^\\d{1,2}\\D+\\d{1,2}\\D+\\d{4}$", "dd-MM-yyyy");//12.21.2013(日.月.年)
    }
    public static Date toFormat(String dateStr){
        String curDate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateFormat formatter1 =new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter2;
        String dateReplace;
        Date strSuccess=null;
        try {
            for (String key : dateRegFormat.keySet()) {
                if (Pattern.compile(key).matcher(dateStr).matches()) {
                    formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
                    if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
                            || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//13:05:34 或 13:05 拼接当前日期
                        dateStr = curDate + "-" + dateStr;
                    } else if (key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//21.1 (日.月) 拼接当前年份
                        dateStr = curDate.substring(0, 4) + "-" + dateStr;
                    }
                    dateReplace = dateStr.replaceAll("\\D+", "-");
                    // System.out.println(dateRegExpArr[i]);
                    strSuccess=formatter2.parse(dateReplace);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("-----------------日期格式无效:"+dateStr);
        }
        return strSuccess;
    }


    public static String getWeekFirstDay(Date dateStr){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        int d = 0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        Date time = cal.getTime();
        return dayFormatter.get().format(time);
    }
    public static String getWeekLastDay(Date dateStr){
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateStr);
        int d = 0;
        if(cal.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_WEEK, d);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date time = cal.getTime();
        return dayFormatter.get().format(time);
    }
    public static String getWeekFirstDay(String dateStr){
        Date date = toFormat(dateStr);
        return getWeekFirstDay(date);
    }
    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isEffectiveDate(Date nowTime, String startTime, String endTime){
        return TimeUtils.isEffectiveDate(nowTime,TimeUtils.toFormat(startTime),TimeUtils.toFormat(endTime));
    }
    public static String getWeekLastDay(String dateStr){
        Date date = toFormat(dateStr);
        return getWeekLastDay(date);
    }
    public static String getWeekFirstDay(int year,int week){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year );
        //设置周
        cal.set(Calendar.WEEK_OF_YEAR, week);
        //设置该周第一天为星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        //格式化日期
        String format = formatter5.get().format(cal.getTime());
        return format;
    }
    public static String getWeekLastDay(int year,int week){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,year );
        //设置周
        cal.set(Calendar.WEEK_OF_YEAR, week);
        //设置该周第一天为星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        //格式化日期
        return formatter5.get().format(cal.getTime());
    }

    public static String formatDate(String dateStr){
        String curDate =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DateFormat formatter1 =new SimpleDateFormat("yyyy-MM-dd");
        DateFormat formatter2;
        String dateReplace;
        String strSuccess=null;
        try {
            for (String key : dateRegFormat.keySet()) {
                if (Pattern.compile(key).matcher(dateStr).matches()) {
                    formatter2 = new SimpleDateFormat(dateRegFormat.get(key));
                    if (key.equals("^\\d{2}\\s*:\\s*\\d{2}\\s*:\\s*\\d{2}$")
                            || key.equals("^\\d{2}\\s*:\\s*\\d{2}$")) {//13:05:34 或 13:05 拼接当前日期
                        dateStr = curDate + "-" + dateStr;
                    } else if (key.equals("^\\d{1,2}\\D+\\d{1,2}$")) {//21.1 (日.月) 拼接当前年份
                        dateStr = curDate.substring(0, 4) + "-" + dateStr;
                    }
                    dateReplace = dateStr.replaceAll("\\D+", "-");
                    // System.out.println(dateRegExpArr[i]);
                    strSuccess = formatter1.format(formatter2.parse(dateReplace));
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("-----------------日期格式无效:"+dateStr);
        }
        return strSuccess;
    }
    public static Integer getYear(String date){
        Date tmp = toFormat(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmp);
        int i = calendar.get(Calendar.YEAR);
        return i;
    }
    public static Integer getDay(String date){
        Date tmp = toFormat(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmp);
        int i = calendar.get(Calendar.DAY_OF_MONTH);
        return i;
    }
    public static String getDateTime(Date date){
        return dayFormatter.get().format(date);
    }
    public static String getDateTimeComplete(Date date){
        return formatter.get().format(date);
    }
    public static String getNowDateString(){
        return formatter4.get().format(new Date());
    }
    public static String getAddDate(String date,int addDay){
        Date tmp = toFormat(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmp);
        calendar.add(Calendar.DAY_OF_MONTH,addDay);
        Date time = calendar.getTime();
        return dayFormatter.get().format(time);
    }
    public static Integer getMonth(String date){
        Date tmp = toFormat(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tmp);
        int i = calendar.get(Calendar.MONTH)+1;

        return i;
    }
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }
    public static  String getStringDate() {
        Date currentTime = new Date();
        String dateString = formatter.get().format(currentTime);
        return dateString;
    }

    public static  String getStringDate3() {
        Date currentTime = new Date();
        String dateString = dayFormatter.get().format(currentTime);
        return dateString;
    }
    public static long getTimeDifferenceMinute(Date start,Date end){
        long endMinute = end.getTime() / 1000 / 60;
        long startMinute=start.getTime()/1000/60;
        return endMinute-startMinute;
    }

    public static String getFirstDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dayFormatter.get().format(calendar.getTime());
    }

    /**
     * 获取上个月 30 号 或者31号
     * @param date
     * @return
     */
    public static String getLastDayOfLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dayFormatter.get().format(calendar.getTime());
    }

    public static String getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dayFormatter.get().format(calendar.getTime());
    }
    public static Integer getWeekOfYear(String date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toFormat(date));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //第几周
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        return week;
    }
    public static Integer getMonthOfYear(String date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toFormat(date));

        int month = calendar.get(Calendar.MONTH)+1;
        return month;
    }
    public static String getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dayFormatter.get().format(calendar.getTime());
    }
    public static String getFirstMinDayOfMonth(String date) {
        Date date1 = toFormat(date);
        return getFirstDayOfMonth(date1);
    }
    public static String getLastMaxDayOfMonth(String date) {
        Date date1 = toFormat(date);
        return getLastDayOfMonth(date1);
    }
    public static String getFirstMinDayOfLastMonth(String date) {
        Date date1 = toFormat(date);
        return getFirstDayOfLastMonth(date1);
    }
    public static String getLastMaxDayOfLastMonth(String date) {
        Date date1 = toFormat(date);
        return getLastDayOfLastMonth(date1);
    }

    public static boolean isToday(Date time){
        if(time==null){
            return false;
        }
        String nowDay = getStringDate3();
        String format = dayFormatter.get().format(time);
        if(nowDay.equals(format)){
            return true;
        }
        return false;
    }

    /**
	 * 获取指定日期的最后时刻（如果是今天则是此刻，如果不是，则是yyyy-MM-dd 23:59:59）
	 * @param day yyyy-MM-dd
	 * @return
	 */
	public static String getTheDayLastTime(String day){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat tsdfNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat tsdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		try {
			Date now = new Date();
			String nowStr = sdf.format(now);
			if(nowStr.equals(day)){
				return tsdfNow.format(now);
			}else{
				Date date = sdf.parse(day);
				return tsdf.format(date);
			}
		} catch (ParseException e) {
			 System.err.println("-----------------getTheDayLastTime异常:"+e.getMessage());
		}
		return null;
	}



	/**
	 * 获取指定日期作为最后一天的共N周的周数
	 * @param endDay yyyy-MM-dd
	 * @param weekNum N
	 * @return [第N周,第N+1周,第N+2周]
	 */
	public static String[] getWeekArray(String endDay, int weekNum) {
		Calendar instance = Calendar.getInstance();
		instance.setFirstDayOfWeek(Calendar.MONDAY);
		String[] resArray = new String[weekNum];
		int minusNum = -(weekNum - 1);
		Date endDate = toFormat(endDay);
		for(int i=1; i < weekNum; i++) {
			instance.setTime(endDate);
			instance.add(Calendar.WEEK_OF_YEAR, minusNum);
			resArray[i-1] = "第" + instance.get(Calendar.WEEK_OF_YEAR) + "周";
			minusNum++;
		}
		instance.setTime(endDate);
		resArray[weekNum-1] = "第" + instance.get(Calendar.WEEK_OF_YEAR) + "周";
		return resArray;
	}

	public static String[] getWeekDateArray(String endDay, int weekNum) {
		Calendar instance = Calendar.getInstance();
		instance.setFirstDayOfWeek(Calendar.MONDAY);
		String[] resArray = new String[weekNum];
		int minusNum = -(weekNum - 1);
		Date endDate = toFormat(endDay);
		for (int i = 1; i < weekNum; i++) {
			instance.setTime(endDate);
			instance.add(Calendar.WEEK_OF_YEAR, minusNum);
			int d = 0;
	        if(instance.get(Calendar.DAY_OF_WEEK)==1){
	            d = -6;
	        }else{
	            d = 2-instance.get(Calendar.DAY_OF_WEEK);
	        }
	        instance.add(Calendar.DAY_OF_WEEK, d);
	        String mondayStr = formatter5.get().format(instance.getTime());
	        instance.add(Calendar.DAY_OF_WEEK, 6);
	        String sundayStr = formatter5.get().format(instance.getTime());
	        resArray[i-1] = mondayStr + '-' + sundayStr;
			minusNum++;
		}
		String lastDay =  formatter5.get().format(endDate);
		instance.setTime(endDate);
		int d = 0;
        if(instance.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-instance.get(Calendar.DAY_OF_WEEK);
        }
        instance.add(Calendar.DAY_OF_WEEK, d);
        String mondayStr = formatter5.get().format(instance.getTime());
        resArray[weekNum-1] = mondayStr + '-' + lastDay;
        return resArray;
	}

	/**
	 * 获取指定日期作为最后一天的共N月的月数
	 * @param endDay yyyy-MM-dd
	 * @param monthNum N
	 * @return [N月,N+1月,N+2月]
	 */
	public static String[] getMonthArray(String endDay, int monthNum) {
		Calendar instance = Calendar.getInstance();
		String[] resArray = new String[monthNum];
		int minusNum = -(monthNum - 1);
		Date endDate = toFormat(endDay);
		for(int i=1; i < monthNum; i++) {
			instance.setTime(endDate);
			instance.add(Calendar.MONTH, minusNum);
			resArray[i-1] = (instance.get(Calendar.MONTH) + 1) + "月";
			minusNum++;
		}
		instance.setTime(endDate);
		resArray[monthNum-1] = (instance.get(Calendar.MONTH) + 1) + "月";
		return resArray;
	}

	/**
	 * 获取指定日期间隔N周的星期一日期
	 * @param theDay yyyy-MM-dd
	 * @param n 间隔
	 * @return yyyy-MM-dd
	 */
	public static String getIntervalNWeekMonday(String theDay, int n) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(toFormat(theDay));
        instance.add(Calendar.WEEK_OF_YEAR, n);
        int d = 0;
        if(instance.get(Calendar.DAY_OF_WEEK)==1){
            d = -6;
        }else{
            d = 2-instance.get(Calendar.DAY_OF_WEEK);
        }
        instance.add(Calendar.DAY_OF_WEEK, d);
        Date time = instance.getTime();
        return dayFormatter.get().format(time);
	}

	/**
	 * 获取指定日期间隔N月的1号日期
	 * @param theDay yyyy-MM-dd
	 * @param n 间隔
	 * @return yyyy-MM-dd
	 */
	public static String getIntervalNMonthFirstDay(String theDay, int n) {
		Calendar instance = Calendar.getInstance();
        instance.setTime(toFormat(theDay));
        instance.add(Calendar.MONTH, n);
        instance.set(Calendar.DAY_OF_MONTH, instance.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dayFormatter.get().format(instance.getTime());
	}




    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekStrOfDate(Date date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0){
            w = 0;
        }
        return weekDays[w];
    }

    public static List<String>  getBetweenDays(String startDate,String endDate){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            if(endDate.length() > 10){
            endDate = endDate.substring(0,10) + " 00:00:00";
            }
            Date start = toFormat(startDate);
            Date end = toFormat(endDate);
            List<String> dates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while (start.before(end)){
                dates.add(simpleDateFormat.format(start));
                calendar.add(Calendar.DAY_OF_YEAR,1);
                start = calendar.getTime();
            }
            dates.add(simpleDateFormat.format(end));
            return dates;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return null;
    }
    //获取起始日期在内的每一天
    public static List<String>  getBetweenDays(Date start,Date end){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<String> dates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while (start.before(end)){
                dates.add(simpleDateFormat.format(start));
                calendar.add(Calendar.DAY_OF_YEAR,1);
                start = calendar.getTime();
            }
            dates.add(simpleDateFormat.format(end));
            return dates;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return null;
    }

    public static String getDayOfWeekString(String format,int dayOfWeek){
        switch (dayOfWeek) {
            case 1:
                format = format + "\n周日";
                break;
            case 2:
                format = format + "\n周一";
                break;
            case 3:
                format = format + "\n周二";
                break;
            case 4:
                format = format + "\n周三";
                break;
            case 5:
                format = format + "\n周四";
                break;
            case 6:
                format = format + "\n周五";
                break;
            case 7:
                format = format + "\n周六";
                break;
        }
        return format;
    }

    public static String setDefaultEndDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        String end = simpleDateFormat.format(endDate)+" 23:59:59";
        return end;
    }

    public static String setDefaultStartDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR,-6);
        Date startDate = calendar.getTime();
        String start = simpleDateFormat.format(startDate)+" 00:00:00";
        return start;
    }

    public static String setDefaultStartDate(String end){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = null;
		try {
			endDate = simpleDateFormat.parse(end);
		} catch (ParseException e) {
			logger.info("根据截止日期获取起始日期时间转换异常[{}]",e.getMessage());
		}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.DAY_OF_YEAR,-6);
        Date startDate = calendar.getTime();
        String start = simpleDateFormat.format(startDate)+" 00:00:00";
        return start;
    }

    public static String setDefaultEndDate(String start){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
		try {
			startDate = simpleDateFormat.parse(start);
		} catch (ParseException e) {
			logger.info("根据起始日期获取截止日期时间转换异常[{}]",e.getMessage());
		}
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR,+6);
        Date endDate = calendar.getTime();
        String end = simpleDateFormat.format(endDate)+" 23:59:59";
        return end;
    }


    public static String setMonthDefaultStartDate(){
    	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
         Date endDate = new Date();
         String start = simpleDateFormat.format(endDate)+"-01 00:00:00";
         return start;
    }

    public static String setYearDefaultStartDate(){
   	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date endDate = new Date();
        String start = simpleDateFormat.format(endDate)+"-01-01 00:00:00";
        return start;
   }

    public static String setMonthDefaultStartDate(String end){
      	String start = end.substring(0,7)+"-01 00:00:00";
        return start;
      }

    public static String setMonthDefaultEndDate(String start){
     	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
     	 String tmp = start.substring(0,7);
     	 String tmp2 = tmp + "-01";
     	 Date startDate = null;
     	 Calendar calendar = Calendar.getInstance();
     	 try {
			startDate = simpleDateFormat.parse(tmp2);
		} catch (ParseException e) {
			logger.info("时间转换异常[{}]",e.getMessage());
		}
     	 //当月1号
     	 calendar.setTime(startDate);
     	 //下月1号
     	 calendar.add(Calendar.MONTH, 1);
     	 //当月最后一天
     	 calendar.add(Calendar.DAY_OF_YEAR, -1);
         Date time = calendar.getTime();
         String format = simpleDateFormat.format(time);
         format = format + " 23:59:59";
         return format;
     }
    /**
     * 得到当前日期在本周第几天
     */
    public static int getDayInWeek(String date){
    	Calendar c=Calendar.getInstance();
    	Date date2 = toFormat(date);
    	c.setTime(date2);
    	int wday = c.get(c.DAY_OF_WEEK) - 1;
		if (wday == 0) {
			wday = 7;
		}
		return wday;
    }
    /**
     * 获取当前日期在本月的第几天
     * @param date
     * @return
     */
    public static int getDayInMonth(String date){
    	Calendar c=Calendar.getInstance();
    	Date date2 = toFormat(date);
    	c.setTime(date2);
    	int day=c.get(c.DAY_OF_MONTH);
		return day;
    }
    /**
     * 获取当前日期在本年的第几天
     * @param date
     * @return
     */
    public static int getDayInYear(String date){
    	Calendar c=Calendar.getInstance();
    	Date date2 = toFormat(date);
    	c.setTime(date2);
    	int day=c.get(c.DAY_OF_YEAR);
		return day;
    }
    /**
     * 获取当前日期在本月的最大天数
     * @param date
     * @return
     */
    public static int getTotalMonthDay(String date){
    	Calendar c=Calendar.getInstance();
    	Date date2 = toFormat(date);
    	c.setTime(date2);
    	int day=c.getActualMaximum(c.DAY_OF_MONTH);
		return day;
    }
    /**
     * 获取当前日期在本年最大天数
     * @param date
     * @return
     */
    public static int getTotalYearDay(String date){
    	Calendar c=Calendar.getInstance();
    	Date date2 = toFormat(date);
    	c.setTime(date2);
    	int day=c.getActualMaximum(c.DAY_OF_YEAR);
		return day;
    }
    /**
     * 返回本日的开始时间，如2018-11-11 00:00:00
     * @param date
     * @return
     */
    public static String getTheBeginTimeOfDay(String date){
    	Date date1 = toFormat(date);
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	String beginDay=sdf.format(date1);
    	return beginDay;
    }
    /**
     * 返回距离日期的第几周同期日期，格式为2018-12-18
     * @param date
     * @param i
     * @return
     */
    public static String getAddWeek(String date,int i){
    	Calendar c=Calendar.getInstance();
    	Date date1 = toFormat(date);
    	c.setTime(date1);
    	c.add(Calendar.WEEK_OF_YEAR, i);
    	return dayFormatter.get().format(c.getTime());
    }
    /**
     * 返回距离日期的第几月同期日期，格式为2018-12-18
     * @param date
     * @param i
     * @return
     */
    public static String getAddMonth(String date,int i){
    	Calendar c=Calendar.getInstance();
    	Date date1 = toFormat(date);
    	c.setTime(date1);
    	c.add(Calendar.MONTH, i);
    	return dayFormatter.get().format(c.getTime());
    }







    /**
     * @Description: 月报初始化日期
     * @param:
     * @date: 2018/12/20 9:01
     */





    /**

     * @Description: 周报上月同期时间
     * @param:
     * @date: 2018/12/18 17:49
     */
    /**
     * 获取日期上月的同一天，yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getLastMonthSameDay(String date){
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	 Calendar calendar = Calendar.getInstance();
         try {
			calendar.setTime(sf.parse(date));
			calendar.add(Calendar.MONTH, -1);
			return dayFormatter.get().format(calendar.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return date;
		}
    }

    /**
     * @Description: 周/月报去年同期时间
     * @param:
     * @date: 2018/12/18 17:49
     */

    public static Date getLastDayOfMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date result = calendar.getTime();
        return result;
    }
    /**
     * 获取日期所在年份的第一天
     * @param date
     * @return
     */
    public static String getFirstDayOfYear(String date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Integer year = getYear(date);
        calendar.set(Calendar.YEAR, year);
        return dayFormatter.get().format(calendar.getTime());
    }
    /**
     * 获取日期所在年份的最后一天
     * @param date
     * @return
     */
    public static String getLastDayOfYear(String date){
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Integer year = getYear(date);
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return dayFormatter.get().format(calendar.getTime());
    }

    /**
     * @Description: 获取当前日期所在年的第一天和最后一天
     * @param:
     * @date: 2018/12/21 8:49
     */

    /**
     * @Description: 将起始日期分别加上时分秒
     * @param:
     * @date: 2018/12/21 8:57
     */

    /**
     * @Description: 周报上周起始时间
     * @param:
     * @date: 2018/12/18 17:49
     */
    public static String getLastWeekStartDate(String start){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String format = "";
        try {
            date = simpleDateFormat.parse(start);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR,-7);
            Date lastWeekStart = calendar.getTime();
            format = simpleDateFormat.format(lastWeekStart);
        }catch (Exception e){
            logger.info("获取周报上周起始时间失败[{}]",e.getMessage());
        }
        return format;
    }




    /**
     * @Description: 获取起始日期在内的每一月
     * @param:
     * @date: 2018/12/21 16:01
     */
    public static List<String> getBetweenMonth(String startDate, String endDate){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Date start = simpleDateFormat.parse(startDate);
            Date end = simpleDateFormat.parse(endDate);
            List<String> dates = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            while (start.before(end) || start.equals(end)){
                dates.add(simpleDateFormat.format(start));
                calendar.add(Calendar.MONTH,1);
                start = calendar.getTime();
            }
            return dates;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return null;
    }





    /**
     * 获取某年同一天
     * @param date
     * @return
     */
    public static String getTheDayOfSomeYear(String date , int i){
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(toFormat(date));
    	Integer year = getYear(date);
    	calendar.set(Calendar.YEAR, year + i);
    	return dayFormatter.get().format(calendar.getTime());
    }

    /**
     * @Description: 将"yyyy-MM-dd"字符串格式幻化成日期格式
     * @param:
     * @date: 2018/12/26 20:18
     */
    public static Date parseDate(String date){
        Date result = null;
        try {
            result = dayFormatter.get().parse(date);
        }catch (Exception e){
            logger.info("将yyyy-MM-dd字符串格式转化成日期格式失败[{}]",e.getMessage());
        }
        return result;
    }

    /**
     * @Description: 将日期格式转化成"yyyy-MM-dd"字符串格式
     * @param:
     * @date: 2018/12/26 20:18
     */
    public static String formatDate(Date date){
        return dayFormatter.get().format(date);
    }


    /**
     * @Description: 获取本月第一天
     * @param:
     * @date: 2018/12/27 11:06
     */
    public static String getMonthFirstDay(String date){
        String monthFirstDay = date.substring(0,8)+"01";
        return monthFirstDay;
    }


    /**
     * @Description: 获取本月最后一天
     * @param:
     * @date: 2018/12/27 11:06
     */
    public static String getMonthLastDay(String date){
        String monthFirstDay = date.substring(0,8)+"01";
        Date monthFirstDate = parseDate(monthFirstDay);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(monthFirstDate);
        calendar.add(Calendar.MONTH,1);
        calendar.add(Calendar.DAY_OF_YEAR,-1);
        Date monthLastDate = calendar.getTime();
        String monthLastDay = formatDate(monthLastDate);
        return monthLastDay;
    }

    /**
     * @Description: 获取今年第一天
     * @param:
     * @date: 2018/12/27 11:06
     */
    public static String getYearFirstDay(String date){
        String yearFirstDay = date.substring(0,5)+"01-01";
        return yearFirstDay;
    }

    /**
     * @Description: 获取今年最后一天
     * @param:
     * @date: 2018/12/27 11:06
     */
    public static String getYearLastDay(String date){
        String yearLastDay = date.substring(0,5)+"12-31";
        return yearLastDay;
    }

    /**
     * @Description: 判断当前日期是否在本周,是为true
     * @param:
     * @date: 2018/12/27 17:10
     */
    public static boolean isThisWeek(String date){
        String weekFirstDay = getWeekFirstDay(new Date());
        String weekLastDay = getWeekLastDay(new Date());
        if (date.compareTo(weekFirstDay)>=0 && date.compareTo(weekLastDay)<=0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Description: 判断当前日期是否在本月,是为true
     * @param:
     * @date: 2018/12/27 17:10
     */
    public static boolean isThisMonth(String date){
        String monthFirstDay = getMonthFirstDay(formatDate(new Date()));
        String monthLastDay = getMonthLastDay(formatDate(new Date()));
        if (date.compareTo(monthFirstDay)>=0 && date.compareTo(monthLastDay)<=0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @Description: 判断当前日期是否在今年,是为true
     * @param:
     * @date: 2018/12/27 17:10
     */
    public static boolean isThisYear(String date){
        String yearFirstDay = getYearFirstDay(formatDate(new Date()));
        String yearLastDay = getYearLastDay(formatDate(new Date()));
        if (date.compareTo(yearFirstDay)>=0 && date.compareTo(yearLastDay)<=0){
            return true;
        }else{
            return false;
        }
    }

    public static String addMonth(String date,int i){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toFormat(date));
        calendar.add(Calendar.MONTH, i);
        return dayFormatter.get().format(calendar.getTime());
    }
    public static boolean compareDate(String startTime,String endTime){
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date start =null;
    	Date end =null;
		try {
			start = sf.parse(startTime);
		    end =sf.parse(endTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    	return start.before(end);
    }

    /**
    * @Description: 获取某个日期所在年的第一天
    * @param:
    * @date: 2019/1/6 15:50
    */
    public static String setYearDefaultStartDate(String end){
        return end.substring(0,5) + "01-01 00:00:00";
    }

    /**
     * @Description: 获取某个日期所在年的最后一天
     * @param:
     * @date: 2019/1/6 15:50
     */
    public static String setYearDefaultEndDate(String start){

        return start.substring(0,5) + "12-31 23:59:59";
    }

    /**
     * 获取当前月 ：yyyy-MM
     * @param date
     * @return
     */
    public static String getMonthOfDay(String date){
        String nowMonth = date.substring(0,7);
        return nowMonth;
    }
    /**
     * 获取今日的日期,同比使用
     * @param date
     * @return
     */
    public static String getNowDate(String date,Integer type){
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	try {

    		Date now =new Date();
    		String nows =sf.format(now);
 //   		String nows = "2019-01-28";
			if(date.compareTo(nows)>0){
			   if(!testFirstDay(nows, type)){
				  date = getAddDate(date, -1);
			}else{
				//日期向后推3年，确保当前和同期无数据
				date=TimeUtils.addMonth(nows, 36);
			}

			}
			return date;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return date;
    }

    /**
     * 获取默认日期：为空时返回当前日期
     * @param date
     * @return
     */
    public static String getDefaultDate(String date){
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        if(date ==null || date.equals("null") || date.equals("")){
             Date now = new Date();
             date = sf.format(now);
        }

        return date ;
    }
    /**
     * 判断当前日期是否是周一，每月第一天，每年第一天。
     * @param date
     * @param type
     * @return
     */
    public static boolean testFirstDay(String date,Integer type){
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    	try {
			Date day = sf.parse(date);
			Calendar c =Calendar.getInstance();
			c.setTime(day);
			if(type == 2){
				int i = c.get(Calendar.DAY_OF_WEEK);
				if(i==2){
					return true;
				}else{
					return false;
				}
			}
			if(type == 3){
				int m = c.get(Calendar.DAY_OF_MONTH);
				if(m ==1){
					return true;
				}else{
					return false;
				}
			}
			if(type == 4){
				int y = c.get(Calendar.DAY_OF_MONTH);
				if(y==1){
					return true;
				}else {
					return false;
				}
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return false;
    }

    /**
     * 计算当前其所所在年的所有月份list： yyyy-MM
     * @param date
     * @return
     */
    public static List<String> getMonths(String date){
        List <String> list = new ArrayList<>();
        String firstDay =TimeUtils.getYearFirstDay(date);
        String firstMonth = TimeUtils.getMonthOfDay(firstDay);
        list.add(firstMonth);
        for(int i = 0 ; i<11;i++){
            String day = TimeUtils.addMonth(firstDay,i+1);
            list.add(TimeUtils.getMonthOfDay(day));
        }
        return list;
    }

}

