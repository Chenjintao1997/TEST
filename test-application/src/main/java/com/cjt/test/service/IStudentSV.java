package com.cjt.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cjt.test.bean.Student;

/**
 * @Author: chenjintao
 * @Date: 2020/8/10 14:49
 */
public interface IStudentSV extends IService<Student> {

    boolean create(String ext);

    boolean create1(String ext);
}
