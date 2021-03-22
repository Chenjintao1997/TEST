package com.cjt.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjt.test.bean.Student;
import com.cjt.test.mapper.StudentMapper;
import com.cjt.test.service.IStudentSV;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Author: chenjintao
 * @Date: 2020/8/10 15:16
 */
@Service
public class StudentSVImpl extends ServiceImpl<StudentMapper, Student> implements IStudentSV {

    @Override
    public boolean create(String ext) {
        int result = baseMapper.create(ext);
        return result > 0;
    }

    @Override
    public boolean create1(String ext) {
        String tableName = "student" + (StringUtils.isNotBlank(ext) ? "_" + ext : "");

        int result = baseMapper.create1(tableName);
        return result > 0;
    }
}
