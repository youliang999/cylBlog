package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.helper.OptionHelper;
import com.cyl.blog.constants.Constants;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.entity.User;
import com.cyl.blog.helper.CookieRemberManager;
import com.cyl.blog.helper.FunctionHelper;
import com.cyl.blog.helper.WebContextFactory;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CommentService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.shiro.StatelessToken;
import com.cyl.blog.util.CookieUtil;
import com.cyl.blog.util.ServletUtils;
import com.cyl.blog.util.StringUtils;
import com.google.gson.Gson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/4.
 */
@Controller
@RequestMapping("/backend")
public class BackendController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(BackendController.class);
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    CommentService commentService;
    @Autowired
    OptionHelper optionHelper;
    @Autowired
    BlogHelper blogHelper;


    @RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
    @RequestMapping(value = "/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("backend/new/index");
        return mv;
    }

    @RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
    @RequestMapping("/welcome")
    public ModelAndView welcome() {
        long start = System.currentTimeMillis();
        ModelAndView mv = new ModelAndView("/backend/new/welcome");

        mv.addObject("osInfo", optionHelper.getOsInfo());
//        log.info("===>>> osInfo cost {} ms", System.currentTimeMillis() - start);
        long start1 = System.currentTimeMillis();
        blogHelper.getBaseInfo(mv);
//        log.info("===>>> getBaseInfo cost {} ms", System.currentTimeMillis() - start1);
//        log.info("===>>> welcome cost {} ms", System.currentTimeMillis() - start);
        mv.addObject("currentUser", getUser());
        return mv;
    }


    @RequestMapping(value = "/login")
    public ModelAndView login(String msg,
                              @RequestParam(value = "loginInterceptorUrl", required = false, defaultValue = "") String requestUrl){
        ModelAndView mv = new ModelAndView("backend/login-new");
        log.info("get type login =============>");
        cookieFunction(mv);
        if(WebContextFactory.get().isLogon() && !StringUtils.isBlank(requestUrl)) {
            mv.setViewName("redirect:" + requestUrl);
            return mv;
        }
        if(WebContextFactory.get().isLogon()) {
            mv.setViewName("redirect:/backend/index");
            return mv;
        }
        if("logout".equals(msg)){
            mv.addObject("msg", "您已登出。");
        }else if("unauthenticated".equals(msg)){
            mv.addObject("msg", "你没有当前操作权限");
        }
        return mv;
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        CookieRemberManager.logout(request, response);
        SecurityUtils.getSubject().logout();
        CookieUtil cookieUtil = new CookieUtil(request, response);
        cookieUtil.removeCokie(Constants.COOKIE_CSRF_TOKEN);
        cookieUtil.removeCokie("comment_author");
        cookieUtil.removeCokie("comment_author_email");
        cookieUtil.removeCokie("comment_author_url");

        return "redirect:/backend/login?msg=logout";
    }


    @RequestMapping(value = "/loginsubmit")
    @ResponseBody
    public String dashboard(@RequestParam(value = "username") String userName,
                            @RequestParam(value = "userpwd") String userpwd,
                            HttpServletRequest request, HttpServletResponse response){
        log.info("post type login =============>");
        log.info("===>>> input form:{}", userName);
//        Map<String, String> result = LoginFormValidator.validateLogin(form);
//        if(!result.isEmpty()){
//            request.setAttribute("msg", result.get("msg"));
//            return "backend/login";
//        }
        //log.info("===>>> input form:{}", new Gson().toJson(form));
        Map<String, String> data = new HashMap<>();
        User user = userService.login(userName, userpwd);
//        if(user == null) {
//            data.put("msg", "用户不存在");
//            data.put("url", "backend/login");
//            return new Gson().toJson(data);
//        }
//        UserLoginManager.getInstance().makeUserInfo(request, response, user.getId());
        if(user == null) {
            data.put("msg", "用户名密码错误");
            data.put("url", "backend/login");
            return new Gson().toJson(data);
        }

        SecurityUtils.getSubject().login(new StatelessToken(user.getId(), user.getPassword()));
        CookieUtil cookieUtil = new CookieUtil(request, response);
    /* 根据RFC-2109中的规定，在Cookie中只能包含ASCII的编码 */
        cookieUtil.setCookie(Constants.COOKIE_USER_NAME, userName, false, 7 * 24 * 3600);
        cookieUtil.setCookie("comment_author", user.getNickName(), "/", false, 365 * 24 * 3600);
        cookieUtil.setCookie("comment_author_email", user.getEmail(), "/", false, 365 * 24 * 3600, false);
        cookieUtil.setCookie("comment_author_url", ServletUtils.getDomain(request), "/", false, 365 * 24 * 3600, false);

        CookieRemberManager.loginSuccess(request, response, user.getId(), user.getPassword(), true);
        data.put("url", "backend/login");
        return new Gson().toJson(data);
    }

    private void cookieFunction(ModelAndView mv) {
        Map<String, Object> fh = new HashMap<>();
        fh.put("username", FunctionHelper.cookieValue("un"));
        mv.addObject("fh", fh);
    }

}
