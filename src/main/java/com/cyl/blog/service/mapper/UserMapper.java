package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper extends BaseMapper {
  
  User loadByNameAndPass(@Param("username") String username, @Param("password") String password);

  int countUser();

  List<User> getList();

  List<User> getListLimit(Map<String, Object> data);

  User getUserByNickName(String name);

  List<User> getUses(List<String> ids);
}
