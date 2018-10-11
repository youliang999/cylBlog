package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
@Data
public class AuthMenu implements Serializable{
    private static final long serialVersionUID = -4981903956819337561L;
    private String mid;
    private String parentMid;
    private String mname;
    private String murl;
    private boolean isAuthed;
    private Date updateDate;
    private Date createDate;
}
