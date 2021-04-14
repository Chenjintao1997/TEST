package com.cjt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: chenjintao
 * @Date: 2019-11-28 18:38
 */
@RunWith(JUnit4.class)
public class ClassTest {
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    private static final ReentrantLock lock = new ReentrantLock();

    @Test
    public void test() throws InterruptedException {
        for (int i = 0; i < 50; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        Thread.sleep(3000);

    }

    @Test
    public void test2() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                try {
                    simpleDateFormat.parse("2019-12-22" + " 00:00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Test
    public void test3() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1);
        ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
        threadLocal2.set(2);
        System.out.println(threadLocal.get());
    }

    @Test
    public void test3a() {
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1);
        ThreadLocal<Integer> threadLocal2 = new ThreadLocal<>();
        threadLocal2.set(2);
        System.out.println(threadLocal.get());
        System.out.println(threadLocal2.get());

        executorService.execute(() -> {
            System.out.println(threadLocal.get());
            System.out.println(threadLocal2.get());

        });

        executorService.execute(() -> {
            threadLocal.set(1);
            threadLocal2.set(2);
            System.out.println(threadLocal.get());
            System.out.println(threadLocal2.get());

        });
    }

    @Test
    public void test4() throws IOException {
        Process process = Runtime.getRuntime().exec("who");

        try (InputStream is = process.getInputStream();
             InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                System.out.println(buffer);
            }
        }
    }

    @Test
    public void test5() throws InterruptedException {
        Set<Long> set = new HashSet<>();
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                Long id = Thread.currentThread().getId();
                set.add(id);
                System.out.println(id);
            });
        }

        Thread.sleep(5000);
        System.out.println("size:" + set.size());
    }

    @Test
    public void test6() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                Integer r = random.nextInt(10);
                list.add(r);

                if (map.containsKey(r)) {
                    Integer value = map.get(r);
                    value++;
                    map.put(r, value);
                } else {
                    map.put(r, 0);
                }
            });
        }

        Thread.sleep(5000);
        System.out.println("map:" + map.toString());

        Map<Integer, Integer> result = new ConcurrentHashMap<>();
        for (Integer i : list) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    if (result.containsKey(j)) {
                        Integer count = result.get(j);
                        count++;
                        result.put(j, count);
                    } else {
                        result.put(j, 0);
                    }
                    break;
                }
            }
        }
        System.out.println("result" + result);
    }

    @Test
    public void test7() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                Integer r = random.nextInt(10);
                list.add(r);

                lock.lock();
                if (map.containsKey(r)) {
                    Integer value = map.get(r);
                    value++;
                    map.put(r, value);
                } else {
                    map.put(r, 0);
                }
                lock.unlock();
            });
        }

        Thread.sleep(5000);
        System.out.println("map:" + map.toString());

        Map<Integer, Integer> result = new ConcurrentHashMap<>();
        for (Integer i : list) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    if (result.containsKey(j)) {
                        Integer count = result.get(j);
                        count++;
                        result.put(j, count);
                    } else {
                        result.put(j, 0);
                    }
                    break;
                }
            }
        }
        System.out.println("result" + result);
    }

    @Test
    public void test7ForException() throws InterruptedException {
        List<Integer> list = new CopyOnWriteArrayList<>();
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(() -> {
                Integer r = random.nextInt(10);
                System.out.println(r);
                list.add(r);

                lock.lock();

                if (r == 9)
                    throw new RuntimeException("123");

                if (map.containsKey(r)) {
                    Integer value = map.get(r);
                    value++;
                    map.put(r, value);
                } else {
                    map.put(r, 0);

                }

                lock.unlock();
            });
        }

        Thread.sleep(5000);
        System.out.println("map:" + map.toString());

        Map<Integer, Integer> result = new ConcurrentHashMap<>();
        for (Integer i : list) {
            for (int j = 0; j < 10; j++) {
                if (i == j) {
                    if (result.containsKey(j)) {
                        Integer count = result.get(j);
                        count++;
                        result.put(j, count);
                    } else {
                        result.put(j, 0);
                    }
                    break;
                }
            }
        }
        System.out.println("result" + result);
    }

    @Test
    public void testTryLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            reentrantLock.lock();
            System.out.println("thread1");
            reentrantLock.unlock();
            for (int i = 0; i < 50; i++) {
                reentrantLock.lock();
                System.out.println("thread1");
                reentrantLock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            if (reentrantLock.tryLock()) {
                System.out.println("thread2");
                reentrantLock.unlock();
            }

            for (int i = 0; i < 200; i++) {
                System.out.println(reentrantLock.isHeldByCurrentThread() + "," + reentrantLock.getHoldCount());
                if (reentrantLock.tryLock()) {
                    System.out.println(i + "-thread2");
                    System.out.println(reentrantLock.isHeldByCurrentThread() + "," + reentrantLock.getHoldCount());
                    reentrantLock.unlock();
                    //break;
                }
            }

//            reentrantLock.lock();
//            System.out.println("thread2");
//            reentrantLock.unlock();


        });

        Thread thread3 = new Thread(() -> {
            reentrantLock.lock();
            System.out.println("thread3");
            reentrantLock.unlock();
            for (int i = 0; i < 50; i++) {
                reentrantLock.lock();
                System.out.println("thread3");
                reentrantLock.unlock();
            }
        });

        Thread.sleep(1000);

        thread1.start();
        thread3.start();
        thread2.start();

        Thread.sleep(2000);
    }

    @Test
    public void testNotify1() throws InterruptedException {
        String str = "123";
        new Thread(() -> {
            synchronized (str) {
                System.out.println("--->wait:start");
                try {
                    str.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--->wait:end");

            }
        }).start();


        new Thread(() -> {
            System.out.println("notify");
            synchronized (str) {
                str.notify();
            }

        }).start();
    }

    @Test
    public void testNotify2() throws InterruptedException {
        String str = "123";
        new Thread(() -> {
                System.out.println("--->wait:start");
                try {
                    str.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--->wait:end");

        }).start();


        Thread.sleep(1000);
        new Thread(() -> {
            System.out.println("notify");
                str.notify();

        }).start();
    }

    @Test
    //必须使用synchronized锁，reentrantLock重入锁不行
    public void testNotify3() throws InterruptedException {
        String str = "123";
        new Thread(() -> {
            System.out.println("--->wait:start");
            try {
                lock.lock();
                str.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--->wait:end");
            lock.unlock();
        }).start();


        Thread.sleep(1000);
        new Thread(() -> {
            lock.lock();
            System.out.println("notify");
            str.notify();
            lock.unlock();
        }).start();
    }


    @Test
    public void testDaemon() throws InterruptedException {
        Thread thread = new Thread(() -> {
            Thread thread1 = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("我是守护线程");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.setDaemon(true);
            thread1.start();

            Thread thread2 = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("我是子线程");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread2.start();

            System.out.println("我是主进程");
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("主进程：我要结束了");
        });

        thread.start();

        Thread.sleep(10 * 1000);
    }

    @Test
    public void testDaemon1() throws InterruptedException {
        Thread thread = new Thread(() -> {
            Map<String, Boolean> map = new HashMap<>();
            map.put("flag", true);
            Thread thread1 = new Thread(() -> {
                while (map.get("flag")) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("我是守护线程");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.setDaemon(true);
            thread1.start();


            System.out.println("我是主进程");
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put("flag", false);
            System.out.println("主进程：我要结束了");
        });

        thread.start();

        Thread.sleep(10 * 1000);
    }

    @Test
    public void testDaemon2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            boolean[] flag = {true};
            Thread thread1 = new Thread(() -> {
                while (flag[0]) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("我是守护线程");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread1.setDaemon(true);
            thread1.start();


            System.out.println("我是主进程");
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag[0] = false;
            System.out.println("主进程：我要结束了");
        });

        thread.start();

        Thread.sleep(10 * 1000);
    }

    @Test
    public void testCallAble() throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            long id = Thread.currentThread().getId();
            String name = Thread.currentThread().getName();
            System.out.println(id+":"+name);
            return name;
        });

        Thread t1 = new Thread(futureTask);
        Thread t2 = new Thread(futureTask);



        t1.start();

        t2.start();
        String r1 = futureTask.get();
        System.out.println(r1);
        Thread.sleep(1*1000);

    }

    @Test
    public void testCallAble1() throws InterruptedException, ExecutionException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            long id = Thread.currentThread().getId();
            String name = Thread.currentThread().getName();
            System.out.println(id+":"+name);
            return name;
        });

        Thread t1 = new Thread(futureTask);

        t1.start();
        String r1 = futureTask.get();
        System.out.println(r1);

        Thread.sleep(1000);
        Thread t2 = new Thread(futureTask);         //拿同一个FutureTask实例作为线程的参数来启用线程并不会重复执行call方法，因为future对象有记录状态
        t2.start();
        String r2 = futureTask.get();
        System.out.println(r2);
    }

    @Test
    public void testExecutors(){
        ExecutorService executorService = Executors.newScheduledThreadPool(10);
    }
}
