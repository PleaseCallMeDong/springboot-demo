package com.example.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: dj
 * @create: 2020-12-02 14:01
 * @description:
 */
@Data
public class UserDO implements Serializable {

    private String name;

    private Integer sex;

}
