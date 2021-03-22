package com.cjt.test.file;

import com.cjt.test.bean.TestBean1;
import com.cjt.test.file.util.triplet.SimpleTripletSource;
import com.cjt.test.file.util.triplet.Triplet;
import com.cjt.test.file.util.triplet.TripletSource;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: chenjintao
 * @Date: 2021/1/10 15:54
 */
public class TestReader2 {
    private final static String FILE_PATH = "./file/";

    public static List<TestBean1> readFile() throws IOException {
        List<TestBean1> bean1List = new ArrayList<>(); //testBean是模拟的实体bean
        String fileCharset = "UTF-8";
        String batchId = UUID.randomUUID().toString().replace("-", "");//声明批次id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fileNamespaceRegex = sdf.format(new Date());
        File parentPathFile = new File(FILE_PATH);

        File[] fileArr = parentPathFile.listFiles((dir, name) -> name.contains(fileNamespaceRegex));

        if (fileArr == null || fileArr.length < 1) return bean1List;
        for (File file : fileArr) {
            if (!file.isFile()) continue;
            String fileName = file.getName();
            //String fileNameNoExt = FilenameUtils.getBaseName(fileName);//如果fileid要求不携带文件扩展名就用这个
            try (TripletSource tripletSource = new SimpleTripletSource(file, fileCharset, "\\|");
            ) {
                for (Triplet triplet : tripletSource) {
                    String day = triplet.get("field0");
                    String age = triplet.get("field1");
                    String context = triplet.get("field1");

                    TestBean1 testBean1 = new TestBean1();//创建实体bean对象
                    testBean1.setBatchId(batchId);
                    testBean1.setFileId(fileName);
                    testBean1.setDay(day);
                    testBean1.setAge(age);
                    testBean1.setContext(context);
                    bean1List.add(testBean1);
                }
            }
        }
        return bean1List;
    }
}
