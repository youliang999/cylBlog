package com.cyl.blog.service.mapper;

import com.cyl.blog.controller.index.model.Blog;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
public interface BlogMapper extends BaseMapper {

    Blog loadById(String postid);

    List<Blog> getBlogs(Map<String, Object> map);

    int countBlog();
}
