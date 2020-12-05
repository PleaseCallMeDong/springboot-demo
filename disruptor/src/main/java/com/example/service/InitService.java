package com.example.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author: dj
 * @create: 2020-12-02 13:58
 * @description:
 */
@Service
public class InitService {

    @Resource
    private ProduceService produceService;

    @PostConstruct
    public void init() {

        for (int i = 0; i < 1000; i++) {
            produceService.sayHelloMq(i + "");
        }

    }

}
