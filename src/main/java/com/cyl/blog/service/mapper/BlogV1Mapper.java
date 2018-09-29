package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.BlogV1;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/29.
 */
public interface BlogV1Mapper {

    int insertBlogV1(BlogV1 blogv1);

    BlogV1 getById(int id);

    List<BlogV1> getBlogs(Map<String, Object> data);

    List<BlogV1> getAllBlogs(Map<String, Object> map);
}
