package com.cyl.blog.backend.vo;


import com.cyl.blog.util.CommRegular;
import com.cyl.blog.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class LinkFormValidator {

  public static Map<String, Object> validateInsert(Link link){
    Map<String, Object> form = new HashMap<String, Object>();
    if(StringUtils.isBlank(link.getName())){
      form.put("name", "需填写链接名称");
    }else if(StringUtils.isBlank(link.getUrl()) || !link.getUrl().matches(CommRegular.DOMAIN)){
      form.put("url", "链接格式不正确");
    }

    return form;
  }

  public static Map<String, Object> validateUpdate(Link link){
    Map<String, Object> form = validateInsert(link);
    if(StringUtils.isBlank(link.getId())){
      form.put("msg", "ID不合法");
    }

    return form;
  }

}
