package com.cyl.blog.service;

import com.cyl.blog.controller.index.model.Blog;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.BlogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
@Service
public class BlogService extends BlogBaseService {
    private static final Integer PAGESIZE = 20;

    @Autowired
    private BlogMapper blogMapper;

    public PageIterator<Blog> getBlogs(int page, int pageSize) {
        if(page <=0 || pageSize <= 0) {
            page = 1;
            pageSize = PAGESIZE;
        }
        int totalCount = countBlog();
        PageIterator<Blog> pageIterator = PageIterator.createInstance(page, pageSize, totalCount);
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page-1)*pageSize);
        map.put("limit", pageSize);
        List<Blog> blogs = blogMapper.getBlogs(map);
        pageIterator.setData(blogs);
        return pageIterator;
    }

    public int countBlog() {
        return blogMapper.countBlog();
    }

    @Override
    protected BaseMapper getMapper() {
        return blogMapper;
    }
}
