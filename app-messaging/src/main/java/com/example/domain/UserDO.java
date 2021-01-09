package com.example.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: dj
 * @create: 2021-01-09 09:27
 * @description: app 用户
 */
@Data
public class UserDO implements Serializable {

    private Long id;

    /**
     * android
     * ios
     */
    private String appType;

    private String appId;


}
