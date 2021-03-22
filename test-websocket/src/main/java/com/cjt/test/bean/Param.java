package com.cjt.test.bean;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: chenjintao
 * @Date: 2020-06-03 15:19
 */
@Data
@ToString
public class Param implements Serializable {
    private static final long serialVersionUID = 4306456155492863196L;

    //0:heart check,1:text,2:img_filePath,3:music_filepath,4:video_filepath,5:other_filePath
    private int type;

    //内容
    private String data;

    //目的id
    private Integer to;

    //扩展
    private String ext;

}
