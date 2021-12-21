package com.freedom.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.freedom.user.mapper"})
public class UserApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(UserApplication.class, args);
    }
}