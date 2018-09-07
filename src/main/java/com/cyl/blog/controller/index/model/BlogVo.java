package com.cyl.blog.controller.index.model;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Category;
import com.cyl.blog.entity.User;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youliang.cheng on 2018/7/12.
 */
@Data
@Accessors(chain = true)
public class BlogVo extends Blog implements Serializable {
    private User user;
    private Category category;
    private List<String> tags;
    private String url;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BlogVo) {
            BlogVo b= (BlogVo) obj;
            return url.equalsIgnoreCase(b.getUrl());
        }
        return false;
    }
}
