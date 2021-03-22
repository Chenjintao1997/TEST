package com.cjt.test.schedule;

import com.cjt.test.util.SftpUtil;
import com.cjt.test.util.triplet.SimpleTripletSource;
import com.cjt.test.util.triplet.Triplet;
import com.cjt.test.util.triplet.TripletSource;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FilenameUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author: chenjintao
 * @Date: 2020/7/31 17:21
 */
@RunWith(JUnit4.class)
public class TaskTest {
    
    private final static Logger LOGGER = LoggerFactory.getLogger(TaskTest.class);

    @Test
    public void testFtp() throws IOException, JSchException, SftpException {
        Date now = new Date();
        //计算当前日期的信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowTimeFormat = sdf.format(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);


        //声明连接sftp的信息
        String host = "123.57.89.226";
        Integer port = 22;
        String username = "guest";
        String password = "Founder123@";
        String dataPathPrefix = "/home/guest/data";
        String reportPathPrefix = "/home/guest/report";

        //声明文件名称信息
        String sftpFileFormatInfo = "i_10000_" + year + month + day + hour + "_VGOP1-R2.10-98201";

        //fixme 测试先用以下的，生产再修改
        //String datePath = year + month + day + File.pathSeparator + hour;
        String datePath = "20200513/12";

        String dataPath = dataPathPrefix + File.separatorChar + datePath;   //数据提供方文件路劲，---不带文件名
        String reportPath = reportPathPrefix + File.separatorChar + year + month + day + File.separatorChar + hour;   //todo  日期路劲在生产环境可更换为datePath

        SftpUtil sftpUtil = new SftpUtil(host, port, username, password);
        ChannelSftp channelSftp = null;
        //打开连接
        try {
            channelSftp = sftpUtil.getChannel();
        } catch (JSchException e) {
            LOGGER.error("sftp服务器连接失败", e);
            Throwable throwable = e.getCause();
            sftpUtil.closeChannel();
            if (throwable instanceof UnknownHostException) {
                LOGGER.error("sftp服务器连接失败，该host地址无法连接，请检查host地址是否正确");
                //throw new GeneralException("-3", "sftp服务器连接失败，该host地址无法连接，请检查host地址是否正确");
            } else if (throwable instanceof ConnectException) {
                LOGGER.error("sftp服务器连接失败，连接拒绝，请检查端口等配置是否正确");
                //  throw new GeneralException("-4", "sftp服务器连接失败，连接拒绝，请检查端口等配置是否正确");
            } else if ("Auth fail".equalsIgnoreCase(e.getMessage())) {
                LOGGER.error("sftp服务器连接失败，认证失败，请检查用户名或密码是否正确");
                //   throw new GeneralException("-5", "sftp服务器连接失败，认证失败，请检查用户名或密码是否正确");
            } else if ("timeout: socket is not established".equalsIgnoreCase(e.getMessage())) {
                LOGGER.error("sftp服务器连接失败，认证超时，请检查配置信息是否正确");
                // throw new GeneralException("-6", "sftp服务器连接失败，认证超时，请检查配置信息是否正确");
            } else {
                LOGGER.error("sftp服务器连接失败，请检查配置信息是否正确");
                // throw new GeneralException("-7", "sftp服务器连接失败，请检查配置信息是否正确");
            }
        }
        if (channelSftp == null) {
            LOGGER.error("sftp服务器连接失败，请检查配置信息是否正确");
            //fixme return随后干掉
            return;
            // throw new GeneralException("-7", "sftp服务器连接失败，请检查配置信息是否正确");
        }


        //获取校验文件和数据文件
        List<ChannelSftp.LsEntry> FileList = new ArrayList<>();
        try {
            FileList = sftpUtil.getFileInfo(dataPath);
        } catch (SftpException se) {
            LOGGER.error("sftp服务器文件读取失败", se);
            sftpUtil.closeChannel();
            if ("No such file".equalsIgnoreCase(se.getMessage())) {
                LOGGER.error("没有在sftp服务器上找到数据提供方提供的文件，请检查路径配置是否正确");
                //todo 确定校验文件不存在是否需要做处理
            }
        }

        if (FileList.isEmpty()) {
            LOGGER.error("校验文件不存在");
            //todo 确定校验文件不存在是否需要做处理
        }

        boolean isExistVerf = false;    //声明是否存在校验文件

        //todo 计算重传次数，这里先默认设为0
        int reUploadCount = 0;
        String reUploadCountStr = reUploadCount < 10 ? "0" + reUploadCount : String.valueOf(reUploadCount);
        //todo

        //声明记录级校验文件的输出流，多对一关系
        if (!sftpUtil.isDirExist(reportPath)) {
            sftpUtil.mkdir(reportPath);
        }
        String recordReportFileName = "r_" + sftpFileFormatInfo + "_" + reUploadCountStr + ".verf";       //声明记录级校验报告文件名称
        String recordReportFilePath = reportPath + File.separatorChar + recordReportFileName;     //声明记录级校验报告文件路劲
        OutputStream osForRecordReport = channelSftp.put(recordReportFilePath);                   //声明声明记录级校验文件的输出流
        OutputStreamWriter oswForRecordReport = new OutputStreamWriter(osForRecordReport, "GBK");
        BufferedWriter bwForRecordReport = new BufferedWriter(oswForRecordReport);


        //解析接口校验文件和数据文件
        for (ChannelSftp.LsEntry lsEntry : FileList) {
            //todo 确定是否会有重复获取
            String filename = lsEntry.getFilename();
            String extension = FilenameUtils.getExtension(filename);

            if ("verf".equals(extension)) {
                isExistVerf = true;
                //todo 文件级校验

                //todo

                //获取接口校验文件内容
                String verfFilePath = dataPath + File.separatorChar + filename;
                InputStream verfIs = channelSftp.get(verfFilePath);
                TripletSource tripletsForVerf = new SimpleTripletSource(verfIs);
                for (Triplet triplet : tripletsForVerf) {
                    String dataFileName = triplet.get("field0");
                    long dataFileSize = Long.parseLong(triplet.get("field1"));
                    int dataCount = Integer.parseInt(triplet.get("field2"));
                    String dataDate = triplet.get("field3");
                    String dataGenerationTime = triplet.get("field4");

                    //todo 文件级校验

                    //todo

                    System.out.println("----------->接口校验文件内容：" + triplet.toString());

                    //文件级校验报告文件生成及上传   一个数据文件对应一个文件级校验报告
                    String fileReportFileName = "f_" + FilenameUtils.getBaseName(dataFileName) + ".verf";               //文件级校验报告文件名称
                    String fileReportFilePath = reportPath + File.separatorChar + fileReportFileName;                       //文件级校验报告文件路劲

                    OutputStream osForFileReport = channelSftp.put(fileReportFilePath);
                    OutputStreamWriter oswForFileReport = new OutputStreamWriter(osForFileReport, "GBK");
                    BufferedWriter bwForFileReport = new BufferedWriter(oswForFileReport);

                    //todo fileReportCode的不同值的真实获取
                    String fileReportCode = "00";
                    String fileReportLineContent = dataFileName + "€" + nowTimeFormat + "€" + fileReportCode;
                    bwForFileReport.write(new String(fileReportLineContent.getBytes()));
                    bwForFileReport.newLine();
                    bwForFileReport.flush();

                    //获取接口数据文件内容
                    String dataFilePath = dataPath + File.separatorChar + dataFileName;
                    LOGGER.info("----------->获取接口数据文件：" + dataFilePath);
                    if (!sftpUtil.isFileExist(dataFilePath)) {
                        LOGGER.error("-11", "\"" + dataFilePath + "\"" + "文件不存在");
                        //todo 文件级校验处理

                        //todo

                        continue;
                    }

                    InputStream dataIs = channelSftp.get(dataFilePath);
                    TripletSource tripletsForData = new SimpleTripletSource(dataIs);

                    for (Triplet data : tripletsForData) {
                        int num = Integer.parseInt(data.get("field0"));
                        String timeStr = data.get("field1");
                        String guestGroupId = data.get("field2");
                        String guestGroupName = data.get("field3");
                        String guestListId = data.get("field4");

                        //todo 记录级校验

                        //todo

                        System.out.println(data.toString());

                        //记录级校验报告文件内容生成
                        //todo  fileReportCode的不同值的真实获取
                        String recordReportCode = "000000000";
                        String numForContent = "000000000".equals(recordReportCode) ? " " : String.valueOf(num);
                        String recordReportLineContent = dataFileName + "€" + numForContent + "€" + recordReportCode;
                        bwForRecordReport.write(new String(recordReportLineContent.getBytes()));
                        bwForRecordReport.newLine();

                    }


                }

            }
        }

        bwForRecordReport.flush();


    }

    @Test
    public void testStr() {
        String str = "i_10000_2020042712_VGOP1-R2.10-98201_00.verf";
        String verfCurrentReUploadNum = str.substring(str.lastIndexOf("_") + 1, str.lastIndexOf("."));
        System.out.println(verfCurrentReUploadNum);
    }

    @Test
    public void testStr2(){
        String str = "654321";
        System.out.println(str.substring(str.length()-5));

    }

    @Test
    public void testBool(){
        boolean b1 = !(true && true) && true;
        boolean b2 = !(true && false) && true;
        System.out.println(b1);
        boolean b3 = !(true && false) && false;
        boolean b4 = !((true && false) && false);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b4);
    }

    @Test
    public void testClose() throws JSchException, SftpException, InterruptedException {
        String host = "123.57.89.226";
        Integer port = 22;
        String username = "guest";
        String password = "Founder123@";
        SftpUtil sftpUtil = new SftpUtil(host,port,username,password);
        ChannelSftp channel = sftpUtil.getChannel();

        Thread.sleep(21*1000);
        System.out.println(channel.pwd());
        Session session = sftpUtil.getSession();
        session.disconnect();

    }
    


}
