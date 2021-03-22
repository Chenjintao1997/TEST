package com.cjt.test.util.schedule;

import com.cjt.test.util.SftpUtil;
import com.cjt.test.util.triplet.SimpleTripletSource;
import com.cjt.test.util.triplet.Triplet;
import com.cjt.test.util.triplet.TripletSource;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2020-05-20 11:06
 */
public class GuestTask extends Task {
    private final static Logger LOGGER = LoggerFactory.getLogger(Task.class);

    @Autowired
    private TaskManager taskManager;

    private Date baseTime;

//    @Value("${spring.profile.active}")
//    private String active;

    @Override
    public void execute() throws Exception {
        LOGGER.info("------------------>定时任务开始执行：获取sftp服务器上的数据文件");
        Long eventBeginTime = System.currentTimeMillis();

        Thread.sleep(50 * 1000);

        Date now = new Date();
        Date currentBaseTime = baseTime != null ? baseTime : now;  //计算基本单位时间（文件同步所属时间段），如果成员变量为null，则使用当前时间为基本单元时间

        String syncRecordId = job(currentBaseTime);

        //判断是否需要进行重试获取当前时间段的sftp数据----syncRecordId不为null表示需要
        if (StringUtils.isNotBlank(syncRecordId)) {
            Calendar calendar = Calendar.getInstance();
            GuestTask task = new GuestTask();

            //计算下次要执行的时间，且创建的定时任务只执行一次
            calendar.add(Calendar.MINUTE, 1);    //如果校验有错误，就10分钟后在执行一次
            String restartMin = String.valueOf(calendar.get(Calendar.MINUTE));
            String restartHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            String restartDayOfMonth = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            String restartMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String restartDayOfWeek = "?";
            String restartYear = String.valueOf(calendar.get(Calendar.YEAR));
            StringBuilder sb = new StringBuilder();
            sb.append("0").append(" ")
                    .append(restartMin).append(" ")
                    .append(restartHour).append(" ")
                    .append(restartDayOfMonth).append(" ")
                    .append(restartMonth).append(" ")
                    .append(restartDayOfWeek).append(" ")
                    .append(restartYear);
            String cron = sb.toString();
            task.setCronExp(cron);
            task.setBaseTime(currentBaseTime);


            taskManager.addTask(syncRecordId, task);  //添加定时任务
            LOGGER.info("----------->添加重试获取sftp数据定时任务，预计下次执行时间：" + calendar.getTime());

            Long eventEndTime = System.currentTimeMillis();
            LOGGER.info("------------------>定时任务执行完毕：获取sftp服务器上的数据文件，耗时：" + (eventEndTime - eventBeginTime) + "mm");
        }


    }

    public String job(Date baseTime) throws IOException, SftpException {
        Date now = new Date();
        Date formatBaseTime = convertTime(baseTime);

        //声明当前环境是否为生产环境  todo 根据判断系统配置文件属性定义
        boolean isProduction = false;

        //计算当前日期的信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowTimeFormat = sdf.format(now);

        //todo 计算重传次数，这里先默认设为0
        int reUploadCount = 0;
        String reUploadCountStr = reUploadCount < 10 ? "0" + reUploadCount : String.valueOf(reUploadCount);
        LOGGER.info("----------->计算重传次数，当前时间段为第" + (reUploadCount + 1) + "次执行");
        //todo

        //获取基本单元时间的各个时间属性
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatBaseTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        //fixme 测试先用以下的，生产再修改
        //String datePath = year + month + day + File.pathSeparator + hour;
        String datePath = isProduction ? year + month + day + File.pathSeparator + hour : "20200513/12";                      //日期路劲
        String baseTimeStr = "" + year + month + day + hour;  //基本单位时间格式化字符串

        //声明连接sftp的信息
        String host = "123.57.89.226";
        Integer port = 22;
        String username = "guest";
        String password = "Founder123@";
        String dataPathPrefix = "/home/guest/data";
        String reportPathPrefix = "/home/guest/report";


        String dataPath = dataPathPrefix + File.separatorChar + datePath;            //数据提供方文件路劲，---不带文件名
        String reportPath = reportPathPrefix + File.separatorChar + year + month + day + File.separatorChar + hour;   //数据接收方文件路劲 todo  日期路劲在生产环境可更换为datePath

        LOGGER.info("----------->开始连接SFTP服务器");
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
            return null;
            // throw new GeneralException("-7", "sftp服务器连接失败，请检查配置信息是否正确");
        }
        LOGGER.info("----------->SFTP服务器连接成功");


        //声明文件名称信息
        String sftpUnitCode = "VGOP1-R2.10-98201";   //sftp接口单元编码
        String sftpFileFormatInfo = "i_10000_" + baseTimeStr + "_" + sftpUnitCode + "_" + reUploadCountStr;

        boolean fileReportVerf = true;    //声明文件级校验是否通过
        boolean recordReportVerf = true;  //声明记录级校验是否通过
        int reUploadMaxCount = 3;         //定义同一时间段的数据接收方自动最大重传（重试）次数---最大自动创建定时任务的次数


        //获取校验文件和数据文件
        LOGGER.info("----------->开始获取校验文件和数据文件");
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


        //声明记录级校验文件的输出流，多对一关系
        if (!sftpUtil.isDirExist(reportPath)) {
            sftpUtil.mkdir(reportPath);
        }
        String recordReportFileName = "r_" + sftpFileFormatInfo + ".verf";       //声明记录级校验报告文件名称
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
                LOGGER.info("----------->开始解析接口校验文件内容");
                //根据重传序号判断是否是本次定时任务需要获取的文件
                String verfCurrentReUploadNum = filename.substring(filename.lastIndexOf("_") + 1, filename.lastIndexOf("."));
                if (!reUploadCountStr.equals(verfCurrentReUploadNum) && isProduction) continue;


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

                    String dataFileNum = dataFileName.substring(dataFileName.lastIndexOf("_") + 1, dataFileName.lastIndexOf("."));   //获取数据文件序列号
                    String dataFilePath = dataPath + File.separatorChar + dataFileName;               //接口数据文件路劲
                    String[] dataFileNameSplitArr = dataFileName.split("_");


                    LOGGER.info("----------->接口校验文件内容：" + triplet.toString());

                    //文件级校验报告文件生成及上传   一个数据文件对应一个文件级校验报告
                    String fileReportFileName = "f_" + sftpFileFormatInfo + "_" + dataFileNum + ".verf";                   //文件级校验报告文件名称
                    String fileReportFilePath = reportPath + File.separatorChar + fileReportFileName;                       //文件级校验报告文件路劲

                    OutputStream osForFileReport = channelSftp.put(fileReportFilePath);
                    OutputStreamWriter oswForFileReport = new OutputStreamWriter(osForFileReport, "GBK");
                    BufferedWriter bwForFileReport = new BufferedWriter(oswForFileReport);

                    LOGGER.info("----------->开始进行文件级校验");
                    //fileReportCode的不同值的真实获取
                    String fileReportCode = "00";
                    //文件级校验----start todo 目前只做了部分的必要性高的校验
                    if (isProduction) {
                        if (!sftpUtil.isFileExist(dataFilePath)) {
                            LOGGER.error("-11", "\"" + dataFilePath + "\"" + "文件不存在");
                            fileReportCode = "02";
                        } else if (!dataDate.equals(dataFileNameSplitArr[2])) {
                            fileReportCode = "07";
                        } else if (!(baseTimeStr).equals(dataDate)) {
                            fileReportCode = "08";
                        } else if (!(sftpUnitCode.equals(dataFileNameSplitArr[4]))) {
                            fileReportCode = "10";
                        }
                    }
                    LOGGER.info("----------->文件级校验结果：" + fileReportCode);
                    //文件级校验----end


                    LOGGER.info("----------->生成文件级校验报告文件并上传服务器");
                    //生成报告内容
                    String fileReportLineContent = dataFileName + "€" + nowTimeFormat + "€" + fileReportCode;
                    bwForFileReport.write(new String(fileReportLineContent.getBytes()));
                    bwForFileReport.newLine();
                    bwForFileReport.flush();

                    //判断文件级校验是否通过
                    if (isProduction) {
                        if (!"00".equals(fileReportCode)) {
                            fileReportVerf = false;
                            continue;                    //不通过-->进入获取下个接口数据文件信息的循环
                        }
                    }

                    //获取接口数据文件内容
                    LOGGER.info("----------->开始获取接口数据文件：" + dataFilePath);
                    InputStream dataIs = channelSftp.get(dataFilePath);
                    TripletSource tripletsForData = new SimpleTripletSource(dataIs);

                    for (Triplet data : tripletsForData) {
                        String num = data.get("field0");
                        String timeStr = data.get("field1");
                        String guestGroupId = data.get("field2");
                        String guestGroupName = data.get("field3");
                        String guestListId = data.get("field4");


                        System.out.println(data.toString());


                        LOGGER.info("----------->开始进行记录级校验");
                        //fileReportCode的不同值的真实获取
                        String recordReportCode = "000000000";
                        //记录级校验----start todo 目前只做了日期的校验
                        if ((baseTimeStr).equals(timeStr)) {
                            recordReportCode = sftpUnitCode.substring(sftpUnitCode.length() - 5) + "002" + 5;
                            recordReportVerf = false;
                        }
                        //记录级校验----end
                        LOGGER.info("----------->记录级校验结果：" + recordReportCode);

                        LOGGER.info("----------->生成记录级校验报告内容");
                        //记录级校验报告文件内容生成
                        String numForContent = "000000000".equals(recordReportCode) ? " " : num;
                        String recordReportLineContent = dataFileName + "€" + numForContent + "€" + recordReportCode;
                        bwForRecordReport.write(new String(recordReportLineContent.getBytes()));
                        bwForRecordReport.newLine();

                    }


                }

            }
        }

        LOGGER.info("----------->生成记录级校验报告文件并上传服务器");
        //todo 研究下如何根据条件判断不写入文件
        bwForRecordReport.flush();

        LOGGER.info("----------->SFTP文件数据同步信息保存入库");
        //定义记录sftp同步信息的id，同时作为定时任务task对象注入spring的标识
        String syncRecordId = UUID.randomUUID().toString();  //todo 后期改成IdWorker获取

        //todo 记录同步信息 入库

        //判断是否需要增加定时任务
        if (!(fileReportVerf && recordReportVerf && reUploadCount < reUploadMaxCount)) {
            return syncRecordId;
        }

        return null;

    }

    public Date getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(Date baseTime) {
        this.baseTime = baseTime;
    }

    /**
     * 转换时间为分秒都为0的时间
     *
     * @param date
     * @return
     */
    private Date convertTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}
