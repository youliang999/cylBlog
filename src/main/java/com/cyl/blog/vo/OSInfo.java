package com.cyl.blog.vo;


import com.cyl.blog.helper.WebContextFactory;
import lombok.Data;

import javax.servlet.ServletContext;
import java.io.Serializable;

/**
 * 系统信息
 * 
 * @author cyl
 *
 */
@Data
public class OSInfo implements Serializable{
  private static final long serialVersionUID = 4328650278827924305L;
  private String osName;
  private String osVersion;
  private String javaVersion;
  private String serverInfo;
  private long totalMemory;

  private OSInfo(){
  }

  public static OSInfo getCurrentOSInfo(){
    OSInfo instance = new OSInfo();
    instance.osName = System.getProperty("os.name");
    instance.osVersion = System.getProperty("os.version");
    instance.javaVersion = System.getProperty("java.version");
    ServletContext sc = WebContextFactory.get().getRequest().getServletContext();
    instance.serverInfo = sc.getServerInfo();
    /**
     * 获取java进程内存大小，以M为单位
     *
     * @return
     */
    instance.totalMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
    return instance;
  }

}
