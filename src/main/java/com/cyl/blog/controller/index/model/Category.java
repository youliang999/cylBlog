package com.cyl.blog.controller.index.model;

import lombok.Data;

/**
 * Created by youliang.cheng on 2018/7/12.
 */
@Data
public class Category extends BaseBlog {
    private String name;
    private int leftv;
    private int rightv;
    /* 是否显示 */
    private boolean visible = true;
}
