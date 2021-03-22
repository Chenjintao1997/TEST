package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-06-04 11:26
 */
@Data
@ToString
public class MessageVO implements Serializable {
    private static final long serialVersionUID = -3997174396205102193L;


    //1:text,2:img_filePath,3:music_filepath,4:video_filepath,5:other_filePath
    private int dataType;

    //内容
    private String data;

    //目的id
    private String to;

    //源id
    private String from;

    //扩展
    private String ext;

    //1:正常消息，2：心跳检测
    private Integer type;
}
