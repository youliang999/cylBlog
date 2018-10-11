package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/10/9.
 */
@Data
public class UserAuth implements Serializable {
    private static final long serialVersionUID = 3506223568495920408L;
    private int id;
    private String userId;
    private String nickName;
    private String menuList;
    private Date updateDate;
    private Date createDate;
}
