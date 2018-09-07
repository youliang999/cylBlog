package com.cyl.blog.vo;


import com.cyl.blog.constants.OptionConstants;
import com.cyl.blog.helper.ApplicationContextUtil;
import com.cyl.blog.service.OptionsService;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * view中使用的的对象
 * 
 * @author zhou
 *
 */
public class Global implements Serializable{
  private static final long serialVersionUID = 6565516632806601542L;
  private String domain;
  private String title;
  private String subtitle;
  private String description;
  private String keywords;
  private boolean allowComment;
  private int year;

  public Global(String domain){
    this.domain = domain;
    this.title = getTitle();
    this.subtitle = getSubtitle();
    this.description = getDescription();
    this.keywords = getKeywords();
    this.allowComment = isAllowComment();
    this.year = getYear();
  }

  public String getTitle(){
    return getOptionValue(OptionConstants.TITLE);
  }

  public String getSubtitle(){
    return getOptionValue(OptionConstants.SUBTITLE);
  }

  public String getDescription(){
    return getOptionValue(OptionConstants.DESCRIPTION);
  }
  
  public String getKeywords(){
    return getOptionValue(OptionConstants.KEYWORDS);
  }

  public boolean isAllowComment(){
    return Boolean.parseBoolean(getOptionValue(OptionConstants.ALLOW_COMMENT));
  }

  public int getYear(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    return calendar.get(Calendar.YEAR);
  }

  public String getDomain(){
    return domain;
  }

  private static String getOptionValue(String name){
    OptionsService optionsService = ApplicationContextUtil.getBean(OptionsService.class);
    return optionsService.getOptionValue(name);
  }

}
