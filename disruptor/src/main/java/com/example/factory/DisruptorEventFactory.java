package com.example.factory;

import com.example.bo.DisruptorDataBO;
import com.lmax.disruptor.EventFactory;

/**
 * @author: dj
 * @create: 2020-12-02 14:47
 * @description: 事件工厂
 */
public class DisruptorEventFactory implements EventFactory<DisruptorDataBO> {

    @Override
    public DisruptorDataBO newInstance() {
        return new DisruptorDataBO();
    }

}
