package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.vo.UserFormValidator;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.entity.User;
import com.cyl.blog.helper.WebContext;
import com.cyl.blog.helper.WebContextFactory;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.UserService;
import com.cyl.blog.util.CookieUtil;
import com.cyl.blog.util.IdGenerator;
import com.cyl.blog.util.StringUtils;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Controller
@RequestMapping("/backend/users")
@RequiresAuthentication
public class UserController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    @RequiresRoles("admin")
    public ModelAndView index(@PathVariable(value = "page") int page){
        ModelAndView mv = new ModelAndView("backend/new/user-list");
        PageIterator<User> userPageIterator = userService.list(page, 15);
        mv.addObject("users", userPageIterator.getData());
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", userPageIterator.getTotalPages());
        mv.addObject("totalCount", userPageIterator.getTotalCount());
        mv.addObject("domain", getGlobal().getDomain() +  "/backend/users" );
        mv.addObject("currentuser", cookieValue("un"));
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    @RequiresRoles("admin")
    public String insert(User user, String repass, Model model){
        Map<String, Object> form = UserFormValidator.validateInsert(user, repass);
        log.info("===>>> form:{}", new Gson().toJson(form));
        if(!form.isEmpty()){
            model.addAllAttributes(form);
            return "backend/new/user-edit";
        }

        user.setId(IdGenerator.uuid19());
        user.setCreateTime(new Date());
        user.setLastUpdate(user.getCreateTime());

        userService.insert(user);
        return "redirect:/backend/users/1";
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView update(User user, String repass, Model model){
        ModelAndView mv = new ModelAndView("backend/new/user-edit");
        Map<String, Object> form = UserFormValidator.validateUpdate(user, repass);
        if(!form.isEmpty()){
            mv.addObject("form", form);
            mv.addObject("user", user);
            return mv;
        }

        user.setLastUpdate(new Date());
        userService.update(user);
        mv.setViewName("redirect:/backend/users/1");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
    @RequiresRoles("admin")
    public Object remove(@PathVariable("userid") String userid){
        userService.deleteById(userid);
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return data;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String uid){
        ModelAndView mv = new ModelAndView("backend/new/user-edit");
        if(!StringUtils.isBlank(uid))
            mv.addObject("user", userService.loadById(uid));
        return mv;
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView my(){
        ModelAndView mv = new ModelAndView("backend/new/user-my");
        mv.addObject("my", WebContextFactory.get().getUser());
        return mv;
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    public ModelAndView upmy(User user, String repass){
        log.info("===>>> user:{}", new Gson().toJson(user));
        ModelAndView mv = new ModelAndView("backend/new/user-my");
        Map<String, Object> form = UserFormValidator.validateMy(user, repass);
        if(!form.isEmpty()){
            log.info("===>>> form:{}", new Gson().toJson(form));
            mv.addObject("form", form);
            mv.addObject("my", user);
            return mv;
        }
        user.setRole(WebContextFactory.get().getUser().getRole());
        user.setLastUpdate(new Date());
        userService.update(user);
        mv.setViewName("redirect:/backend/users/1");
        return mv;
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

}
