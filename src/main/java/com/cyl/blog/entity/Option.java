package com.cyl.blog.entity;

import com.cyl.blog.controller.index.model.BaseBlog;

import java.io.Serializable;

/**
 * 站点选项,id和name为一致
 * 
 * @author
 * 
 */
public class Option extends BaseBlog implements Serializable {
  private static final long serialVersionUID = -3184733854528245006L;
  private String name;
  private String value;

  public Option(){
  }

  public Option(String name, String value){
    this.name = name;
    this.value = value;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getValue(){
    return value;
  }

  public void setValue(String value){
    this.value = value;
  }

}
