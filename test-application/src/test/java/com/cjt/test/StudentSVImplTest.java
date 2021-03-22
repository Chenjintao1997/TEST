package com.cjt.test;

import com.cjt.test.service.IStudentSV;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: chenjintao
 * @Date: 2020/8/10 17:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StudentSVImplTest {

    @Autowired
    private IStudentSV studentSV;

    @Test
    public void testCreate() {
        studentSV.create("20200812");
    }

    @Test
    public void testCreate1() {
        System.out.println(studentSV.create1("20200812"));
    }
}
