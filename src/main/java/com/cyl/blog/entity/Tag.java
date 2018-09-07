package com.cyl.blog.entity;

import com.cyl.blog.controller.index.model.BaseBlog;

import java.io.Serializable;

public class Tag extends BaseBlog implements Serializable {
  private static final long serialVersionUID = -3443186050281843966L;
  private String name;
  private String postid;

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getPostid(){
    return postid;
  }

  public void setPostid(String postid){
    this.postid = postid;
  }

}
