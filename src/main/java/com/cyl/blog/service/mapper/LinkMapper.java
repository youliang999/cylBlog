package com.cyl.blog.service.mapper;

import com.cyl.blog.backend.vo.Link;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
public interface LinkMapper extends BaseMapper{

    int countLink();

    List<Link> getLinks(Map<String, Object> map);
}
