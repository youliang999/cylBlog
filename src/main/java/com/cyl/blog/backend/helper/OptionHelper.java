package com.cyl.blog.backend.helper;

import com.cyl.blog.backend.vo.GeneralOption;
import com.cyl.blog.backend.vo.MailOption;
import com.cyl.blog.backend.vo.PostOption;
import com.cyl.blog.constants.OptionConstants;
import com.cyl.blog.service.OptionsService;
import com.cyl.blog.service.jedis.JedisOperationService;
import com.cyl.blog.service.jedis.SingleLoader;
import com.cyl.blog.util.NumberUtils;
import com.cyl.blog.util.StringUtils;
import com.cyl.blog.vo.OSInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/9/5.
 */
@Component
public class OptionHelper {
    private static final String SYSTEM_KEY = "systeminfo";
    private static final long CACHE_DURATION = 24 * 30;
    private static final TimeUnit CACHE_TIME_UNIT = TimeUnit.HOURS;
    @Autowired
    @Qualifier("systemCache")
    private JedisOperationService<String, OSInfo> systemCache;
    @Autowired
    private OptionsService optionsService;

    public OSInfo getOsInfo() {
        return systemCache.getOrLoadAndSet(SYSTEM_KEY, systemSingloader, CACHE_DURATION, CACHE_TIME_UNIT);
    }

    private SingleLoader<String, OSInfo> systemSingloader = new SingleLoader<String, OSInfo>() {
        @Override
        public OSInfo load(String s) {
            if (StringUtils.isBlank(s)) {
                return OSInfo.getCurrentOSInfo();
            }
            return OSInfo.getCurrentOSInfo();
        }
    };

    public GeneralOption getGeneralOption() {
        return optionsService.getGeneralOption();
    }

    /**
     * 更新网站基础设置，同时更新WebConstants中变量
     *
     * @param form
     */
    @Transactional
    public void updateGeneralOption(GeneralOption form){
        optionsService.updateOptionValue(OptionConstants.TITLE, form.getTitle());
        optionsService.updateOptionValue(OptionConstants.SUBTITLE, form.getSubtitle());
        optionsService.updateOptionValue(OptionConstants.DESCRIPTION, form.getDescription());
        optionsService.updateOptionValue(OptionConstants.KEYWORDS, form.getKeywords());
        optionsService.updateOptionValue("weburl", form.getWeburl());
    }

    public PostOption getPostOption(){
        PostOption option = new PostOption();
        option.setMaxshow(NumberUtils.toInteger(optionsService.getOptionValue(OptionConstants.MAXSHOW), 10));
        option.setAllowComment(Boolean.parseBoolean(optionsService.getOptionValue(OptionConstants.ALLOW_COMMENT)));
        option.setDefaultCategory(optionsService.getOptionValue(OptionConstants.DEFAULT_CATEGORY_ID));
        return option;
    }

    @Transactional
    public void updatePostOption(PostOption form){
        optionsService.updateOptionValue(OptionConstants.MAXSHOW, form.getMaxshow() + "");
        optionsService.updateOptionValue(OptionConstants.ALLOW_COMMENT, form.isAllowComment() + "");
        optionsService.updateOptionValue(OptionConstants.DEFAULT_CATEGORY_ID, form.getDefaultCategory());
    }

    public MailOption getMailOption(){
        MailOption option = new MailOption();
        option.setHost(optionsService.getOptionValue("mail_host"));
        if(!StringUtils.isBlank(option.getHost())){
            option.setPort(Integer.parseInt(optionsService.getOptionValue("mail_port")));
            option.setUsername(optionsService.getOptionValue("mail_username"));
            option.setPassword(optionsService.getOptionValue("mail_password"));
        }else{
            option = null;
        }

        return option;
    }

    @Transactional
    public void updateMailOption(MailOption form){
        optionsService.updateOptionValue("mail_host", form.getHost());
        optionsService.updateOptionValue("mail_port", form.getPort() + "");
        optionsService.updateOptionValue("mail_username", form.getUsername());
        optionsService.updateOptionValue("mail_password", form.getPassword());
    }
}
