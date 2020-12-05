package com.example.service;

import com.alibaba.fastjson.JSON;
import com.example.bo.DisruptorDataBO;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: dj
 * @create: 2020-12-02 14:29
 * @description:
 */
@Slf4j
@Service
public class Test1ConsumerService implements EventHandler<DisruptorDataBO>, WorkHandler<DisruptorDataBO> {

    @Override
    public void onEvent(DisruptorDataBO bo, long l, boolean b) throws Exception {
        this.onEvent(bo);
    }

    @Override
    public void onEvent(DisruptorDataBO bo) throws Exception {
        log.info("收到数据:{}", JSON.toJSONString(bo));
    }

}
