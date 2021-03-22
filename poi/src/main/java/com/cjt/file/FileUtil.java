package com.cjt.file;

import java.io.*;

/**
 * @Author: chenjintao
 * @Date: 2019-04-11 16:15
 */
public class FileUtil {

    private static final String ENCODING = "UTF-8";

    public static boolean createHtmlDir(String path) {
        int i = path.lastIndexOf('/');
        String dirPath = "";
        if (i > 0 && i < path.length() - 1) {
            dirPath = path.substring(0, i).toLowerCase();
        }
        File dir = new File(dirPath);
        if (!dir.exists()) return dir.mkdir();
        return true;
    }

    public static void writeFile(String content, String path) {
        createHtmlDir(path);

        File file = new File(path);
        try (OutputStream os = new FileOutputStream(file);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, ENCODING))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getFileName(String filePath, boolean withExtension) {
        int sep = filePath.lastIndexOf("\\") == -1 ? filePath.lastIndexOf("/") : filePath.lastIndexOf("\\");
        return withExtension ? filePath.substring(sep + 1) : filePath.substring(sep + 1, filePath.lastIndexOf("."));
    }


}
