package com.freedom.comsumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 */
@SpringBootApplication
public class ComsumerApplication {

    public static void main(String[] args){
        // 运行spring应用
        SpringApplication.run(ComsumerApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}