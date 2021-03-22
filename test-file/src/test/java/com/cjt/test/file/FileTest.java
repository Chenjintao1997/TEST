package com.cjt.test.file;

import com.alibaba.fastjson.JSON;
import com.cjt.test.file.util.triplet.SimpleTripletSource;
import com.cjt.test.file.util.triplet.Triplet;
import com.cjt.test.file.util.triplet.TripletSource;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2020-05-16 19:02
 */
@RunWith(JUnit4.class)
public class FileTest {

    @Test
    public void test1() {
        System.out.println(File.separator);
        System.out.println(File.pathSeparator);
    }

    @Test
    public void testCsv() {
        //获取需要过滤的数据
        Set<String> excludeGuest = new HashSet<>();                 //定义需要过滤的潜客数据集合
        Collection<File> excludeFileList = FileUtils.listFiles(new File("./exclude"), null, true);
        if (!excludeFileList.isEmpty()) {
            for (File excludeFile : excludeFileList) {
                TripletSource tripletSource = new SimpleTripletSource(excludeFile, "UTF-8", "|");
                for (Triplet triplet : tripletSource) {
                    String guestListId = triplet.get("field0");
                    excludeGuest.add(guestListId);
                }
            }
        }

        System.out.println(excludeGuest.size());
    }

    @Test
    public void testCsv1() {
        File file = new File("./exclude/TEST_20200520.txt");
        TripletSource tripletSource = new SimpleTripletSource(file, "GBK", "\\|");
        for (Triplet triplet : tripletSource) {
            System.out.println(JSON.toJSONString(triplet));
            String field0 = triplet.get("field0");
            String field1 = triplet.get("field1");

            System.out.println(field0);

            System.out.println(field1);
        }
    }

    @Test
    public void testReader1() throws IOException {
        TestReader1 testReader1 = new TestReader1();
        File[] fileArr = testReader1.readFile();
        List<Map<String, String>> list = testReader1.analysisFile(fileArr, "\\|");
        System.out.println(JSON.toJSONString(list));

    }

    @Test
    public void testReader2() throws IOException {
        System.out.println(JSON.toJSONString(TestReader2.readFile()));

    }

}
