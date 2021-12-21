package com.freedom.comsumer.controller;

import com.freedom.comsumer.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/comsumer")
public class ComsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{id}")
    public User queryById(@PathVariable Long id){
        String url = "http://localhost:9091/user/"+id;
        User user = restTemplate.getForObject(url,User.class);
        return user;
    }
}
