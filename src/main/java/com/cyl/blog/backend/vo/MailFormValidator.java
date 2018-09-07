package com.cyl.blog.backend.vo;


import com.cyl.blog.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class MailFormValidator {

  public static Map<String, String> validate(MailOption form){
	  Map<String, String> result = new HashMap<>();
    if(StringUtils.isBlank(form.getHost())){
      result.put("host", "请输入host");
    }

    if(form.getPort() < 10){
      result.put("port", "请输入合法端口号");
    }

    if(StringUtils.isBlank(form.getUsername())){
      result.put("username", "请输入用户名");
    }

    if(StringUtils.isBlank(form.getPassword())){
      result.put("password", "请输入密码");
    }

    return result;
  }
}
