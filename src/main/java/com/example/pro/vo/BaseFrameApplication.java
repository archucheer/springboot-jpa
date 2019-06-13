package com.example.pro.vo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//异步
//@EnableAsync
//计划
@EnableScheduling
@EnableTransactionManagement
@MapperScan("com.example.pro.vo.repository")
@ComponentScan("com.example.pro")
public class BaseFrameApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseFrameApplication.class, args);
    }

}
