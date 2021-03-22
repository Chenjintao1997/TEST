package com.cjt.test.util;


import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class SftpUtil1 {

    private String host;   //IP地址
    private Integer port;  //端口
    private String username;  //登陆用户名
    private String password;  //登陆密码


    private Session session;
    private HashMap<String, Channel> channelMap = new HashMap<>();

    Logger logger = LoggerFactory.getLogger(SftpUtil1.class);

    public SftpUtil1(String host, Integer port, String username, String password) {
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
    public ChannelSftp createChannel() throws JSchException {
        // 通过Session建立链接
        // 打开SFTP通道
        Channel channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        String id = String.valueOf(sftp.getId());
        channelMap.put(id, channel);
        logger.info("channel connected successfully to host = " + host + "," +
                "as username = " + username);
        return sftp;
    }

    /**
     * 建立通道
     *
     * @return
     * @throws JSchException
     */
    public ChannelSftp createChannel(String id) throws JSchException {
        // 通过Session建立链接
        // 打开SFTP通道
        Channel channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        ChannelSftp sftp = (ChannelSftp) channel;
        channelMap.put(id, channel);
        logger.info("channel connected successfully to host = " + host + "," +
                "as username = " + username);
        return sftp;
    }

    /**
     * 建立会话
     *
     * @return
     * @throws JSchException
     */
    public Session createSession() throws JSchException {
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
        logger.info("session connected successfully to host = " + host + "," +
                "as username = " + username);
        return session;
    }

    /**
     * 关闭与服务器的会话连接
     */
    public void closeSession(){
        if (session == null || !session.isConnected()) {
            return;
        }
        session.disconnect();
        channelMap.clear();
        logger.info("close session successfully!");
    }

    /**
     * 断开指定Channel
     */
    public void closeChannel(String id) {
        Channel channel = channelMap.get(id);
        closeChannel(channel);
        logger.info("close channel:" + id + " successfully!");
    }

    /**
     * 断开指定Channel
     */
    public void closeChannel(Channel channel) {
        if (session == null || !session.isConnected()) {
            return;
        }
        if (channel == null || channel.isClosed()) return;
        channel.disconnect();
        channelMap.values().remove(channel);
        logger.info("close channel successfully!");
    }

    /**
     * 断开全部Channel
     */
    public void closeChannel() {
        if (session == null || !session.isConnected()) {
            return;
        }
        for (Channel channel : channelMap.values()) {
            channel.disconnect();
        }
        channelMap.clear();
        logger.info("close channel successfully!");
    }


    /**
     * 上传单个文件
     */
    public boolean uploadFile(String channelId, String filePath, String fileName, String content) {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        return uploadFile(sftp, filePath, fileName, content);
    }

    /**
     * 上传单个文件
     */
    public boolean uploadFile(Channel channel, String filePath, String fileName, String content) {
        boolean result = false;
        InputStream ins = null;
        try {
            ins = new ByteArrayInputStream(content.getBytes());
            ChannelSftp sftp = (ChannelSftp) channel;
            createDir(sftp, filePath);
            sftp.put(ins, fileName);
            result = true;
        } catch (SftpException e) {
            logger.error("上传文件出错", e);
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
     */
    public List<String> readFile(String channelId, String fileName, String charSet) throws Exception {

        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        return readFile(sftp, fileName, charSet);
    }

    /**
     * 读取sftp上面的txt文件
     */
    public List<String> readFile(Channel channel, String fileName, String charSet) throws Exception {
        List<String> list = new ArrayList<>();
        ChannelSftp sftp = (ChannelSftp) channel;
        InputStream inputStream = null;
        try {
            inputStream = sftp.get(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charSet));
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

    /**
     * 读取sftp上面的txt文件
     */
    public List<String> readFile(String channelId, String fileName) throws Exception {
        return readFile(channelId, fileName, "UTF-8");
    }

    /**
     * 获取服务器指定文件
     *
     * @param channelId
     * @param path
     * @return
     * @throws SftpException
     */
    public InputStream getFile(String channelId, String path) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        return getFile(sftp, path);
    }

    /**
     * 获取服务器指定文件
     */
    public InputStream getFile(Channel channel, String path) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) channel;
        return sftp.get(path);
    }

    /**
     * 判断目标路径是否存在，如果不存在，则进行创建
     */
    public void createDir(String channelId, String createpath) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        createDir(sftp, createpath);
    }

    /**
     * 判断目标路径是否存在，如果不存在，则进行创建
     */
    public void createDir(Channel channel, String createpath) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) channel;
        if (isDirExist(sftp, createpath)) {
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
            if (isDirExist(sftp, filePath.toString())) {
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
    public boolean isDirExist(Channel channel, String directory) {
        ChannelSftp sftp = (ChannelSftp) channel;
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

    /**
     * 判断目录是否存在
     */
    public boolean isDirExist(String channelId, String directory) {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        return isDirExist(sftp, directory);
    }

    /**
     * 判断文件是否存在
     *
     * @param channel
     * @param directory
     * @return
     */
    public boolean isFileExist(Channel channel, String directory) {
        ChannelSftp sftp = (ChannelSftp) channel;
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

    /**
     * 判断文件是否存在
     */
    public boolean isFileExist(String channelId, String directory) {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        return isFileExist(sftp, directory);
    }

    /**
     * 获取文件列表
     *
     * @param channel
     * @param path
     * @return
     * @throws SftpException
     */
    public List<LsEntry> getFileInfo(Channel channel, String path) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) channel;
        List<LsEntry> allList = sftp.ls(path);
        List<LsEntry> list = new ArrayList<>();
        //过滤掉目录
        for (LsEntry lsEntry : allList) {
            SftpATTRS sftpATTRS = lsEntry.getAttrs();
            if (!sftpATTRS.isLink() && !sftpATTRS.isDir()) list.add(lsEntry);
        }
        return list;
    }

    /**
     * 获取文件列表
     */
    public List<LsEntry> getFileInfo(String channelId, String path) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        return getFileInfo(sftp, path);
    }

    public void mkdir(String channelId, String path) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) getChannel(channelId);
        mkdir(sftp, path);

    }

    public void mkdir(Channel channel, String path) throws SftpException {
        ChannelSftp sftp = (ChannelSftp) channel;
        if (StringUtils.isBlank(path)) return;
        String[] dirArr = path.split(File.separator);
        String currentDir = dirArr[0];
        for (int i = 0; i < dirArr.length; i++) {
            if (i != 0) {
                currentDir += File.separator + dirArr[i];
            }
            if (StringUtils.isNotBlank(currentDir) && !isDirExist(channel, currentDir))
                sftp.mkdir(currentDir);
        }

    }

//    public void mkdir(String path) throws SftpException {
//        String shell = "mkdir -p "+path;
//        sftp.
//    }


    public Session getSession() {
        return session;
    }

    public Channel getChannel(String id) {
        if (!session.isConnected()) return null;
        return channelMap.get(id);
    }
}
