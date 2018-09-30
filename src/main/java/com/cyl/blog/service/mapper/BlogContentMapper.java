package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.BlogContent;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/9/29.
 */
public interface BlogContentMapper {

    int insertBlogContent(BlogContent blogContent);

    List<BlogContent> getByIds(List<Integer> ids);

    BlogContent getById(int id);

    int update(BlogContent blogContent);

}
