package com.cyl.blog.controller;

import com.cyl.blog.databind.DataBind;
import com.cyl.blog.databind.DataBindManager;
import com.cyl.blog.databind.DataBindTypeEnum;
import com.cyl.blog.entity.User;
import com.cyl.blog.vo.Global;

/**
 * Created by youliang.cheng on 2018/8/17.
 */
public class BaseController {
    private static final DataBind<Global> global = DataBindManager.getInstance().getDataBind(DataBindTypeEnum.GLOBAL);
    private static final DataBind<User> LOGIN_USER_BIND = DataBindManager.getInstance().getDataBind(DataBindTypeEnum.LOGIN_USER);

    public Global getGlobal() {
        return global.get();
    }

    public User getUser() {
        return LOGIN_USER_BIND.get();
    }
}
