package com.cjt.test;


import com.cjt.test.bean.DateTest1;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @Author: chenjintao
 * @Date: 2019-09-16 17:58
 */
@RunWith(JUnit4.class)
public class ClazzTest {


    @Test
    public void test1() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        DateTest1 dateTest1 = new DateTest1();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        Date date = calendar.getTime();
        dateTest1.setDate1(date);

        BeanUtilsBean.setInstance(new BeanUtilsBean2());
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");

        ConvertUtils.register(dateConverter, Date.class);

        System.out.println(BeanUtils.getProperty(dateTest1, "date1"));
    }

    @Test
    public void testZone() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("CST"));
        //calendar.setTimeZone(TimeZone.getTimeZone("CST"));
        System.out.println(calendar.getTime());
        Date now = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        System.out.println(simpleDateFormat.format(now));

    }

    @Test
    public void testLocalDate() {
        LocalDate localDate1 = LocalDate.of(2020, 1, 2);
        LocalDate localDate2 = LocalDate.of(2019, 12, 31);
        System.out.println(localDate2.toEpochDay() - localDate1.toEpochDay());

    }

    @Test
    public void testGetFreedayForYear() {
        Calendar calendarForFreeDay = Calendar.getInstance();
        int year = calendarForFreeDay.get(Calendar.YEAR);
        calendarForFreeDay.set(year, 0, 1);


        while (calendarForFreeDay.get(Calendar.YEAR) < year + 1) {
            int nowDateWeekNum = calendarForFreeDay.get(Calendar.DAY_OF_WEEK); //获取当天的当前周号 1和7表示为周天
            if (nowDateWeekNum == 1 || nowDateWeekNum == 7) {
                System.out.println(calendarForFreeDay.getTime());
            }

            calendarForFreeDay.add(Calendar.DAY_OF_YEAR, 1);

        }
    }

    @Test
    public void testCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.DAY_OF_YEAR, 32);
        System.out.println(calendar.getTime());
    }


   /* //增加休息日
    private Workday createFreeWorkday(Date beginTime, Date endTime) {
        Calendar calendar = Calendar.getInstance();
        //1.组装开始时间
        calendar.setTime(beginTime);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        beginTime = calendar.getTime();

        //2.组装结束时间
        calendar.setTime(endTime);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        endTime = calendar.getTime();


        Workday workday = new Workday();
        workday.setTenantId(tenantId);
        // workday.setStartTime(sdf.format(beginTime));
        workday.setStartTime(DateUtil.format(beginTime, DateUtil.DATE_TIME_PATTERN));
        //   workday.setEndTime(sdf.format(endTime));
        workday.setEndTime(DateUtil.format(endTime, DateUtil.DATE_TIME_PATTERN));
        workday.setWorkdayType("0");
        workday.setIsWork(0);
        workday.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now));
        return workday;
    }*/


    @Test
    public void testWeek() throws ParseException {
        Date date = DateUtils.parseDate("2021-01", "YYYY-ww");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.WEEK_OF_YEAR, 1);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);

        Date date1 = calendar.getTime();
        System.out.println("c_first_week_day:" + calendar.getFirstDayOfWeek());
        System.out.println("c_minimal_day_first_week:" + calendar.getMinimalDaysInFirstWeek());

        System.out.println(date);

        System.out.println("date1:" + date1);

        String yearWeek = getWeek("2021-01-04");
        System.out.println("yearWeeK:" + yearWeek);

        String yearWeek2 = getWeek2(date);
        System.out.println("yearWeeK2:" + yearWeek2);
    }

    public String getWeek(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);

        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        String result = String.format("%s-%s", year, week);
        System.out.println(result);
        return result;
    }

    public String getWeek2(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);

        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.WEEK_OF_YEAR);

        String result = String.format("%s-%s", year, week);
        System.out.println(result);
        return result;
    }
}
