package com.cyl.blog.controller.index.model;

import com.cyl.blog.constants.UserConstants;
import lombok.Data;

/**
 * Created by youliang.cheng on 2018/7/12.
 */
@Data
public class User extends BaseBlog{
    private String nickName;
    private String realName;
    private String password;
    private String email;
    /* 用户账号状态 */
    private String status = UserConstants.USER_STATUS_NORMAL;
    private String role = UserConstants.USER_ROLE_CONTRIBUTOR;
    private String description;
}
