package com.cyl.blog.service;

import com.cyl.blog.entity.UserAuth;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
public interface UserAuthService {

    boolean insertAuth(UserAuth userAuth);

    UserAuth getByUid(String uid);

    UserAuth getByUName(String nickName);

    boolean updateAuth(UserAuth userAuth);

    boolean deleteAuth(String uid);

}
