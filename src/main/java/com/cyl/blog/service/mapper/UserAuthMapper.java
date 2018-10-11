package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.UserAuth;

/**
 * Created by youliang.cheng on 2018/10/9.
 */
public interface UserAuthMapper {

    int insertAuth(UserAuth userAuth);

    UserAuth getByUid(String uid);

    UserAuth getByUName(String nickName);

    int updateAuth(UserAuth userAuth);

    int deleteAuth(String uid);
}
