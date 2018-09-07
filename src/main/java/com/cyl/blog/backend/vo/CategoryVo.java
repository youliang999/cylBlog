package com.cyl.blog.backend.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/6.
 */
@Data
public class CategoryVo  implements Serializable{
    private String id;
    /* 是否显示 */
    private boolean visible = true;
    String text;
    int level;
}
