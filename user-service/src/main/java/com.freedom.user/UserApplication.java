package com.freedom.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 启动类
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.freedom.user.mapper"})
@EnableDiscoveryClient
public class UserApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(UserApplication.class, args);
    }
}