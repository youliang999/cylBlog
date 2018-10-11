package com.cyl.blog.backend.controller;

import com.cyl.blog.controller.BaseController;
import com.cyl.blog.entity.AuthMenu;
import com.cyl.blog.entity.Menu;
import com.cyl.blog.entity.User;
import com.cyl.blog.entity.UserAuth;
import com.cyl.blog.service.MenuService;
import com.cyl.blog.service.UserAuthService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.util.StringUtils;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
@Controller
@RequestMapping("/backend/auth")
public class AuthController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @RequestMapping("tree")
    public ModelAndView tree() {
        ModelAndView mv = new ModelAndView("/backend/new/authTree");
        mv.addObject("nickName", getUser().getNickName());
        return mv;
    }

    @RequestMapping("getTree")
    @ResponseBody
    public String getTree() {
        Map<String, Object> data = new HashMap<>();
        List<Menu> menus = menuService.getAllMenu();
        UserAuth userAuth = userAuthService.getByUName(getUser().getNickName());
        List<String> authMenus = userAuth == null ? Collections.EMPTY_LIST : Arrays.asList(userAuth.getMenuList().split(","));
        List<AuthMenu> authedMenus = new ArrayList<>();
        for(Menu menu : menus) {
            AuthMenu authMenu = new AuthMenu();
            authMenu.setMid(menu.getMid());
            authMenu.setMname(menu.getMname());
            authMenu.setMurl(menu.getMurl());
            authMenu.setParentMid(menu.getParentMid());
            authMenu.setCreateDate(menu.getCreateDate());
            authMenu.setUpdateDate(menu.getUpdateDate());
            if(authMenus.contains(menu.getMid())) {
               authMenu.setAuthed(true);
            } else {
                authMenu.setAuthed(false);
            }
            authedMenus.add(authMenu);
        }
        data.put("menus", authedMenus);
        return new Gson().toJson(data);
    }

    @RequestMapping("save")
    @ResponseBody
    public String saveAuth(@RequestParam(value = "uid") String uid,
                           @RequestParam(value = "auths") String auths) {
        Map<String, Object> data = new HashMap<>();
        if(StringUtils.isBlank(uid) || StringUtils.isBlank(auths)) {
            data.put("msg", "save failed.");
            return new Gson().toJson(data);
        }
        User user = userService.getUserByNickName(uid);
        UserAuth userAuth = new UserAuth();
        userAuth.setMenuList(auths);
        userAuth.setNickName(uid);
        userAuth.setUserId(user.getId());
        UserAuth old = userAuthService.getByUName(uid);
        boolean res = false;
        if(old != null) {
            res = userAuthService.updateAuth(userAuth);
        } else {
            res = userAuthService.insertAuth(userAuth);
        }
        if(res) {
            data.put("msg", "save success.");
        } else {
            data.put("msg", "save failed.");
        }
        return new Gson().toJson(data);
    }

    @RequestMapping("addMenu")
    public ModelAndView addMenu() {
        ModelAndView mv = new ModelAndView("/backend/new/authTree");
        return mv;
    }
}
