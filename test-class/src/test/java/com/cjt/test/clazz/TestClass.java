package com.cjt.test.clazz;

import com.alibaba.fastjson.JSON;
import com.cjt.test.*;
import com.cjt.test.pojo.NormalClass;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chenjintao
 * @Date: 2019-08-26 15:51
 */

@RunWith(JUnit4.class)
@Slf4j
public class TestClass {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestClass.class);

    @Test
    public void test1() {
        String strDate = "2018-02";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-ww");
        try {
            System.out.println(sdf.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws ParseException {
        Date date = DateUtils.parseDate("2019-01", "yyyy-ww");
        System.out.println(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        calendar.add(Calendar.SECOND, -1);
        System.out.println(calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.getTime());
    }

    @Test
    public void test3() throws ParseException {
        Date date = DateUtils.parseDate("2019-01", "yyyy-ww");
        System.out.println(date);
        Date date1 = DateUtils.addDays(date, 1);
        System.out.println(date1);
        Date date2 = DateUtils.addWeeks(date1, 1);
        System.out.println(date2);
        Date date3 = DateUtils.addSeconds(date2, -1);
        System.out.println(date3);

        StringBuffer stringBuffer = new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        stringBuffer.append(simpleDateFormat.format(date1));
        stringBuffer.append("~");
        stringBuffer.append(simpleDateFormat.format(date3));
        System.out.println(stringBuffer.toString());
    }


    @Test
    public void test5() {
//        String str = "123";
//        Random random = new Random();
//        Random random1 = new Random();
//        System.out.println(random.nextInt());
//        System.out.println(random1.nextInt());
//        System.out.println(random.nextInt(1));
//        System.out.println(random1.nextInt(100));


//        Double d1= 0.56;
//        Integer int1 = 3;
//        Double int3 = int1/d1;
//      //  Integer int2 = int1/d1.intValue();
//        System.out.println(int1/d1);
//       // System.out.println(int2);
//        System.out.println(int3.intValue());
//        System.out.println((int)(int1/d1));
//
//        Double double1 = 0.0;
//        System.out.println(double1 == 0);
//        System.out.println(double1.equals(0d));

//        Integer int1 = null;
//        int int2 =2;
//        int int3 = 0;
//        int1 += 2;
//        System.out.println(int1);
        String str1 = "a";
        String str2 = "b";
        str1 += str2;
        System.out.println(str1);
    }

    @Test
    public void test6() {
//        BigDecimal bigDecimal = new BigDecimal(0);
//        System.out.println(bigDecimal.toString());
//        Integer int1 = null;
//        int int2 = int1;
//        BigDecimal bigDecimal1 = new BigDecimal(1000);
//       // System.out.println(bigDecimal1.toString());
//        BigDecimal bigDecimal2 = new BigDecimal(255);
//        System.out.println(bigDecimal2.divide(bigDecimal1,2,BigDecimal.ROUND_HALF_DOWN).doubleValue());
        Integer i = null;
        System.out.println(i / 100);
    }

    @Test
    public void test7() {
        Byte b1 = -16;
        byte b2 = 16;

        short s1 = -16;
        short s2 = 16;

        int i1 = 100;
        System.out.println(b1 >>> 2);
        System.out.println(s1 >>> 2);
        System.out.println(b2 >>> 2);
        System.out.println(s2 >>> 2);

        System.out.println(b2 >> 1);
        System.out.println(b2 >> 2);

        System.out.println(i1 << 32);
    }

    @Test
    public void test1a() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date beginTime = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.MILLISECOND, -1);
        Date entTime = calendar.getTime();
        System.out.println(beginTime);
        System.out.println(entTime);
    }

    @Test
    public void test2a() throws ParseException {
        Date beginTime = null;
        Date endTime = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        beginTime = calendar.getTime();

        String time = "2018-11-29 16";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        System.out.println(simpleDateFormat.parse(time).compareTo(beginTime));
    }

    @Test
    public void test3a() throws ParseException {
        String str = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        System.out.println(simpleDateFormat.parse(str));
    }

    @Test
    public void test4() {
        System.out.println(Math.sin(Math.toRadians(180)));
//        System.out.println(Math.sin(Math.toRadians(90)));
//        System.out.println(Math.sin(Math.toRadians(180)));
//        System.out.println(Math.sin(Math.toRadians(30)));
//        BigDecimal bigDecimal = BigDecimal.valueOf(3.1455926);
//        System.out.println(BigDecimal.valueOf(Math.sin(Math.toRadians(180))).setScale(2, RoundingMode.HALF_UP));
//        System.out.println(BigDecimal.valueOf(Math.sin(Math.toRadians(90))).setScale(2, RoundingMode.HALF_UP));
//        System.out.println(BigDecimal.valueOf(Math.sin(Math.toRadians(360))).setScale(2, RoundingMode.HALF_UP));
//        System.out.println(BigDecimal.valueOf(Math.sin(Math.toRadians(30))).setScale(2, RoundingMode.HALF_UP));

        int setSize = 9;     //定义正多边形的边数和顶点数
        int radius = 100;
        for (int i = 1; i <= setSize; i++) {
            double x = (BigDecimal.valueOf(Math.sin((2 * Math.PI) / setSize * (i - 1))).multiply(new BigDecimal(-radius)).setScale(2, RoundingMode.HALF_UP).doubleValue());
            double y = (BigDecimal.valueOf(Math.cos((2 * Math.PI) / setSize * (i - 1))).multiply(new BigDecimal(radius)).setScale(2, RoundingMode.HALF_UP).doubleValue());
            System.out.println("x=" + x + ";y=" + y);
        }
        System.out.println(BigDecimal.valueOf(Math.sin((2 * Math.PI) / setSize * (6 - 1))));
        System.out.println(BigDecimal.valueOf(Math.sin((2 * Math.PI) / setSize * (6 - 1))).setScale(2, 4));
        System.out.println(BigDecimal.valueOf(Math.sin((2 * Math.PI) / setSize * (6 - 1))).multiply(new BigDecimal(100)).setScale(2, 4));
        System.out.println(-radius * (BigDecimal.valueOf(Math.sin((2 * Math.PI) / setSize * (6 - 1))).setScale(2, RoundingMode.HALF_UP).doubleValue()));

    }

    @Test
    public void test5a() {
        System.out.println(Integer.parseInt(" "));
    }

    @Test
    public void test6a() {
        Integer i1 = 1000;
        Integer i2 = 200;
        System.out.println(i1 > 200);
        System.out.println(i1 < 1000);
    }


    @Test
    public void test8() {
//        String content = "机器人：喂您好，这里是人保电话车险，我是小王，工号0801，请问您是[张三]本人吗？|用户：|机器人：麻烦问一下您就是[张三]本人对吧？|用户：最好就是刚才|机器人：那您是[张三]本人是吗？|用户：干啥的|机器人：那您是[张三]本人是吗？|用户：是的呀是的|机器人：感谢您的接听，我们人保电话车险正在做优惠活动，您的爱车[皖A12345]保险快到期了对吧？|用户：我不知道到期了没|机器人：那您这个[皖A12345]的车子保险也快到期了吧？|用户：我不知道到期了没啊|机器人：那您这个[皖A12345]的车子保险也快到期了吧？|用户：到期了到期了到期了|机器人：那您这个[皖A12345]的车子保险也快到期了吧？|用户：到期了|机器人：那您这个[皖A12345]的车子保险也快到期了吧？|用户：恩是的|机器人：是这样的，我们人保车险现在优惠力度还挺大的，稍后安排一位高级客户经理帮您算算今年大概的保费，到时您接听一下电话好吧？|用户：|机器人：要不我们稍后先给您报个价，您先了解一下市场行情，您看可以吗？|用户：可以好的先这样吧|机器人：要不我们稍后先给您报个价，您先了解一下市场行情，您看可以吗？|用户：好好好好好好好|机器人：好的，稍后客户经理给您回电，请您注意接听，就不打扰了，祝您生活愉快，再见。\n";
//
//        String[] split = content.split("(机器人：).*\\|(用户：).*");
//
//        for (String text : split) {
//            System.out.println(text);
//            if (text.matches("(机器人：).*")) {
//                System.out.println("----J");
//            }
//            if (text.matches("(用户：).*")) {
//                System.out.println("----Y");
//            }
//    }

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
        System.out.println(calendar.get(Calendar.WEEK_OF_MONTH));

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_WEEK, 5);
        calendar1.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
        System.out.println(calendar1.getTime());
    }

    @Test
    public void test9() {
        int[] workWeekArray = {2, 3, 4, 5, 6};
        int[] freeWeekArray = {7, 5, 1};
        System.out.println(Arrays.binarySearch(workWeekArray, 3));
        System.out.println(Arrays.binarySearch(freeWeekArray, 1));
        Arrays.sort(workWeekArray);
        Arrays.sort(freeWeekArray);
        System.out.println(Arrays.binarySearch(workWeekArray, 3));
        System.out.println(Arrays.binarySearch(freeWeekArray, 1));
    }

    @Test
    public void test10() {
        LocalDate localDate = LocalDate.of(2020, 1, 20);
        LocalDate localDate1 = LocalDate.of(2021, 1, 21);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(localDate1.toEpochDay() - localDate.toEpochDay());
    }

    @Test
    public void test11() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        ListIterator<Integer> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            listIterator.add(1);
            System.out.println(listIterator.previous());
            listIterator.next();
            ;
            //   System.out.println(listIterator.previousIndex());

            //   System.out.println("index"+listIterator.nextIndex());

            //   System.out.println("index"+listIterator.nextIndex());
            //System.out.println(listIterator.next() );


        }

//        while (listIterator.hasNext()){
//
//            System.out.println(listIterator.nextIndex());
//            System.out.println(listIterator.next() );
//            System.out.println(listIterator.nextIndex());
////            System.out.println(listIterator.previousIndex());
//        }
//    }
    }

    @Test
    public void test12() {
        int calledCount = 60001;
        int batchCount = 10000;
        int splitCount = calledCount % batchCount == 0 ? calledCount / batchCount : calledCount / batchCount + 1;

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(ListUtils.partition(list, 10000));
    }

    @Test
    public void test13() {
        String str = "56789";
        System.out.println(str.replaceAll("(\\d+)(\\d{5})", "$1"));


        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                list.remove(0);
            }
            System.out.println(i);
            System.out.println(list.get(i));
        }
    }

    @Test
    public void test14() throws InterruptedException {
        class ReentrantLockTest extends Thread {

            private ReentrantLock lock = new ReentrantLock();
            private int i = 0;

            private ReentrantLockTest(String name) {
                super.setName(name);
            }

            @Override
            public void run() {
                for (int j = 0; j < 10000; j++) {
                    lock.lock();
                    try {
                        System.out.println(this.getName() + " " + i);
                        i++;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }

        ReentrantLockTest thread1 = new ReentrantLockTest("thread1");
        ReentrantLockTest thread2 = new ReentrantLockTest("thread2");
        thread1.setDaemon(true);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(thread1.i);
        //Thread.sleep(1000);
        //System.out.println(ReentrantLockTest.i);

    }

    @Test
    public void test15() {
        Item item = new Item();
        item.setStr("test");
        Item item1 = new Item(item);
        System.out.println(item1.toString());

        ItemOne itemOne = new ItemOne(item1);
        itemOne.setAge(1);
        System.out.println(itemOne.toString());
    }

    @Test
    public void test16() {
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < 1000000; i++) {
            set.add(UUID.randomUUID().toString());
        }
        System.out.println(set.size());
    }

    @Test
    public void test17() {
        int i = 1000 % 1000;
        System.out.println(i);
    }

    @Test
    public void test18() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        List<List<Integer>> list1 = ListUtils.partition(list, 3);

        for (List<Integer> listItem : list1) {
            System.out.println(listItem);
        }
    }

    @Test
    public void test19() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        Date beginTime = calendar.getTime();
        System.out.println(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endTime = calendar.getTime();
        System.out.println(calendar.getTime());
    }


    @Test
    public void test21() {

        String str = "123";
        System.out.println(str instanceof Serializable);
    }

    @Test
    public void test22() {
        String str = "陈锦涛,15515091290,421521199706047019 \n" +
                "李梁 18818881999 410521199706057018 \n" +
                "王培成，13313331333，410521199706047017 \n" +
                "庆贺|15888885555|410521199706047016 \n";
        StringTokenizer stringTokenizer = new StringTokenizer(str, " \t\n\r\f,，|");

        while (stringTokenizer.hasMoreElements()) {
            System.out.println(stringTokenizer.nextElement());
        }
    }


    @Test
    public void test24() {
        String str = "123456";
        System.out.println(str.substring(3));
        System.out.println(str.length());
    }

    @Test
    public void test25() {
        System.out.println(1.000000 == 1);
    }

    @Test
    public void test26() {
        //BigDecimal bigDecimal = new BigDecimal("null");

        String str = null;
        BigDecimal bigDecimal1 = new BigDecimal(str);
    }

    @Test
    public void test27() {
        int i = 1568106803;
        long l = i * 1000L;
        System.out.println(l);
    }


    @Test
    public void test28() {
        Integer i = null;
        //System.out.println(2 == i);
    }

    @Test
    public void test29() {
        String fileName = "11.txt";
        System.out.println(fileName.substring(fileName.lastIndexOf(".") + 1));
    }

    @Test
    public void test30() {
        System.out.println(DigestUtils.md5Hex("123456"));
    }


    @Test
    public void test31() {
        System.out.println(getClass());
    }

    @Test
    public void test32() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Item item = new Item();
        item.setStr("123");
        System.out.println(BeanUtils.getProperty(item, "str"));
    }

    @Test
    public void test33() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);


        for (int i = 0; i < 4; i++) {
            System.out.println(intList.get(0));
            intList.remove(0);
            System.out.println("---s:" + intList.size());

        }
    }

    @Test
    public void test33a() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        for (int i = 0; i < 4; i++) {
            System.out.println(intList.get(i));
            if (i == 2)
                intList.remove(i);
            System.out.println("---s:" + intList.size());

        }
    }

    @Test
    public void test33b() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        for (int i = 0; i < intList.size(); i++) {     //现在不会报错了，之前版本会报错，但是依然不能这么写遍历删除的功能，因为会漏一次遍历，还得用iterator
            System.out.println(intList.get(i));
            if (i == 1) {
                intList.remove(i);
            }

            System.out.println("---s:" + intList.size());

        }
        System.out.println(intList.toString());
    }

    @Test
    public void test33ba() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        for (int i = 0; i < intList.size(); i++) {     //使用索引退位i--也可解决问题
            Integer value = intList.get(i);
            System.out.println(value);
            if (value == 1) {
                intList.remove(i);
                i--;
            }

            System.out.println("---s:" + intList.size());

        }
        System.out.println(intList.toString());
    }

    @Test
    public void test33c() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        for (Integer i : intList) {
            System.out.println(intList.get(i));
            if (i == 2)
                intList.remove(i);
            System.out.println("---s:" + intList.size());

        }

        System.out.println(intList.toString());
    }

    @Test
    public void test34() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        ListIterator<Integer> listIterator = intList.listIterator();
        while (listIterator.hasNext()) {
            System.out.println(listIterator.previousIndex() + "-" + listIterator.nextIndex());
            //System.out.println(listIterator.previous());
            System.out.println(listIterator.next());
            System.out.println(listIterator.previousIndex() + "-" + listIterator.nextIndex());
            listIterator.remove();
            System.out.println(listIterator.previousIndex() + "-" + listIterator.nextIndex());
            System.out.println("--------");
        }
    }

    @Test
    public void test34a() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        ListIterator<Integer> listIterator = intList.listIterator(4);
        while (listIterator.hasPrevious()) {
            System.out.println(listIterator.previousIndex() + "-" + listIterator.nextIndex());
            System.out.println(listIterator.previous());

        }
    }

    @Test
    public void test35() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        Iterator<Integer> iterator = intList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }
    }

    @Test
    public void test35a() {
        List<Integer> intList = new ArrayList<>();
        intList.add(0);
        intList.add(1);
        intList.add(2);
        intList.add(3);

        Iterator<Integer> iterator = intList.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            System.out.println(i);
            if (i == 1)
                iterator.remove();
        }
        System.out.println(intList.toString());
    }

    @Test
    public void test36() {
        String str = "发达,发,d,f,sfa,";
        System.out.println(str.replaceAll(",$", ""));
    }

    @Test
    public void test37() {
        for (int j = 0; j < 50000; j++) {
            int i = 8;
            while ((i -= 3) > 0) ;
            System.out.println("i = " + i);
        }

    }

    @Test
    public void test38() {
        for (int i = 0; i < 50000; i++) {
            testBug();
        }
    }

    @Test
    public void test39() {
        if (false) {
            System.out.println(1);
        } else if (true) {
            System.out.println(1);
        } else if (false) {
            System.out.println(1);
        } else if (true) {
            System.out.println(1);
        }
    }

    @Test
    public void test40() {
        String str = "123、123、456、";
        System.out.println(str.replaceAll("(.*)、$", "$1;"));
        System.out.println(str.replaceAll("、", ""));
        System.out.println(str.replaceAll("、$", ";"));
    }

    @Test
    public void test41() {
        long time1 = System.currentTimeMillis();
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < 10000000; i++) {
            list1.add(String.valueOf(i));
        }
        long time2 = System.currentTimeMillis();
        System.out.println(time2 - time1);

        ArrayList<String> list2 = (ArrayList<String>) list1.clone();
        long time3 = System.currentTimeMillis();
        System.out.println(time3 - time2);

    }

    @Test
    public void test42() {
        String called = "15515091290";
        String normalPhone = called.length() > 11 ? called.substring(called.length() - 11) : called;
        System.out.println(normalPhone);
    }

    @Test
    public void test43() {
        int i1 = 100;
        int i2 = 100;
        int i3 = 200;
        int i4 = 200;
        Integer i5 = 100;
        Integer i6 = 100;
        Integer i7 = 200;
        Integer i8 = 200;
        System.out.println(i1 == i2);
        System.out.println(i3 == i4);
        System.out.println(i5 == i6);
        System.out.println(i5.equals(i6));
        System.out.println(i7 == i8);
        System.out.println(i7.equals(i8));
    }

    @Test
    public void test44() {
        String str = null;
        System.out.println("123" + str);
    }

    @Test
    public void test45() throws InvocationTargetException, IllegalAccessException {
        NormalClass normalClass = new NormalClass();

//        BeanUtils.setProperty(normalClass,"str1","strValue1");
//        BeanUtils.setProperty(normalClass,"str2","strValue2");
//        BeanUtils.setProperty(normalClass,"int1",1);

        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        beanUtilsBean.setProperty(normalClass, "str1", "strValue1");
        beanUtilsBean.setProperty(normalClass, "str2", "strValue2");
        beanUtilsBean.setProperty(normalClass, "int1", 1);

        System.out.println(normalClass.toString());
    }

    @Test
    public void test46() throws InvocationTargetException, IllegalAccessException {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();  //支持类型自动转换
        for (int i = 0; i < 50000; i++) {

            NormalClass normalClass = new NormalClass();
            beanUtilsBean.setProperty(normalClass, "str1", "strValue1-" + i);
            beanUtilsBean.setProperty(normalClass, "str2", "strValue2-" + i);
            beanUtilsBean.setProperty(normalClass, "int1", String.valueOf(i));
            System.out.println(normalClass.toString());
        }
    }

    @Test
    public void test47() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
        PropertyUtilsBean propertyUtilsBean = beanUtilsBean.getPropertyUtils(); //不支持类型自动转换 但是性能高
        for (int i = 0; i < 50000; i++) {

            NormalClass normalClass = new NormalClass();
            propertyUtilsBean.setProperty(normalClass, "str1", "strValue1-" + i);
            propertyUtilsBean.setProperty(normalClass, "str2", "strValue2-" + i);
            propertyUtilsBean.setProperty(normalClass, "int1", i);
            System.out.println(normalClass.toString());
        }
    }

    @Test
    public void test48() throws InterruptedException {
        BeanUtilsBean util1 = BeanUtilsBean.getInstance();  //支持类型自动转换
        PropertyUtilsBean util = util1.getPropertyUtils(); //不支持类型自动转换
        //测试有没有线程安全问题
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                NormalClass normalClass = new NormalClass();
                String threadName = Thread.currentThread().getName();
                try {
                    util.setProperty(normalClass, "str1", "strValue1-" + threadName);
                    util.setProperty(normalClass, "str2", "strValue2-" + threadName);
                    util.setProperty(normalClass, "int1", Thread.activeCount());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                System.out.println(normalClass.toString());
            }).start();
        }

        Thread.sleep(5000);
    }

    @Test
    public void test49() {
        Map<Integer, String> map = new TreeMap<>((i1, i2) -> i2 - i1);
        map.put(1, "1");
        map.put(2, "2");
        map.put(-1, "-1");
        map.put(-5, "-5");

        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println(map);
    }

    @Test
    public void test50() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");

        String str = list.toString();
        System.out.println(str);

        System.out.println(str.replaceAll(",", "#"));
        System.out.println(str.replaceAll("^[(.*)]$", "$1"));

        System.out.println("213#".replaceAll("#$", ""));
    }

    @Test
    public void test51() {
        System.out.println(lineToHump("cust_name"));
        System.out.println(humpToLine("custName"));
    }

    @Test
    public void test52() {
        Integer integer1 = null;

        //int int1 = integer1;
        int int2 = 0;
        System.out.println(integer1);
        // System.out.println(int1);
        System.out.println(int2 == integer1);
    }

    @Test
    public void test53() {
        String temp = "1.8603732262E10";
        if (temp.contains("E")) {
            BigDecimal bigDecimal = new BigDecimal(temp);
            temp = bigDecimal.toPlainString();
        }
        System.out.println(temp);
    }

    @Test
    public void test54() {
        int i = 10;
        Object o = 0.1;
        Integer i1 = 10;
        System.out.println(i1 instanceof Integer);
    }

    @Test
    public void test55() {
        Class<String> clazz = String.class;
        String typeName = clazz.getTypeName();
        String typeName1 = clazz.toString();
        System.out.println(typeName);
        System.out.println(typeName1.toString());
    }

    @Test
    public void test56() {
        char char1 = 48;
        System.out.println(char1);
    }


    @Test
    public void test58() {
        String str1 = null;
        String str2 = null;
        System.out.println(str1 == str2);
    }


    @Test
    public void test60() {
        String str1 = null;
        String str2 = null;
        String str3 = str1 + str2;
        Set<String> set = new HashSet<>();
        set.add(str3);
        set.add(str1);
        set.add(str2);

        StringBuilder sb = new StringBuilder();
        sb.append(str1).append(str2);
        System.out.println(str3);
        System.out.println(str3);
        System.out.println(sb.toString());
    }

    @Test
    public void test61() {

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        int allSize = 1000;
        int quantity = 55;
        int samplingSize = 0;
        int samplingType = 2;

        int quantityType = 1;
        if (quantityType == 1) {
            samplingSize = quantity;
        } else if (quantityType == 2) {
            samplingSize = allSize * quantity / 100;
        }

        List<Integer> result = new ArrayList<>();

        if (samplingType == 1) {
            int baseNum = allSize / samplingSize;
            for (int i = 0; i < samplingSize; i++) {
                result.add(list.get(i * baseNum));
            }
        } else if (samplingType == 2) {
            Collections.shuffle(list);
            for (int i = 0; i < samplingSize; i++) {
                result.add(list.get(i));
            }
        }


        System.out.println(result);
    }

    @Test
    public void test62() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        Collections.shuffle(list);
        System.out.println(list);
    }

    @Test
    public void test63() {
        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            System.out.println(random.nextInt(1));
        }
    }

    @Test
    public void test64() {
        Integer i = null;
        BigDecimal bigDecimal = new BigDecimal(i);
        System.out.println(bigDecimal);
    }

    @Test
    public void test65() {
        List<? extends Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(null);
        }
        System.out.println(list);
    }

    @Test
    public void test66() {
        String str1 = "123456789";
        System.out.println(str1.substring(0, str1.length() - 5));
    }

    @Test
    public void test67() {
        Integer i1 = 5;
        Integer i2 = 1;
        System.out.println(i1.compareTo(i2));

        Date now = new Date();
        long result = DateUtils.getFragmentInDays(now, Calendar.DATE);
        System.out.println(result);
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }

    @Test
    public void test68() {
        System.out.println();
        if (!(true || true) && false) {
            System.out.println("1`11");
        }
    }

    @Test
    public void test69() {
        Integer i1 = 7000;
        Integer i2 = 7000;
        System.out.println(i1.hashCode());
        System.out.println(i2.hashCode());
        Map<Integer, String> map = new HashMap<>();
        map.put(i1, "123");
        System.out.println(map.containsKey(i2));
        map.put(i2, "456");
        System.out.println(map);
    }


    @Test
    public void test70() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(i);
                throw new RuntimeException(String.valueOf(i));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test71() {
        BigDecimal bigDecimal = new BigDecimal(56);
        BigDecimal bigDecimal1 = new BigDecimal(150);
        BigDecimal bigDecimal2 = new BigDecimal(30);

//        BigDecimal bigDecimal3 = BigDecimal.valueOf(1.54);
//        System.out.println(bigDecimal3.setScale(1,RoundingMode.HALF_DOWN));
//        System.out.println(bigDecimal3.setScale(1,RoundingMode.HALF_UP));

        BigDecimal result1 = bigDecimal.divide(bigDecimal1, 4, RoundingMode.FLOOR);
        BigDecimal result2 = bigDecimal2.divide(bigDecimal1, 4, RoundingMode.HALF_UP);
        System.out.println(result1.toString() + " " + result1.doubleValue());
        System.out.println(result2.toString() + " " + result2.doubleValue());
    }

    @Test
    public void test72() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new LinkedList<>();

        for (int i = 0; i < 1000000; i++) {
            list1.add(i);
            list2.add(i);
        }

        System.out.println(list1.get(0));
        System.out.println(list1.get(999999));
        System.out.println(list2.get(0));
        System.out.println(list2.get(999999));
    }

    @Test
    public void test73() {
        Map<String, Integer> map = new HashMap<>();
        map.put("1", null);
        Integer i1 = map.get("1");
        i1 = i1 == null ? 1 : i1;
        System.out.println(map.get("1"));

    }

    @Test
    public void test74() {
        String str1 = "0#1#2##4#5##7";

        Map<String, Object> result = new HashMap<>();
        int i = 0;
        int j = 0;
        String lastToken = null;
        for (StringTokenizer st = new StringTokenizer(str1, "#", true); st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            if (token.equalsIgnoreCase("#")) {
                if (token.equalsIgnoreCase(lastToken)) {
                    System.out.println("---" + j + ":");
                    j++;
                }
            } else {
                System.out.println("---" + j + ":" + token);
                j++;
            }
            lastToken = token;

            result.put("field" + i, token);


        }
        System.out.println(result);
    }

    @Test
    public void test74a() throws UnsupportedEncodingException {
        String str1 = new String("0€1€2€€4€5€€7".getBytes(), "GBK");
        String delim = new String("€".getBytes(), "GBK");
        Map<String, Object> result = new HashMap<>();
        int i = 0;

        for (StringTokenizer st = new StringTokenizer(str1, delim, false); st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            result.put("field" + i, token);

        }
        System.out.println(result);


    }

    @Test
    public void test74b() throws IOException {
        OutputStream os = new FileOutputStream(new File(".", "G.txt"));
        OutputStreamWriter oswForFileReport = new OutputStreamWriter(os, "GBK");
        BufferedWriter bwForRecordReport = new BufferedWriter(oswForFileReport);

        for (int i = 0; i < 10; i++) {
            String numForContent = String.valueOf(i);
            String str1 = "城市" + i;
            String str2 = "小区" + i;
            String splitStr = new String("€".getBytes(), "GBK");
            String recordReportLineContent = numForContent + splitStr + str1 + splitStr + str2;
            bwForRecordReport.write(new String(recordReportLineContent.getBytes()));
            bwForRecordReport.newLine();
        }
        bwForRecordReport.flush();

    }

    @Test
    public void test74c() throws IOException {

        String delim = new String("€".getBytes());

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "b.txt")), "GBK"));
        Map<String, Object> result = new HashMap<>();

        String str1 = input.readLine();
        System.out.println(str1);
        int i = 0;
        StringTokenizer st = new StringTokenizer(str1, delim, false);
        for (; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            result.put("field" + i, token);

        }
        System.out.println(result);


    }


    @Test
    public void test74d() throws IOException {

        String delim = new String("€".getBytes(), "gbk");

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "c.txt")), "gbk"));
        Map<String, Object> result = new HashMap<>();

        String str1 = input.readLine();
        int i = 0;
        StringTokenizer st = new StringTokenizer(str1, delim, false);
        for (; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            result.put("field" + i, token);

        }
        System.out.println(result);


    }

    @Test
    public void test74e() throws IOException {

        String delim = new String("€".getBytes(), "gbk");

        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "d.dat")), "gbk"));
        Map<String, Object> result = new HashMap<>();

        String str1 = input.readLine();
        int i = 0;
        StringTokenizer st = new StringTokenizer(str1, delim, false);
        for (; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            result.put("field" + i, token);

        }
        System.out.println(result);


    }

    @Test
    public void test74f() throws IOException, DecoderException {

        String delim = new String(Hex.decodeHex("80".toCharArray()), "ascii");
        //String delim = String.valueOf((char) 0x80);
        System.out.println(delim);
        System.out.println((char) 0x80);
        System.out.println(Arrays.toString(String.valueOf((char) 0x80).getBytes("ASCII")));
        System.out.println(Arrays.toString(delim.getBytes("ascii")));


        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "e.verf")), "gbk"));
        Map<String, Object> result = new HashMap<>();

        String str1 = input.readLine();
        int i = 0;
        StringTokenizer st = new StringTokenizer(str1, delim, false);
        for (; st.hasMoreTokens(); i++) {

            String token = st.nextToken();
            //System.out.println(Arrays.toString(token.getBytes()));
            System.out.println(token);
            result.put("field" + i, token);
        }

        System.out.println(result);

    }


    @Test
    public void test74f1() throws IOException, DecoderException {

        byte[] byte1 = Hex.decodeHex("80".toCharArray());
        char char1 = (char) 0x80;

        byte[] byte2 = {(byte) 255};

        byte[] byte3 = {(byte) 257, (byte) 258};

        String delim = new String(byte2);
        //String delim = String.valueOf((char) 0x80);

        String str2 = new String(byte1);


        System.out.println(Arrays.toString(byte1));
        System.out.println(Arrays.toString(byte2));
        System.out.println(Arrays.toString(byte3));
        System.out.println(Arrays.toString("哈哈".getBytes("ascii")));
        System.out.println(str2);


        System.out.println(Arrays.toString(new String(byte1).getBytes("UTF-8")));


        System.out.println(delim);
        System.out.println(char1);

        System.out.println(Arrays.toString(String.valueOf(char1).getBytes("ASCII")));
        System.out.println(Arrays.toString(delim.getBytes("ascii")));
        System.out.println("0x" + Integer.toHexString(char1));

        System.out.println(Arrays.toString(String.valueOf((char) 0x80).getBytes()));
        System.out.println(Arrays.toString(delim.getBytes()));


        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "e.verf")), "gbk"));

        String str1 = input.readLine();
        String[] strArr = str1.split(delim);
        System.out.println(Arrays.toString(strArr));


    }

    @Test
    public void test74f2() throws IOException, DecoderException {

        byte[] byte1 = Hex.decodeHex("7e".toCharArray());
        String delim = new String(byte1);
        System.out.println(Arrays.toString(byte1));
        //String delim = String.valueOf((char) 0x80);
        char char1 = (char) 0x7e;
//        byte[] byte1 = {-127};
//        System.out.println(Arrays.toString(new String(byte1).getBytes("utf-8")));


        System.out.println(delim);
        System.out.println(char1);

        System.out.println(Arrays.toString(String.valueOf(char1).getBytes("ASCII")));
        System.out.println(Arrays.toString(delim.getBytes("ascii")));
        System.out.println("0x" + Integer.toHexString(char1));

        System.out.println(Arrays.toString(String.valueOf(char1).getBytes()));
        System.out.println(Arrays.toString(delim.getBytes()));


        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "e.verf")), "gbk"));

        String str1 = input.readLine();
        String[] strArr = str1.split(delim);
        System.out.println(Arrays.toString(strArr));


    }


    @Test
    public void test75g() throws IOException {
        String delim = new String("€".getBytes());
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "e.verf")), "GBK"));
        Map<String, Object> result = new HashMap<>();

        String str1 = input.readLine();
        System.out.println(str1);
        int i = 0;
        StringTokenizer st = new StringTokenizer(str1, delim, false);
        for (; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            result.put("field" + i, token);

        }
        System.out.println(result);


    }

    @Test
    public void test75H() throws IOException {
        String delim = new String("€".getBytes(), "GBK");
        BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(new File(".", "G.txt")), "GBK"));
        Map<String, Object> result = new HashMap<>();

        String str1 = input.readLine();
        int i = 0;
        StringTokenizer st = new StringTokenizer(str1, delim, false);
        for (; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            System.out.println(token);
            result.put("field" + i, token);

        }
        System.out.println(result);


    }

    @Test
    public void test75() throws UnsupportedEncodingException {
        String str1 = new String("0€1€2€€4€5€€7".getBytes(), "GBK");
        Map<String, Object> result1 = new HashMap<>();
        String delim = new String("€".getBytes(), "GBK");
        String[] tokens = str1.split(delim);
        Pattern pattern = Pattern.compile(new String("€".getBytes(), "GBK"));
        String[] tokens1 = pattern.split(str1, -1);
        System.out.println(Arrays.toString(tokens));
        for (int o = 0; o < tokens.length; o++) {
            String s = tokens[o];
            System.out.println(s);
            result1.put("field" + o, s);
        }

        System.out.println(result1);
    }

    @Test
    public void test76() throws UnsupportedEncodingException {
//        String str1 = new String("0€1€2€€4€5€€7".getBytes(), "GBK");
//        String delim = new String("€".getBytes(), "GBK");
        String str1 = new String("0#1#2##4#5##7".getBytes(), "GBK");
        String delim = new String("#".getBytes(), "GBK");
        Map<String, Object> result1 = new HashMap<>();

        String[] tokens = StringUtils.split(str1, delim);
        System.out.println(Arrays.toString(tokens));
        for (int o = 0; o < tokens.length; o++) {
            String s = tokens[o];
            System.out.println(s);
            result1.put("field" + o, s);
        }

        System.out.println(result1);
    }


    @Test
    public void test77() throws UnsupportedEncodingException {
        String str1 = new String("0€1€2€€4€5€€7".getBytes(), "GBK");
        Map<String, Object> result1 = new HashMap<>();
        String delim = new String("€".getBytes(), "GBK");

        int i = 0;
        org.apache.commons.text.StringTokenizer tokens = new org.apache.commons.text.StringTokenizer(str1, delim);
        tokens.setEmptyTokenAsNull(true);
        tokens.setIgnoreEmptyTokens(false);
        for (; tokens.hasNext(); i++) {
            String token = tokens.next();
            result1.put("field" + i, token);
        }

        System.out.println(result1);
    }

    @Test
    public void test78() {
        int[] intArr = {2};
        System.out.println(Arrays.binarySearch(intArr, 2));
    }


    @Test
    public void test79() {
        TestDefaultInterface testDefaultInterface = new TestDefaultInterfaceImpl();
        testDefaultInterface.test1();
        testDefaultInterface.test2();
        TestDefaultInterface.test3();
        System.out.println(TestDefaultInterface.i1);
        testDefaultInterface.test4();
        TestDefaultInterface.test5();
    }

    @Test
    public void test80() throws UnsupportedEncodingException {
        String str = "|";
        System.out.println(Arrays.toString(str.getBytes(StandardCharsets.UTF_8)));
        System.out.println(Arrays.toString(str.getBytes(StandardCharsets.US_ASCII)));
        System.out.println(Arrays.toString(str.getBytes("UNICODE")));
    }

    @Test
    public void test81() {
        String regex = "\\|";
        String str = "ab|cd";
        System.out.println(Arrays.toString(str.split(regex)));
    }

    @Test
    public void test82() {
        double a = 4.3;
        double b = 2.9;
        System.out.println(a - b / b);

        int i1 = 10;
        int i2 = 5;

        System.out.println(i1 - i2 / i2);

        System.out.println(2.9 / 2.9);
        System.out.println(b / b);
        System.out.println((int) (a - b / b));
        System.out.println(i1 ^ 2);
    }

    @Test
    public void test82a() {
        boolean b = 0.99999999f == 1f;
        System.out.println(b);

        float f1 = 16777215f;
        for (int i = 0; i < 10; i++) {
            System.out.println(BigDecimal.valueOf(f1).toPlainString());
            f1++;
        }
    }

    @Test
    public void test83() {
        Integer i1 = null;
        Integer i2 = 100;
        // i1+= i2;
        System.out.println(i1);

        String str = "";
        System.out.println(Long.parseLong(str));
    }


    @Test
    public void test85() {
        String str1 = "aBc";
        String res = str1.toUpperCase();
        System.out.println(res);
        System.out.println(str1);

        String str2 = "一二三";
        String res2 = str2.toUpperCase();
        System.out.println(res2);
        System.out.println(str2);
    }

    @Test
    public void test86() {
        int i = 1;
        Integer in = 2;

        boolean b = true;
        Boolean bo = false;

        Map<Integer, Object> map = new HashMap<>();
        map.put(1, i);
        map.put(2, in);
        map.put(3, b);
        map.put(4, bo);

        for (int j = 1; j < 3; j++) {
            System.out.println(map.get(j) instanceof Integer);
        }

        for (int j = 3; j < 5; j++) {
            System.out.println(map.get(j) instanceof Boolean);
        }


    }

    @Test
    public void testException() {
        try {
            Integer i = Integer.valueOf("123abc");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }


    }

    @Test
    public void test87() {
        String str1 = "col1000a";
        System.out.println(str1.replaceAll("col(\\d+).*", "$1"));
    }

    @Test
    public void test88() {
        String str1 = "a,b，c d    " +
                "\n" +
                "e;f";
        String delim = ",|，| +|\n|;";
        System.out.println(Arrays.toString(str1.split(delim)));
        System.out.println(str1.replaceAll(delim, ","));
    }

    public void testBug() {
        int i = 8;
        while ((i -= 3) > 0) ;
        System.out.println("i = " + i);
    }

    @Test
    public void test89() {
        lable:
        for (int i = 0; i < 10; i++) {
            for (int k = 0; k < 5; k++) {
                System.out.println("k:" + k);
                if (i == 3) continue lable;
            }
            System.out.println("i:" + i);
        }
    }

    @Test
    public void test90() {
        String str1 = null;
        System.out.println(str1 += "1");
    }

    @Test
    public void test91() {
        List<String> list1 = new ArrayList<>();
        list1.add("a");

        System.out.println(list1.stream().toString());
    }

    @Test
    public void test92() {
        // 确保一个漂亮的外观风格
        JFrame.setDefaultLookAndFeelDecorated(true);

        // 创建及设置窗口
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 "Hello World" 标签
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
    }

    @Test
    public void test93() {
        System.out.println(false ^ true);
    }

    @Test
    public void test94() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Class<? extends List> clazz = list.getClass();
        Method add = clazz.getDeclaredMethod("add", Object.class);
        add.invoke(list, "abc");
        System.out.println(list);
    }

    @Test
    public void test95() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Item item = new Item();
        item.setStr("123");
        Class<? extends Item> clazz = item.getClass();
        Field field = clazz.getDeclaredField("str");
        System.out.println(field.get(item));
    }

    @Test
    public void test96() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<Integer> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        Class<? extends List> c1 = list1.getClass();
        Class<? extends List> c2 = list2.getClass();
        System.out.println(c1.toString());
        System.out.println(c1.toGenericString());

        System.out.println(c2.toString());
        System.out.println(c2.toGenericString());

        System.out.println(c1 == c2);

        System.out.println(c1.equals(c2));

        System.out.println(list1.equals(list2));
        list1.add(1);
        list2.add("1");
        System.out.println(list1.equals(list2));

        Item i1 = new Item();
        Item i2 = new Item();
        System.out.println(i1 == i2);
    }

    @Test
    public void test97() {
        Class<Integer> c1 = int.class;
        Class<Integer> c2 = Integer.class;
        System.out.println(c1 == c2);
        System.out.println(c1 == Integer.TYPE);

    }

    @Test
    public void test98() {
        CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
        set.add("123");

        System.out.println(set.contains("123"));
        System.out.println(set.add("123"));
        System.out.println(set.add("456"));

        for (int i = 0; i < 10000; i++) {
            System.out.println(i + ":" + set.add("123"));
        }
        System.out.println("rm:" + set.remove("123"));
        System.out.println("add:" + set.add("123"));

    }

    @Test
    public void test99() throws InterruptedException {
        long unit1 = System.nanoTime();
        long t1 = System.currentTimeMillis();
        System.out.println(unit1);
        System.out.println(t1);

        Thread.sleep(1000);

        long unit2 = System.nanoTime();
        long t2 = System.currentTimeMillis();
        System.out.println(unit2);
        System.out.println(t2);
    }

    @Test
    public void test100() {
        int timeLen = 2000;
        long nanoTimeLent = TimeUnit.MILLISECONDS.toNanos(timeLen);
        System.out.println(nanoTimeLent);
        long endNano = nanoTimeLent + System.nanoTime();
        for (; ; ) {
            if (endNano - System.nanoTime() <= 0) {
                System.out.println(System.nanoTime());
                break;
            }
        }
    }

    @Test
    public void test101() {
        Object o = new ArrayList<>();
        o = null;
        ArrayList<String> list = new ArrayList<>();
        Page page = new Page();
        System.out.println(o instanceof Collection<?>);
        System.out.println(list instanceof Collection<?>);
        System.out.println(page instanceof Collection<?>);
        Object o2 = createList();
        System.out.println(o2 instanceof Collection<?>);
    }

    @Test
    public void test102() {
        List o = new ArrayList<>();
        System.out.println(o.get(100));
    }

    @Test
    public void test103() {
        Map<String, Car> map = new HashMap<>();
        Car car = new Car();
        car.setBrand("1");
        map.put("car1", car);
        Car cacheCar = map.get("car1");
        Car car2 = new Car();
        car2.setBrand("2");
        car2.setPrince("2");
        cacheCar = car2;
        System.out.println(JSON.toJSONString(map));
        car2.setPrince(null);
        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void test104() {
        Worker worker1 = new Worker();
        worker1.setAge(1000);
        Integer age1 = worker1.getAge();

        Map<String, Object> map = new HashMap<>();
        map.put("1", age1);

        Integer age2 = 2000;
        age1 = age2;
        System.out.println(worker1.getAge());
    }

    @Test
    public void test105() {
        AtomicReference<Integer> i1 = new AtomicReference<>(0);
        final Integer[] i2 = {0};
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.forEach(l -> {
            System.out.println(l);
            i1.getAndSet(i1.get() + 1);
            i2[0]++;
        });
        System.out.println(i1.get());
        System.out.println(i2[0]);
    }

    @Test
    public void testIdentity() {
        Integer in1 = new Integer(1);
        int i1 = 1;
        System.out.println(in1 == i1);

        System.out.println(System.identityHashCode(in1));
        System.out.println(System.identityHashCode(i1));

        Map<String, Integer> map = new HashMap<>();
        map.put("1", in1);
        in1 = new Integer(1000);

        System.out.println(JSON.toJSONString(map));
    }

    @Test
    public void testFinal() {
        final Car car = new Car();
        car.setPrince("123");
        System.out.println(JSON.toJSONString(car));

        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = str2.intern();
        String str5 = str3.intern();
        System.out.println(str1 == str2);
        System.out.println(str1 == str3);

        System.out.println(str2 == str4);
        System.out.println(str3 == str5);

        System.out.println(System.identityHashCode(str1));
        System.out.println(System.identityHashCode(str2));
        System.out.println(System.identityHashCode(str3));
    }

    @Test
    public void testFinal2() {

        String str3 = new StringBuilder("edf").toString();
        String str5 = str3.intern();
        System.out.println(str3 == str5);
        System.out.println(System.identityHashCode(str3));
    }

    @Test
    public void testFinal3() {

        String str3 = new StringBuilder("abc").append("def").toString();
        String str5 = str3.intern();
        System.out.println(str3 == str5);
        System.out.println(System.identityHashCode(str3));
    }

    @Test
    public void test106() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("abc");
        list.add("!@#");
        String str = String.join(",", list);
        System.out.println(str);

        List<String> list2 = null;

        String str2 = String.join(",", list2);
        System.out.println(str2);
    }

    @Test
    public void test107() {
        List<String> list = new ArrayList<>();
        list.add("123");

        list.add("789");

        list.add("456");

        list.sort((t1, t2) -> {
            return -t1.compareTo(t2);
        });
        System.out.println(JSON.toJSONString(list));

        list.sort((t2, t1) -> {
            return -t1.compareTo(t2);
        });
        System.out.println(JSON.toJSONString(list));
    }

    @Test
    public void test108() {
        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b2 = new BigDecimal("3");
        BigDecimal b3 = b1.divide(b2, 4, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(b3.doubleValue());
        System.out.println(b3.toPlainString());
    }

    @Test
    public void test109() {
        BigDecimal b1 = new BigDecimal("1");
        BigDecimal b2 = new BigDecimal("3");
        BigDecimal b3 = b1.divide(b2, 4, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(b3.doubleValue());
        System.out.println(b3.toPlainString());
    }


    @Test
    public void testDynaProxy() {
        Paint paint = (Paint) Proxy.newProxyInstance(Paint.class.getClassLoader(), new Class[]{Paint.class}, (proxy, method, args) -> {
            System.out.println(method);
            log.info("-------->test_proxy");
//           method.invoke(proxy,args);
            return null;
        });
        paint.paint();
    }

    @Test
    public void testDynamicProxy2() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        class MyInvocation implements InvocationHandler {
            private Object target;

            public MyInvocation(Object o) {
                this.target = o;
            }

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                log.info("前置通知");
                Object result = method.invoke(target, args);
                log.info("后置通知");
                return result;
            }
        }

        Class<Paint> paintClass = Paint.class;
        Class<?> proxyClazz = Proxy.getProxyClass(paintClass.getClassLoader(), paintClass);
        Constructor<?> constructor = proxyClazz.getConstructor(InvocationHandler.class);
        Paint paint = (Paint) constructor.newInstance(new MyInvocation(new TeacherForUI()));
        paint.paint();
    }

    @Test
    public void test110() {
        List<String> list1 = new ArrayList<>();
        list1.add("0");
        list1.add("1");
        list1.add("2");
        list1.add("3");

        list1.add(2, "2a");
        System.out.println(JSON.toJSONString(list1));
    }

    @Test
    public void testQueue1() {

        ArrayList<String> list0 = new ArrayList<>();
        list0.add("0");
        list0.add("1");
        list0.add("2");
        list0.add("3");
        for (String str : list0) {
            log.info("0-v:{}", str);
            log.info("0-s:{}", list0.size());
            //    list0.remove(str);
        }

        ArrayDeque<String> list1 = new ArrayDeque<>();
        list1.add("0");
        list1.add("1");
        list1.add("2");
        list1.add("3");
        for (String str : list1) {
            log.info("1-v:{}", str);
            log.info("1-s:{}", list1.size());
//            list1.remove(str);
            //list1.remove();
            System.out.println(list1.remove());

        }

        ArrayBlockingQueue<String> list2 = new ArrayBlockingQueue<>(5);
        list2.add("0");
        list2.add("1");
        list2.add("2");
        list2.add("3");
        for (String str : list2) {
            log.info("2-v:{}", str);
            log.info("2-s:{}", list2.size());
            list2.remove(str);
        }


        LinkedBlockingQueue<String> list3 = new LinkedBlockingQueue<>();
        list3.add("0");
        list3.add("1");
        list3.add("2");
        list3.add("3");
        for (String str : list3) {
            log.info("3-v:{}", str);
            log.info("3-s:{}", list3.size());
        }


        LinkedBlockingDeque<String> list4 = new LinkedBlockingDeque<>();
        list4.add("0");
        list4.add("1");
        list4.add("2");
        list4.add("3");
        for (String str : list4) {
            log.info("4-v:{}", str);
            log.info("4-s:{}", list4.size());
        }


    }

    @Test
    public void testQueue2() {
        ArrayDeque<String> list5 = new ArrayDeque<>();
        list5.addFirst("0");
        list5.addFirst("1");
        list5.addFirst("2");
        list5.add("x");
        list5.addFirst("3");
        for (String str : list5) {
            log.info("5-v:{}", str);
            log.info("5-s:{}", list5.size());
//            list1.remove(str);
            //list1.remove();
            System.out.println(list5.remove());
        }
        System.out.println(JSON.toJSONString(list5));
    }

    @Test
    public void testQueue3() {
        ArrayDeque<String> list = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            list.addFirst("f" + i);
        }
        for (int i = 0; i < 8; i++) {
            list.add("l:" + i);
        }
        list.addFirst("x");
        System.out.println(JSON.toJSONString(list));

    }

    @Test
    public void testQueue4() {
        int[] iArr = {0, 1, 2, 3, 4, 5, 6};
        int[] iArr2 = new int[14];
        Integer[] iArr3 = new Integer[14];
        System.out.println(JSON.toJSONString(iArr2));
        System.out.println(JSON.toJSONString(iArr3));
        System.arraycopy(iArr, 3, iArr2, 0, 4);
        System.out.println(JSON.toJSONString(iArr2));
    }

    @Test
    public void testOperator1() {
        int i1 = 5;
        int i2 = 5;
        int i3 = 4;
        int i4 = -4;

        int i5 = 14;
        int i6 = 1;
        System.out.println(i1 & i2);
        System.out.println(i1 & i3);
        System.out.println(i1 & i4);

        System.out.println(i5 & i6);
    }

    @Test
    public void testOperator2() {
        int i1 = 5;
        int i2 = 5;
        int i3 = 4;
        int i4 = -4;

        int i5 = 14;
        int i6 = 1;
        System.out.println(i1 | i2);
        System.out.println(i1 | i3);
        System.out.println(i1 | i4);

        System.out.println(i5 | i6);
    }

    @Test
    public void testOperator3() {
        int i1 = 12381233;
        int c1 = 9;
        int c2 = 16;

        int c1a = c1 << 1;
        int c2a = c2 << 1;
        System.out.println(i1 & c1);
        System.out.println(i1 & c1a);

        System.out.println(i1 & c2);
        System.out.println(i1 & c2a);
    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        ArrayDeque<String> list5 = new ArrayDeque<>();
        list5.addFirst("0");
        list5.addFirst("1");
        list5.addFirst("2");
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bo);
        os.writeObject(list5);
        byte[] bArr = bo.toByteArray();

        InputStream is = new ByteArrayInputStream(bArr);
        ObjectInputStream objectInputStream = new ObjectInputStream(is);

        ArrayDeque<String> list = (ArrayDeque<String>) objectInputStream.readObject();
        System.out.println(JSON.toJSONString(list));

    }

    @Test
    public void test111() {
        float f1 = 3.4f;
        double l1 = 1.11111111;

        float f2 = f1 + 1;
        System.out.println(f2);

        short s1 = 32767;
        int i1 = (1 << 15) - 1;
        s1 += +1;
        System.out.println(s1);
        System.out.println(i1);
    }

    public Object createList() {
        List<String> list = null;
        return list;
    }

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        for (int i = 0; i < 50000; i++) {
            testClass.testBug();
        }
    }


    public static String lineToHump(String str) {
        Pattern linePattern = Pattern.compile("_(\\w)");
        if (!str.contains("_")) return str;
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        /**
         * 下划线转驼峰
         */
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        if (sb.toString().indexOf("_") == 0) {
            sb.deleteCharAt(sb.toString().indexOf("_"));
        }
        return sb.toString();
    }


}
