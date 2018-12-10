package com.cyl.blog.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>
 * 获取spring bean的工具类
 * </p>
 * <p>
 * 注：spring-servlet.xml中的bean并不由ApplicationContext管理, 它由DispatcherServlet管理
 * </p>
 * 
 * @author zhou
 * 
 */
public class ApplicationContextUtil implements ApplicationContextAware {
  private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextUtil.class);
  static ApplicationContext context;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
  }

  /**
   * 根据提供的bean名称得到相应的类
   * 
   * @param name
   *          bean名称
   */
  public static Object getBean(String name){
    LOG.info("===>>> ApplicationContextUtil getBean.");
    return context.getBean(name);
  }

  /**
   * 根据提供的指定类型的类得到相应的类
   * 
   * @param clazz
   *          bean名称
   */
  public static   <T> T getBean(Class<T> clazz){
    return context.getBean(clazz);
  }

  /**
   * 根据提供的bean名称得到对应于指定类型的类
   * 
   * @param name
   *          bean名称
   * @param clazz
   *          返回的bean类型,若类型不匹配,将抛出异常
   */
  public static  <T> T getBean(String name, Class<T> clazz){
    return context.getBean(name, clazz);
  }


}
