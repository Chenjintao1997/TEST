package com.cjt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: chenjintao
 * @Date: 2020-03-11 18:13
 */
@RunWith(JUnit4.class)
public class TestClass {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestClass.class);

    @Test
    public void test1() throws IOException, InterruptedException {
        //String cmd = "ls";
        String cmd = "/bin/cp /Users/chenjintao/work/CCOS/testshell//test1.txt /Users/chenjintao/work/CCOS/testshell/a";
        Process process = Runtime.getRuntime().exec(cmd);
        int i = process.waitFor();
        System.out.println(i);
        //process.getInputStream是用来读取控制台命令结果的
        //process.getOutputStream是用来往控制台写入参数的

//        OutputStream os = process.getOutputStream();
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
//        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        InputStream is = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

    }

    @Test
    public void test2() throws IOException, InterruptedException {
        //String cmd = "ls";
        String[] cmd = {"/bin/sh", "-c", "/bin/cp /Users/chenjintao/work/CCOS/testshell/test1.txt /Users/chenjintao/work/CCOS/testshell/a;" +
                "/bin/cp /Users/chenjintao/work/CCOS/testshell/test3.txt /Users/chenjintao/work/CCOS/testshell/a;" +
                "/bin/cp /Users/chenjintao/work/CCOS/testshell/test2.txt /Users/chenjintao/work/CCOS/testshell/a;"};

//        String[] cmd = {"/bin/sh","-c","/bin/cp /Users/chenjintao/work/CCOS/testshell/test1.txt /Users/chenjintao/work/CCOS/testshell/a"};
//        String cmd = "/bin/cp /Users/chenjintao/work/CCOS/testshell//test1.txt /Users/chenjintao/work/CCOS/testshell/a;/bin/cp /Users/chenjintao/work/CCOS/testshell//test2.txt /Users/chenjintao/work/CCOS/testshell/a ";
        //String dir = "/Users/chenjintao/IdeaProjects/Test/test-shell";
        Process process = Runtime.getRuntime().exec(cmd);
        int i = process.waitFor();
        System.out.println(i);
        //process.getInputStream是用来读取控制台命令结果的
        //process.getOutputStream是用来往控制台写入参数的

//        OutputStream os = process.getOutputStream();
//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os);
//        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        InputStream is = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }

    }

    @Test
    public void test3() throws InterruptedException, IOException {
        StringBuilder stringBuilder = new StringBuilder();

        //shell命令长度限制262144，通过getconf ARG_MAX查看
        for (int i = 0; i < 3000; i++) {
            String str = "/bin/cp /Users/chenjintao/work/CCOS/testshell/a.mp3 /Users/chenjintao/work/CCOS/testshell/a;";
            stringBuilder.append(str);
        }
        String[] cmd = {"/bin/sh", "-c", stringBuilder.toString()};
        Process process = Runtime.getRuntime().exec(cmd);
        int i = process.waitFor();
        System.out.println(i);
    }

    @Test
    public void test4() throws InterruptedException, IOException {
        for (int i = 0; i < 2000; i++) {
            String[] cmd = {"/bin/sh", "-c", "/bin/cp /Users/chenjintao/work/CCOS/testshell/a.mp3 /Users/chenjintao/work/CCOS/testshell/a;"};
            Process process = Runtime.getRuntime().exec(cmd);
            int j = process.waitFor();
            //System.out.println(j);
        }
    }

    @Test
    public void test5() throws InterruptedException, IOException {
        for (int i = 0; i < 1; i++) {
            String str = "if [ ! -d \"/Users/chenjintao/work/CCOS/testshell/c\" ];then mkdir -p \"/Users/chenjintao/work/CCOS/testshell/c\";/bin/cp /Users/chenjintao/work/CCOS/testshell/a.mp3 /Users/chenjintao/work/CCOS/testshell/c; else /bin/cp /Users/chenjintao/work/CCOS/testshell/a.mp3 /Users/chenjintao/work/CCOS/testshell/c; fi";
            String[] cmd = {"/bin/sh", "-c", str};
            Process process = Runtime.getRuntime().exec(cmd);
            int j = process.waitFor();
            System.out.println(j);
        }
    }

    @Test
    public void testStrFormat() throws IOException, InterruptedException {


        for (int i = 0; i < 10; i++) {
            String str = String.format("if [ ! -d \"%s\" ];then mkdir -p \"%s\"; else /bin/cp %s %s; fi",
                    "/Users/chenjintao/work/CCOS/testshell/c",
                    "/Users/chenjintao/work/CCOS/testshell/c",
                    "/Users/chenjintao/work/CCOS/testshell/a.mp3",
                    "/Users/chenjintao/work/CCOS/testshell/c");

            System.out.println(str);
            String[] cmd = {"/bin/sh", "-c", str};
            Process process = Runtime.getRuntime().exec(cmd);
            int j = process.waitFor();
            System.out.println(j);
        }
    }

    @Test
    public void test() {
        Integer i = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        String str = String.valueOf(i);
        i = 1;

        System.out.println(sb.toString());
        System.out.println(str);

    }

    @Test
    public void testException() {
        try {
            throw new RuntimeException("123");
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        System.out.println("00000000000000000000");
    }

}
