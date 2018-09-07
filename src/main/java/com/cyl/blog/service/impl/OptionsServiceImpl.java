package com.cyl.blog.service.impl;

import com.cyl.blog.backend.vo.GeneralOption;
import com.cyl.blog.constants.OptionConstants;
import com.cyl.blog.entity.Option;
import com.cyl.blog.service.OptionsService;
import com.cyl.blog.service.jedis.JedisOperationService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.OptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service("optionsService")
public class OptionsServiceImpl extends BlogBaseServiceImpl implements OptionsService {
  private Logger LOG = LoggerFactory.getLogger(OptionsServiceImpl.class);
  @Autowired
  private OptionMapper optionMapper;
  private static final int DEFAULT_DURATION = 6 * 30;
  private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.DAYS;

  @Autowired
  @Qualifier("configCache")
  private JedisOperationService<String, String> configCache;



  public String getOptionValue(String name){
    //LOG.info("===>>> name:{}", name);
    String res = configCache.getOrLoadAndSet(name, s -> optionMapper.getOptionValue(name), DEFAULT_DURATION, DEFAULT_TIME_UNIT);
   // LOG.info("===>>> res: {}", res);
    return res;
  }

  /**
   * 以select .. for update,注意此方法须在事务中执行
   * 
   * @param name
   * @return
   */
  public String getOptionValueForUpdate(String name){
    return optionMapper.getOptionValueForUpdate(name);
  }

  /**
   * 此处为MySQL的replace into, 注意这需要主键id一致
   * 
   * @param name
   * @param value
   */
  public void updateOptionValue(String name, String value){
    Option option = new Option(name, value);
    option.setId(name);
    optionMapper.update(option);
    configCache.set(name, value);
  }

  @Override
  public GeneralOption getGeneralOption() {
    GeneralOption generalOption = new GeneralOption();
    generalOption.setTitle(getOptionValue(OptionConstants.TITLE));
    generalOption.setDescription(getOptionValue(OptionConstants.DESCRIPTION));
    generalOption.setKeywords(getOptionValue(OptionConstants.KEYWORDS));
    generalOption.setSubtitle(getOptionValue(OptionConstants.SUBTITLE));
    generalOption.setWeburl(getOptionValue("weburl"));
    return generalOption;
  }

  @Override
  public BaseMapper getMapper(){
    return optionMapper;
  }

}
