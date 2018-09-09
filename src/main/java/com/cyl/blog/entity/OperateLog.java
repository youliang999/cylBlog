package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Data
public class OperateLog implements Serializable {
    private static final long serialVersionUID = -803150330783490538L;
    private long id;
    private String executeTime;
    private String usedTime;
    private String args;
}
