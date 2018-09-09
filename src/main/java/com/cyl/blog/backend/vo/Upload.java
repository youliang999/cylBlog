package com.cyl.blog.backend.vo;

import com.cyl.blog.controller.index.model.BaseBlog;

import java.io.Serializable;

/**
 * 文章、页面的元数据,如文章包含的图片等待
 * 
 * @author zhou
 * 
 */
public class Upload extends BaseBlog implements Serializable {
  private static final long serialVersionUID = 1617807704124607949L;
  /* 对应文章id */
  private String postid;
  /* 图片名称 */
  private String name;
  /* 图片访问路径 */
  private String path;

  public String getPostid(){
    return postid;
  }

  public void setPostid(String postid){
    this.postid = postid;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getPath(){
    return path;
  }

  public void setPath(String path){
    this.path = path;
  }

}
