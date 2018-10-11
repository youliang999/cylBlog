package com.cyl.blog.service;

import com.cyl.blog.entity.Menu;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
public interface MenuService {

    boolean insertMenu(Menu menu);

    Menu getByMid(String mid);

    List<Menu> getAllMenu();

    boolean updateMenu(Menu menu);

    boolean deleteByMid(String mid);
}
