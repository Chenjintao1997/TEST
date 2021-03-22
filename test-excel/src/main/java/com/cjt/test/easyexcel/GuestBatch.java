package com.cjt.test.easyexcel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chenjintao
 * @Date: 2020-02-12 16:30
 */

@TableName("cces_guest_batch")
@Data
@ToString
public class GuestBatch implements Serializable {
    private Long rowId;

    @TableId(type = IdType.INPUT)
    private String guestBatchId;

    private String guestBatchName;

    private String guestListTableExt;

    private Integer guestBatchState;

    private Integer guestListCount;

    private Integer guestAssignCount;

    private Date createTime;

    private Date modifyTime;
}
