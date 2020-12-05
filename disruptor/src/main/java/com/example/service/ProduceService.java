package com.example.service;

import com.example.bo.DisruptorDataBO;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: dj
 * @create: 2020-12-02 14:22
 * @description: 生产
 */
@Slf4j
@Service
public class ProduceService {

    @Resource
    private RingBuffer<DisruptorDataBO> test1RingBuffer;

    public void sayHelloMq(String message) {
        test1RingBuffer.publishEvent((event, sequence, data) -> event.setData(data), message);
    }

}
