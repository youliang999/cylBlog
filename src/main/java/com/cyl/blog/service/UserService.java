package com.cyl.blog.service;


import com.cyl.blog.entity.User;
import com.cyl.blog.plugin.PageIterator;

import java.util.List;

public interface UserService extends BlogBaseService{
    boolean insertU(User user);

    PageIterator<User> list(int pageIndex, int pageSize);

    User login(String username, String password);

    List<User> getUser();

    User getUserByNickName(String name);

    List<User> getByUserIds(List<String> ids);
}
