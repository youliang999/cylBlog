package com.cyl.blog.service.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
public interface BaseMapper {
    /**
     * 新增记录
     *
     * @param t
     */
    public <T> int insert(T t);

    /**
     * 根据ID查询记录
     *
     * @param id
     * @return
     */
    public <T> T loadById(String id);

    public <T> List<T> list(Map<String, Object> map);

    public <T> List<T> list();

    /**
     * 根据ID删除记录
     *
     * @param id
     */
    public <T> int deleteById(String id);

    /**
     * 更新记录
     *
     * @param t
     */
    public <T> int update(T t);

    /**
     * 获取记录总数
     */
    public long count();
}
