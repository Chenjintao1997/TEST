package com.cjt.test.file.web.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author: chenjintao
 * @Date: 2019-07-04 13:33
 */

@RestController
@RequestMapping("/test/file")
@Api(value = "/test/file",description = "测试文件")
public class TestController {

    @GetMapping("/xlsx")
    public void getXlsx(HttpServletRequest request, HttpServletResponse response){

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        String fileName = "excel文件.xlsx";
        try(OutputStream os = response.getOutputStream()) {
            fileName = new String(fileName.getBytes("UTF-8"),"iso8859-1");
            response.setHeader("Content-Disposition","attachment;fileName="+fileName);
            InputStream is = new FileInputStream("/Users/chenjintao/work/sckm/test/工作簿1的副本.xlsx");

            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = is.read(buffer,0,2048))!=-1){
                os.write(buffer,0,bytesRead);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @GetMapping("/zip")
    public void getZip(HttpServletRequest request, HttpServletResponse response){

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        String fileName = "excel文件.zip";
        try(OutputStream os = response.getOutputStream()) {
            fileName = new String(fileName.getBytes("utf-8"),"iso8859-1");
            response.setHeader("Content-Disposition","attachment;fileName="+fileName);
            InputStream is = new FileInputStream("/Users/chenjintao/work/sckm/test/工作簿1.zip");

            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = is.read(buffer,0,2048))!=-1){
                os.write(buffer,0,bytesRead);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
