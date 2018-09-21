package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/9/14.
 */
@Data
public class VisitInfo implements Serializable {
    private static final long serialVersionUID = 2982533536569506591L;
    private int id;
    private String ip;
    private String cookie;
    private String visitUrl;
    private Date visitDate;
    private Date createDate;
}
