package com.cyl.blog.service;

import com.cyl.blog.controller.index.model.BaseBlog;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.mapper.BaseMapper;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/7/30.
 */
public interface BlogBaseService {

        /**
         *
         * ==>> BlogService.insertBlog(Blog blog);
         */
        @Deprecated
        <T extends BaseBlog> int insert(T t);


        <T extends BaseBlog> T loadById(String id);

        <T> void list(PageIterator<T> model);

        <T> List<T> list();

        <T extends BaseBlog> int update(T t);

        int deleteById(String id);

        long count();

        BaseMapper getMapper();
    }
