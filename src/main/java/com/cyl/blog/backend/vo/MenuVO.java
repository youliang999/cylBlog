package com.cyl.blog.backend.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
@Data
public class MenuVO implements Serializable {
    private static final long serialVersionUID = 1808674642843538137L;
    private String mid;
    private String parentMid;
    private String mname;
    private String murl;
    private String iconstyle;
    private Date updateDate;
    private Date createDate;
}
