package com.cyl.blog.backend.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/5.
 */
@Data
public class GeneralOption implements Serializable{
    private static final long serialVersionUID = -5710356000563744885L;
    private String title;
    private String subtitle;
    private String description;
    private String keywords;
    private String weburl;
}
