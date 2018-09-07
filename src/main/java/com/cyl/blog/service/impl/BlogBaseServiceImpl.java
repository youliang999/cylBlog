package com.cyl.blog.service.impl;

import com.cyl.blog.controller.index.model.BaseBlog;
import com.cyl.blog.service.BlogBaseService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.plugin.PageIterator;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
public abstract class BlogBaseServiceImpl implements BlogBaseService{
    public <T extends BaseBlog> int insert(T t){
        return getMapper().insert(t);
    }

    public <T extends BaseBlog> T loadById(String id){
        return getMapper().loadById(id);
    }

    public <T> void list(PageIterator<T> model){
//        List<T> result = getMapper().list(model);
//        model.setData(result);
    }

    public <T> List<T> list(){
        return getMapper().list();
    }

    public <T extends BaseBlog> int update(T t){
        return getMapper().update(t);
    }

    public int deleteById(String id){
        return getMapper().deleteById(id);
    }

    public long count(){
        return getMapper().count();
    }

    public abstract BaseMapper getMapper();
}
