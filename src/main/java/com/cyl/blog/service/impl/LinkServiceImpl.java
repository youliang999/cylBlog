package com.cyl.blog.service.impl;

import com.cyl.blog.backend.vo.Link;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.LinkService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Service("linkService")
public class LinkServiceImpl extends BlogBaseServiceImpl implements LinkService {
    @Autowired
    private LinkMapper linkMapper;

    @Override
    public PageIterator<Link> list(int pageIndex, int pageSize) {
        int count = linkMapper.countLink();
        if(count <= 0) {
            return PageIterator.createInstance(pageIndex, pageSize, Collections.EMPTY_LIST);
        }
        PageIterator<Link> pageModel=PageIterator.createInstance(pageIndex, pageSize, count);
        Map<String, Object> data = new HashMap<>();
        data.put("offset", (pageIndex - 1)*pageSize);
        data.put("limit", pageSize);
        pageModel.setData(linkMapper.getLinks(data));
        return pageModel;
    }


    @Override
    public BaseMapper getMapper(){
        return linkMapper;
    }
}
