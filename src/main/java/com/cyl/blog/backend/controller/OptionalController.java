package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.helper.OptionHelper;
import com.cyl.blog.backend.vo.*;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.util.CollectionUtils;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/5.
 */
@Controller
@RequestMapping("/backend/options")
@RequiresRoles("admin")
public class OptionalController {
    private static final Logger log = LoggerFactory.getLogger(OptionalController.class);
    @Autowired
    private OptionHelper optionHelper;
    @Autowired
    private BlogHelper blogHelper;

    @RequestMapping(value = "/general", method = RequestMethod.GET)
    public ModelAndView general(){
        ModelAndView mv = new ModelAndView("backend/new/optional");
        mv.addObject("form", optionHelper.getGeneralOption());
        return mv;
    }

    @RequestMapping(value = "/general", method = RequestMethod.POST)
    public ModelAndView updateGeneral(GeneralOption form){
        ModelAndView mv = new ModelAndView("backend/new/optional");
        mv.addObject("form", form);
        log.info("===>>> form:{}", new Gson().toJson(form));
        Map<String, String> result = OptionFormValidator.validateGeneral(form);
        log.info("===>>> form1:{}", new Gson().toJson(result));
        if(!result.isEmpty()){
            mv.addObject("form1", result);
            return mv;
        }
        log.info("===>>> update...");
        optionHelper.updateGeneralOption(form);
        mv.addObject("success", true);
        return mv;
    }

    @RequestMapping(value = "/post", method = RequestMethod.GET)
    public ModelAndView post(){
        ModelAndView mv = new ModelAndView("backend/new/post");
        mv.addObject("categorys", blogHelper.getCategory(20));
        mv.addObject("form", optionHelper.getPostOption());
        return mv;
    }

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public ModelAndView updatePost(PostOption form){
        ModelAndView mv = new ModelAndView("backend/new/post");
        mv.addObject("categorys", blogHelper.getCategory(20));
        mv.addObject("form", form);
        log.info("===>>> form:{}", new Gson().toJson(form));
        Map<String, String> result = OptionFormValidator.validatePost(form);
        if(!result.isEmpty()){
            mv.addObject("form1", result);
            return mv;
        }

        mv.addObject("success", true);
        optionHelper.updatePostOption(form);
        return mv;
    }

    @RequestMapping(value = "/email", method = RequestMethod.GET)
    public ModelAndView mail(){
        ModelAndView mv = new ModelAndView("backend/new/email");
        mv.addObject("form", optionHelper.getMailOption());
        return mv;
    }



    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public ModelAndView updateMail(MailOption form){
        log.info("===>>> 1111");
        ModelAndView mv = new ModelAndView("backend/new/email");
        Map<String, String> result = MailFormValidator.validate(form);
        log.info("===>>> result:{}", new Gson().toJson(result));
        if(!CollectionUtils.isEmpty(result)){
            mv.addObject("form1", result);
            return mv;
        }

        optionHelper.updateMailOption(form);
        //mailSenderService.setServerInfo(form);
        return mv;
    }
}
