package com.example.exception;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: dj
 * @create: 2020-12-02 15:09
 * @description:
 */
@Slf4j
public class DisruptorException<T> implements ExceptionHandler<T> {

    @Override
    public void handleEventException(Throwable throwable, long sequence, Object event) {
        throwable.fillInStackTrace();
        log.error("disruptor过程错误 ==[{}] event==[{}] ,ex ==[{}]",
                sequence, event.toString(), throwable.getMessage());
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        log.error("disruptor启动错误 ==[{}]!", throwable.getMessage());
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.error("disruptor接收错误 ==[{}]!", throwable.getMessage());
    }

}