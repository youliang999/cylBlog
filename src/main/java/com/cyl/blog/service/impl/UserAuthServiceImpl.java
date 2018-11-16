package com.cyl.blog.service.impl;

import com.cyl.blog.entity.UserAuth;
import com.cyl.blog.service.UserAuthService;
import com.cyl.blog.service.mapper.UserAuthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {
    private static final Logger log = LoggerFactory.getLogger(UserAuthService.class);
    @Autowired
    private UserAuthMapper userAuthMapper;

    @Override
    public boolean insertAuth(UserAuth userAuth) {
        log.info("===>>> insert a auth:{} ", userAuth);
        return userAuthMapper.insertAuth(userAuth) > 0;
    }

    @Override
    public UserAuth getByUid(String uid) {
        return userAuthMapper.getByUid(uid);
    }

    @Override
    public UserAuth getByUName(String nickName) {
        return userAuthMapper.getByUName(nickName);
    }

    @Override
    public boolean updateAuth(UserAuth userAuth) {
        log.info("===>>> update a auth:{} ", userAuth);
        return userAuthMapper.updateAuth(userAuth) > 0;
    }

    @Override
    public boolean deleteAuth(String uid) {
        return userAuthMapper.deleteAuth(uid) > 0;
    }
}
