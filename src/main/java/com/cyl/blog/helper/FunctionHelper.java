package com.cyl.blog.helper;


import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.CookieUtil;
import com.cyl.blog.util.FileUtils;
import com.cyl.blog.util.StringUtils;

import java.net.URI;
import java.util.Collection;

public class FunctionHelper {

  private FunctionHelper(){
  }

  /**
   * 获取base64解码后的cookie值
   * 
   * @param name
   * @return
   */
  public static String cookieValue(String name){
    WebContext context = WebContextFactory.get();
    CookieUtil cookieUtil = new CookieUtil(context.getRequest(), context.getResponse());
    return cookieUtil.getCookie(name);
  }

  /**
   * 获取制定url的域名链接，包含path路径
   * 
   * @param url
   * @return
   */
  public static String getDomainLink(String url){
    if(StringUtils.isBlank(url))
      return "";

    URI uri = URI.create(url);
    String result = uri.getHost();
    if(uri.getPort() != -1 && uri.getPort() != 80){
      result += ":" + uri.getPort();
    }

    if(!"/".equals(uri.getPath())){
      result += uri.getPath();
    }

    if(result.endsWith("/")){
      result = result.substring(0, result.length() - 1);
    }

    return result;
  }

  public static String fileExt(String filename){
    return FileUtils.getFileExt(filename).toUpperCase();
  }

  public static String join(Collection<String> collect, String delimiter){
    return CollectionUtils.isEmpty(collect) ? null : StringUtils.join(collect, delimiter);
  }

}
