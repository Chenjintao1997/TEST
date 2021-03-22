package com.cjt.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjt.test.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

/**
 * @Author: chenjintao
 * @Date: 2020/8/6 15:05
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Update(
            "CREATE TABLE " +
                    "student" +
                    "<if test = 'ext != null'>" +
                    "_${ext} " +
                    "</if>" +
//                    "concat('student2') " +
                    "(\n" +
                    "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  `age` int(11) DEFAULT NULL,\n" +
                    "  `sex` int(2) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
    )
    @Options(statementType = StatementType.STATEMENT)
    int create(@Param("ext")String ext);


    @Update(
            "CREATE TABLE " +
                    "${tableName} " +
//                    "concat('student2') " +
                    "(\n" +
                    "  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',\n" +
                    "  `name` varchar(255) DEFAULT NULL,\n" +
                    "  `age` int(11) DEFAULT NULL,\n" +
                    "  `sex` int(2) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;"
    )
    @Options(statementType = StatementType.STATEMENT)
    int create1(@Param("tableName")String tableName);
}
