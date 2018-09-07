package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper {
  
  User loadByNameAndPass(@Param("username") String username, @Param("password") String password);

  int countUser();

  List<User> getList();

  User getUserByNickName(String name);

  List<User> getUses(List<String> ids);
}
