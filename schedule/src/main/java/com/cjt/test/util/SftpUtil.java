package com.cjt.test.util;


import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

 public class SftpUtil {

    private String host;   //IP地址
    private Integer port;  //端口
    private String username;  //登陆用户名
    private String password;  //登陆密码

    public Session getSession() {
        return session;
    }

    private Session session;
    private ChannelSftp sftp;

    Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    public SftpUtil(String host, Integer port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }


    /**
     * 建立通道
     *
     * @return
     * @throws JSchException
     */
    public ChannelSftp getChannel() throws JSchException {
        // 创建JSch对象
        JSch jsch = new JSch();
        // 根据用户名，主机ip，端口获取一个Session对象
        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        Properties configTemp = new Properties();
        configTemp.put("StrictHostKeyChecking", "no");
        // 为Session对象设置properties
        session.setConfig(configTemp);
        // 设置timeout时间
        session.setTimeout(20000);
        session.connect();
        // 通过Session建立链接
        // 打开SFTP通道
        Channel channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        sftp = (ChannelSftp) channel;
        logger.info("Connected successfully to host = " + host + "," +
                "as username = " + username);
        return sftp;
    }

    /**
     * 断开SFTP Channel、Session连接
     *
     * @throws Exception
     */
    public void closeChannel() {
        if (sftp != null) {
            sftp.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        logger.info("disconnected SFTP successfully!");
    }


    /**
     * 上传单个文件
     *
     * @param filePath
     * @param fileName
     * @param content
     */
    public boolean uploadFile(String filePath, String fileName, String content) {
        boolean result = false;
        InputStream ins = null;
        try {
            ins = new ByteArrayInputStream(content.getBytes());
            ChannelSftp sftp = getChannel();
            createDir(filePath, sftp);
            sftp.put(ins, fileName);
            result = true;
        } catch (SftpException e) {
            logger.error("上传文件出错", e);
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                closeChannel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 读取sftp上面的txt文件
     *
     * @param fileName
     * @return
     */
    public List<String> readFile(String fileName) throws Exception {
        List<String> list = new ArrayList<>();
        ChannelSftp sftp = getChannel();
        InputStream inputStream = null;
        try {
            inputStream = sftp.get(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        } catch (SftpException | IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return list;
    }

    public InputStream getFile(String path) throws SftpException {
        return sftp.get(path);
    }


    /**
     * 判断目标路径是否存在，如果不存在，则进行创建
     *
     * @param createpath
     * @param sftp
     */
    public void createDir(String createpath, ChannelSftp sftp) throws SftpException {
        if (isDirExist(createpath)) {
            sftp.cd(createpath);
            return;
        }
        String pathArry[] = createpath.split("/");
        StringBuffer filePath = new StringBuffer("/");
        for (String path : pathArry) {
            if (path.equals("")) {
                continue;
            }
            filePath.append(path + "/");
            if (isDirExist(filePath.toString())) {
                sftp.cd(filePath.toString());
            } else {
                // 建立目录
                sftp.mkdir(filePath.toString());
                // 进入并设置为当前目录
                sftp.cd(filePath.toString());
            }
        }
    }

    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    public boolean isFileExist(String directory) {
        boolean isFileExistFlag = true;
        try {
            sftp.lstat(directory);
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isFileExistFlag = false;
            }
        }
        return isFileExistFlag;
    }

    public List<LsEntry> getFileInfo(String path) throws SftpException {
        List<LsEntry> allList = sftp.ls(path);
        List<LsEntry> list = new ArrayList<>();
        //过滤掉目录
        for (LsEntry lsEntry : allList) {
            SftpATTRS sftpATTRS = lsEntry.getAttrs();
            if (!sftpATTRS.isLink() && !sftpATTRS.isDir()) list.add(lsEntry);
        }
        return list;
    }

    public void mkdir(String path) throws SftpException {
        if (StringUtils.isBlank(path)) return;
        String[] dirArr = path.split(File.separator);
        String currentDir = dirArr[0];
        for (int i = 0; i < dirArr.length; i++) {
            if (i != 0) {
                currentDir += File.separator + dirArr[i];
            }
            if (StringUtils.isNotBlank(currentDir) && !isDirExist(currentDir))
                sftp.mkdir(currentDir);
        }

    }

//    public void mkdir(String path) throws SftpException {
//        String shell = "mkdir -p "+path;
//        sftp.
//    }


}
