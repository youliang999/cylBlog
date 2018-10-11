package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/10/9.
 */
@Data
public class Menu implements Serializable {
    private String mid;
    private String parentMid;
    private String mname;
    private String murl;
    private Date updateDate;
    private Date createDate;
}
