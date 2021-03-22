package com.cjt.test.web.annotion;

import java.lang.annotation.*;

/**
 * @Author: chenjintao
 * @Date: 2020/9/23 23:42
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
}
