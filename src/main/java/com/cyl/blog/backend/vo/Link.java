package com.cyl.blog.backend.vo;

import com.cyl.blog.controller.index.model.BaseBlog;

import java.io.Serializable;

public class Link extends BaseBlog implements Serializable {
  private static final long serialVersionUID = 5782329997653720580L;
  private String name;
  private String url;
  /* 注释 */
  private String notes;
  /* 可见性 */
  private boolean visible = true;

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public String getNotes(){
    return notes;
  }

  public void setNotes(String notes){
    this.notes = notes;
  }

  public boolean isVisible(){
    return visible;
  }

  public void setVisible(boolean visible){
    this.visible = visible;
  }

}
