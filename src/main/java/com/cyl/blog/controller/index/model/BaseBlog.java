package com.cyl.blog.controller.index.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
@Data
@Accessors(chain = true)
public class BaseBlog implements Serializable {
    private static final long serialVersionUID = 5457805799873264816L;
    private String id;
    private Date createTime;
    private String creator;
    private Date lastUpdate;
}
