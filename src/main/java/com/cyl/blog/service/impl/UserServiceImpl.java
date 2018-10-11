package com.cyl.blog.service.impl;


import com.cyl.blog.entity.User;
import com.cyl.blog.entity.UserAuth;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.UserAuthService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends BlogBaseServiceImpl implements UserService {
  private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
  private static final Integer PAGESIZE = 20;
  private static final String auths = "CB_200,CB_210,CB_220,CB_230,CB_250";
  @Autowired
  private UserMapper userMapper;
  @Autowired
  private UserAuthService userAuthService;


  public boolean insertU(User user) {
    userMapper.insert(user);
    UserAuth userAuth = new UserAuth();
    userAuth.setUserId(user.getId());
    userAuth.setNickName(user.getNickName());
    userAuth.setMenuList(auths);
    UserAuth old = userAuthService.getByUName(user.getNickName());
    if(old != null) {
      userAuthService.insertAuth(userAuth);
    } else {
      userAuthService.updateAuth(userAuth);
    }
    log.info("===>>> register a user success. userName:{}", user.getNickName());
    return true;
  }


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
