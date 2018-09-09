package com.cyl.blog.service;

import com.cyl.blog.backend.vo.Link;
import com.cyl.blog.plugin.PageIterator;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
public interface LinkService extends BlogBaseService{
    PageIterator<Link> list(int pageIndex, int pageSize);
}
