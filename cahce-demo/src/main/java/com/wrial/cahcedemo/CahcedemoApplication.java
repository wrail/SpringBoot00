package com.wrial.cahcedemo;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.wrial.cahcedemo.mapper")
@SpringBootApplication
@EnableCaching//开启缓存注解
public class CahcedemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CahcedemoApplication.class, args);
    }

}
