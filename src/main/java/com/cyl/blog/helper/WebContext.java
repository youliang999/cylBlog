package com.cyl.blog.helper;


import com.cyl.blog.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebContext {
  private String sid;
  private int loginId;
  private User user;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public WebContext(HttpServletRequest request, HttpServletResponse response){
    this.request = request;
    this.response = response;
  }

  public String getSid(){
    return sid;
  }

  public void setSid(String sid){
    this.sid = sid;
  }

  public int getLoginId(){
    return loginId;
  }

  public void setLoginId(int loginId){
    this.loginId = loginId;
  }

  public User getUser(){
    return user;
  }

  public void setUser(User user){
    this.user = user;
  }

  public boolean isLogon(){
    return user != null;
  }

  public HttpServletRequest getRequest(){
    return request;
  }

  public HttpServletResponse getResponse(){
    return response;
  }

}
