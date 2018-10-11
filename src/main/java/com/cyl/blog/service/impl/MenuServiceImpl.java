package com.cyl.blog.service.impl;

import com.cyl.blog.entity.Menu;
import com.cyl.blog.service.MenuService;
import com.cyl.blog.service.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService{
    @Autowired
    MenuMapper menuMapper;

    @Override
    public boolean insertMenu(Menu menu) {
        return menuMapper.insertMenu(menu) > 0;
    }

    @Override
    public Menu getByMid(String mid) {
        return menuMapper.getByMid(mid);
    }

    @Override
    public List<Menu> getAllMenu() {
        return menuMapper.getAllMenu();
    }

    @Override
    public boolean updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu) > 0;
    }

    @Override
    public boolean deleteByMid(String mid) {
        return menuMapper.deleteByMid(mid) > 0;
    }
}
