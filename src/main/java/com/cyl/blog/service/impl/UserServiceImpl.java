package com.cyl.blog.service.impl;


import com.cyl.blog.entity.User;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.UserService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BlogBaseServiceImpl implements UserService {
  private static final Integer PAGESIZE = 20;
  @Autowired
  private UserMapper userMapper;

  public PageIterator<User> list(int pageIndex, int pageSize){
    if(pageIndex <=0 || pageSize <= 0) {
      pageIndex = 1;
      pageSize = PAGESIZE;
    }
    Map<String, Object> data = new HashMap<>();
    data.put("offset", (pageIndex - 1) * pageSize);
    data.put("limit", pageSize);
    long totalCount = countUser();
    PageIterator<User> page = PageIterator.createInstance(pageIndex, pageSize, (int)totalCount);
    page.setData(userMapper.getListLimit(data));
    return page;
  }
  
  public User login(String username,String password){
    return userMapper.loadByNameAndPass(username, password);
  }

  @Override
  public List<User> getUser() {
    return userMapper.getList();
  }

  @Override
  public User getUserByNickName(String name) {
    return userMapper.getUserByNickName(name);
  }

  public long countUser() {
    return userMapper.count();
  }

  @Override
  public List<User> getByUserIds(List<String> ids) {
    return userMapper.getUses(ids);
  }

  @Override
  public BaseMapper getMapper(){
    return userMapper;
  }

}
