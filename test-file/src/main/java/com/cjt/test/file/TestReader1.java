package com.cjt.test.file;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2021/1/8 12:32
 */
public class TestReader1 {
    private final static String FILE_PATH = "./file/";

    public File[] readFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String dateStr = sdf.format(new Date());
//        String fileNamespaceRegex = String.format("%s_\\d", dateStr);
        String fileNamespaceRegex = sdf.format(new Date());
        File parentPathFile = new File(FILE_PATH);

        return parentPathFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(fileNamespaceRegex);
            }
        });
    }

    public List<Map<String, String>> analysisFile(File[] fileArr, String divider) throws IOException {
        String fileCharset = "UTF-8";
        List<Map<String, String>> list = new ArrayList<>();
        if (fileArr == null || fileArr.length < 1) return list;
        for (File file : fileArr) {
            if (!file.isFile()) continue;
            String fileName = file.getName();
            //String fileNameNoExt = FilenameUtils.getBaseName(fileName);//如果fileid要求不携带文件扩展名就用这个
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), fileCharset));) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    String[] tokenArr = line.split(divider, -1);
                    Map<String, String> map = new HashMap<>();
                    for (int i = 0; i < tokenArr.length; i++) {
                        map.put("field" + i, tokenArr[i]);
                        map.put("fileId", fileName);
                    }
                    list.add(map);
                }
            }
        }
        return list;

    }
}
