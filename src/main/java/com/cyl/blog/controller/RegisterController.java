package com.cyl.blog.controller;

import com.cyl.blog.backend.vo.UserFormValidator;
import com.cyl.blog.entity.User;
import com.cyl.blog.service.UserService;
import com.cyl.blog.util.IdGenerator;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/10/11.
 */
@Controller
public class RegisterController {
    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView mv = new ModelAndView("/register");
        return mv;
    }

    @RequestMapping(value = "/register/submit", method = RequestMethod.POST)
    @ResponseBody
    public String insert(User user, String repass, Model model){
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> form = UserFormValidator.validateInsert(user, repass);
        log.info("===>>> register form:{}", new Gson().toJson(form));
        if(!form.isEmpty()){
            //model.addAllAttributes(form);
            data.put("success", 1);
            data.put("form", form);
            data.put("msg", "注册失败!");
            return new Gson().toJson(data);
//            return "/register";
        }
        User user1 = userService.getUserByNickName(user.getNickName());
        if(user1 != null) {
            data.put("success", 1);
            data.put("form", form);
            data.put("msg", "注册失败! 该用户已存在!");
        }
        user.setId(IdGenerator.uuid19());
        user.setCreateTime(new Date());
        user.setLastUpdate(user.getCreateTime());

        userService.insertU(user);
        data.put("success", 0);
        data.put("msg", "注册成功!");
        return new Gson().toJson(data);
    }
}
