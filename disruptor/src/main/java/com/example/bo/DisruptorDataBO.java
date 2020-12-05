package com.example.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: dj
 * @create: 2020-12-02 14:44
 * @description:
 */
@Data
public class DisruptorDataBO implements Serializable {

    private String data;

    private LocalDateTime time;

    public DisruptorDataBO() {
        time = LocalDateTime.now();
    }

}
