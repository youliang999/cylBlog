package com.cyl.blog.controller.index.model;

import lombok.Data;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/7/12.
 */
@Data
public class BlogVo extends Blog {
    private User user;
    private Category category;
    private List<String> tags;
}
