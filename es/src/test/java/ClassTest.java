import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chenjintao
 * @Date: 2019-04-10 17:28
 */
@RunWith(JUnit4.class)
public class ClassTest {

    @Test
    public void test1() throws IOException {
        File file1 = new File("testfilemove/1.txt");
        File file2 = new File("./testfilemove");
        //FileUtils.moveDirectory(file1,file2);
        // FileUtils.moveToDirectory(file1,file2,true);
        System.out.println(file2);
    }

    @Test
    public void enumTest() {
        System.out.println(test.valueOf("TEST1"));
        System.out.println(test2.TEST3);
        System.out.println(Arrays.toString(test2.values()));
        System.out.println(test2.valueOf("TEST3"));
        test test11 = test.TEST1;
        System.out.println(test11);
        System.out.println(test11.name());
        System.out.println(test11.toString());
    }

    enum test {
        TEST1(0),
        TEST2(1);

        test(Integer i) {
        }

    }

    enum test2 {
        TEST3,
        TEST4;

        test2() {

        }
    }

    @Test
    public void test3() {
        Date date = new Date();
        System.out.println(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        System.out.println(simpleDateFormat.format(date));
    }

    @Test
    public void test4() {
        String str = "123,456,789";
        int i = str.lastIndexOf(",");
        System.out.println(i);
        int j = str.lastIndexOf(",", i - 1);
        System.out.println(j);
        System.out.println(str.substring(j));
    }

    @Test
    public void test5() {
        String[] strArr = {"1", "2", "3"};
        List<String> stringList = Arrays.asList(strArr);
        List<String> stringList1 = new ArrayList<>(stringList);
        stringList1.remove("1");
        System.out.println(stringList1);
    }

    @Test
    public void test6() throws ParseException {
        Date now = new Date();
        String date = "2019-01-06 00:00:00";
        DateFormat dateFormatForDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now = dateFormatForDay.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        System.out.println(calendar.get(Calendar.WEEK_OF_YEAR));

        DateFormat dateFormatForMonth = new SimpleDateFormat("yyyy-MM");
        DateFormat dateFormatForWeek = new SimpleDateFormat("yyyy-ww");
        System.out.println(dateFormatForMonth.format(now));
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println(dateFormatForWeek.format(calendar.getTime()));
    }

    @Test
    public void test7() {
        File file = new File("http://127.0.0.1:8080/");
        File file1 = new File(file, "/123123");
        System.out.println(file.getParentFile());

    }

    @Test
    public void test8() throws ParseException {
        Date now = new Date();

        String date = "2019-01-1 00:00:00";
        DateFormat dateFormatForDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now = dateFormatForDay.parse(date);
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_WEEK,2);
//        System.out.println(calendar.getTime());
        calendar.setTime(now);
        calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println(calendar.getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        System.out.println(calendar.getTime());
    }

    @Test
    public void test9() throws ParseException {
        Date now = new Date();

        String date = "2019-01-13 00:00:00";
        DateFormat dateFormatForDay = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        now = dateFormatForDay.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        System.out.println(dateFormatForDay.format(calendar.getTime()));
        calendar.add(Calendar.MONTH, 1);
        System.out.println(dateFormatForDay.format(calendar.getTime()));
    }

    @Test
    public void test10() {
        StringBuffer sb = new StringBuffer();
        System.out.println(sb.toString());
    }

    @Test
    public void test11() {
        String str = "asdfghasd";
        System.out.println(StringUtils.countMatches(str, "asd"));

        String[] strArr = {"asdfghasd", "asdfghas", "asdasdasd"};

        Arrays.sort(strArr, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return StringUtils.countMatches(o1, "asd") - StringUtils.countMatches(o2, "asd");
                //return 1;
            }
        });


        System.out.println(Arrays.toString(strArr));

        Integer[] intArr = {1, 3, 4, 25, 6, 4};
        Arrays.sort(intArr);
        System.out.println(Arrays.toString(intArr));


        StringBuffer stringBuffer = new StringBuffer();
        for (int i : intArr) {
            stringBuffer.insert(0, i);
        }
        System.out.println(stringBuffer.toString());
    }

    @Test
    public void test12() throws IllegalAccessException, InstantiationException {
        //Class<TestSingle> clazz = TestSingle.class;

        //TestSingle testSingle = TestSingle.class.newInstance();
        //String str = String.class.newInstance();
        TestInstance testInstance = TestInstance.class.newInstance();
    }

    @Test
    public void test13() throws UnsupportedEncodingException {
        String str = "一";
        System.out.println(Arrays.toString(str.getBytes()));
        String str1 = new String(str.getBytes(), "iso8859-1");
        System.out.println(str1);
        System.out.println(Arrays.toString(str1.getBytes()));
        System.out.println(Arrays.toString(str1.getBytes("iso8859-1")));

        byte[] arr = {63};
        System.out.println(new String(arr, "iso8859-1"));

    }

    @Test
    public void test144444() throws InterruptedException {
        Set<String> set = new HashSet<>();

        for (int i = 0; i < 5000000; i++) {
            set.add(UUID.randomUUID().toString().replace("-", ""));
        }

        System.out.println(set.size());
    }

    @Test
    public void test14() throws InterruptedException {
        Set<String> set = new HashSet<>();

        new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                set.add(UUID.randomUUID().toString().replace("-", ""));
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                set.add(UUID.randomUUID().toString().replace("-", ""));
            }
        }).start();

        System.out.println(set.size());
    }

    @Test
    public void test15() throws InterruptedException {
        Set<String> set = new HashSet<>();
        class TestUUID extends Thread {

            @Override
            public void run() {
                for (int i = 0; i < 500; i++) {
                    set.add(UUID.randomUUID().toString().replace("-", ""));
                }
            }
        }

        Thread thread1 = new TestUUID();
        Thread thread2 = new TestUUID();
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(set.size());
    }

    @Test
    public void test16() throws InterruptedException {
        Set<String> set1 = new HashSet<>();
        ReentrantLock lock = new ReentrantLock();
        class TestUUId1 implements Runnable {
            private Set<String> set2 = new HashSet<>();

            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    lock.lock();
                    String id = UUID.randomUUID().toString().replace("-", "");
                    lock.unlock();
                    set1.add(id);
                    set2.add(id);
                }
            }
        }
        TestUUId1 testUUId = new TestUUId1();
        Thread thread1 = new Thread(testUUId);
        Thread thread2 = new Thread(testUUId);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println(set1.size());
        System.out.println(testUUId.set2.size());
    }

    @Test
    public void test17() throws InterruptedException {
        String str = "123";
        str.wait();
        str.notify();
        System.out.println(str);
    }

    @Test
    public void test18() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        System.out.println(list.toString());
    }



    @Test
    public void test20() {
        Pattern pattern = Pattern.compile("^[0-1a-zA-Z]$");

        Matcher matcher = pattern.matcher("哈哈");
        System.out.println(matcher.find());


    }

    @Test
    public void test21() {
        Map<Integer, Object> map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            map.put(i, UUID.randomUUID().toString());
        }
        long l1 = System.currentTimeMillis();
        System.out.println(map.get(344333));
        long l2 = System.currentTimeMillis();
        System.out.println("耗时:" + (l2 - l1));
    }

    class TestSingle {
        public TestSingle() {
        }
    }


}
