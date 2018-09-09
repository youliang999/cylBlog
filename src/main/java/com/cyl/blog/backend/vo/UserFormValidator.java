package com.cyl.blog.backend.vo;


import com.cyl.blog.entity.User;
import com.cyl.blog.util.CommRegular;
import com.cyl.blog.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserFormValidator {

  public static Map<String, Object> validateInsert(User user, String repass){
    Map<String, Object> form = validateUser(user);
    if(StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(repass)){
      form.put("password", "需填写用户密码");
    }
    if(!user.getPassword().equals(repass) || !user.getPassword().matches(CommRegular.PASSWD)){
      form.put("password", "两次密码不一致或者密码格式不对");
    }

    return form;
  }

  public static Map<String, Object> validateUpdate(User user, String repass){
    Map<String, Object> form = validateUser(user);
    if(!StringUtils.isBlank(user.getPassword())
        && (!user.getPassword().equals(repass) || !user.getPassword().matches(CommRegular.PASSWD))){
      form.put("password", "两次密码不一致或者密码格式不对");
    }else if(StringUtils.isBlank(user.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

  private static Map<String, Object> validateUser(User user){
    Map<String, Object> form = new HashMap<>();
    if(StringUtils.isBlank(user.getNickName())){
      form.put("nickName", "需填写用户名称");
    }
    if(StringUtils.isBlank(user.getEmail()) || !user.getEmail().matches(CommRegular.EMAIL)){
      form.put("email", "邮箱格式不正确");
    }
    if(StringUtils.isBlank(user.getRealName())){
      form.put("realName", "需填写用户真实名称");
    }

    return form;
  }

  public static Map<String, Object> validateMy(User user, String repass){
    Map<String, Object> form = new HashMap<>();
    if(StringUtils.isBlank(user.getEmail()) || !user.getEmail().matches(CommRegular.EMAIL)){
      form.put("email", "邮箱格式不正确");
    }
    if(StringUtils.isBlank(user.getRealName())){
      form.put("realName", "需填写用户真实名称");
    }

    if(!StringUtils.isBlank(user.getPassword())
        && (!user.getPassword().equals(repass) || !user.getPassword().matches(CommRegular.PASSWD))){
      form.put("password", "两次密码不一致或者密码格式不对");
    }else if(StringUtils.isBlank(user.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

}
