package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Data
public class RedisInfoDetail implements Serializable {
    private static final long serialVersionUID = 3091645125326668952L;

    private String key;

    private String value;
}
