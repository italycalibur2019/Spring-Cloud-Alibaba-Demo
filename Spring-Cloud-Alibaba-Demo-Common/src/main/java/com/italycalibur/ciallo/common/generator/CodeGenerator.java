package com.italycalibur.ciallo.common.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.query.SQLQuery;

import java.nio.file.Paths;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-16 08:32:55
 * @description: Mybatis Plus 代码生成器
 */
public class CodeGenerator {
    public static final String url = "jdbc:postgresql://localhost:5432/demo?currentSchema=basic";

    public static final String username = "postgres";

    public static final String password = "123456";

    public static void main(String[] args) {
        FastAutoGenerator.create(url,username,password)
                .globalConfig(builder -> builder
                        .author("italycalibur")
                        .outputDir(Paths.get(System.getProperty("user.dir"))
                                + "/src/main/java")
                        .commentDate("yyyy-MM-dd")
                )
                .dataSourceConfig(builder -> builder
                        .databaseQueryClass(SQLQuery.class)
                        .typeConvert(new PostgreSqlTypeConvert())
                        .dbQuery(new PostgreSqlQuery())
                )
                .packageConfig(builder -> builder
                        .parent("com.italycalibur.ciallo.common.models")
                        .entity("entity")
                        .mapper("mapper")
                        .xml("mapper.xml")
                )
                .strategyConfig(builder -> builder
                        .addInclude("t_goods_master_po")
                        .addTablePrefix("t_")
                        .entityBuilder()
                        .enableLombok()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
