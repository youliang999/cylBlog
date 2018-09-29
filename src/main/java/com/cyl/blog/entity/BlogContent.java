package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/29.
 */
@Data
public class BlogContent implements Serializable {
    private static final long serialVersionUID = 6540834805227548073L;
    private int blog_id;
    private String excerpt;
    private String content;
}
