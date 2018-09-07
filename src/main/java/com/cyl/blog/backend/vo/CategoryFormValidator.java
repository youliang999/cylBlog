package com.cyl.blog.backend.vo;


import com.cyl.blog.entity.Category;
import com.cyl.blog.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class CategoryFormValidator {

  public static Map<String, Object> validateInsert(Category category){
    Map<String, Object> form = new HashMap<>();
    if(StringUtils.isBlank(category.getName())){
      form.put("msg", "分类名称不能为空");
    }

    return form;
  }

}
