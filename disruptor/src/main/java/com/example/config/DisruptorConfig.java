package com.example.config;

import cn.hutool.core.thread.ThreadUtil;
import com.example.bo.DisruptorDataBO;
import com.example.exception.DisruptorException;
import com.example.factory.DisruptorEventFactory;
import com.example.service.Test1ConsumerService;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author: dj
 * @create: 2020-12-02 14:43
 * @description:
 */
@Configuration
public class DisruptorConfig {

    @Bean("test1RingBuffer")
    public RingBuffer<DisruptorDataBO> test1RingBuffer() {

        // 定义用于事件处理的线程池，
        // Disruptor通过java.util.concurrent.ExecutorSerivce提供的线程来触发consumer的事件处理

        ExecutorService executor = ThreadUtil.newExecutor(2);

        // 指定事件工厂
        DisruptorEventFactory factory = new DisruptorEventFactory();

        // 指定ringbuffer字节大小，必须为2的N次方（能将求模运算转为位运算提高效率），否则将影响效率
        int bufferSize = 1024 * 256;

        // 单线程模式，获取额外的性能
        Disruptor<DisruptorDataBO> disruptor = new Disruptor<>(factory, bufferSize, executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());

        //设置错误
        disruptor.setDefaultExceptionHandler(new DisruptorException<>());

        // 设置事件业务处理器---消费者
        disruptor.handleEventsWith(new Test1ConsumerService());

        // 启动disruptor线程
        disruptor.start();

        // 获取ringbuffer环，用于接取生产者生产的事件
        return disruptor.getRingBuffer();
    }


}
