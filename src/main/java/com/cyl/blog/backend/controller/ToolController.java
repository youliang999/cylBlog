package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.helper.RedisHelper;
import com.cyl.blog.entity.RedisInfoDetail;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Controller
@RequestMapping("/backend/tool")
@RequiresRoles("admin")
public class ToolController {

    @Autowired
    private RedisHelper redisHelper;

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public ModelAndView ehcache(){
        ModelAndView mv = new ModelAndView("backend/new/redis-info");
        List<RedisInfoDetail> redisInfoDetails =  redisHelper.getClientInfo();
        mv.addObject("redisInfos", redisInfoDetails);
        return mv;
    }

}
