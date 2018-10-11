package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.Menu;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/10/9.
 */
public interface MenuMapper {

    int insertMenu(Menu menu);

    Menu getByMid(String mid);

    List<Menu> getAllMenu();

    int updateMenu(Menu menu);

    int deleteByMid(String mid);
}
