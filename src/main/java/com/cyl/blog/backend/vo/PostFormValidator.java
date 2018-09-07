package com.cyl.blog.backend.vo;


import com.cyl.blog.constants.PostConstants;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PostFormValidator {

  public static Map<String, Object> validatePublish(Blog post){
    return validatePost(post, true);
  }

  public static Map<String, Object> validateUpdate(Blog post){
    Map<String, Object> form = validatePublish(post);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

  public static Map<String, Object> validateFastUpdate(Blog post){
    Map<String, Object> form = validatePost(post, false);
    if(StringUtils.isBlank(post.getId())){
      form.put("msg", "文章ID不合法");
    }

    return form;
  }

  private static Map<String, Object> validatePost(Blog post, boolean verifyContent){
	  Map<String, Object> form = new HashMap<>();
    if(StringUtils.isBlank(post.getTitle())){
      form.put("msg", "文章标题未填写");
    }else if(verifyContent && StringUtils.isBlank(post.getContent())){
      form.put("msg", "请填写文章内容");
    }else if(PostConstants.TYPE_POST.equals(post.getType()) && StringUtils.isBlank(post.getCategoryid())){
      form.put("msg", "请选择文章分类");
    }

    return form;
  }

}
