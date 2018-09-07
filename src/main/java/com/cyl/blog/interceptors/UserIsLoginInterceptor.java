package com.cyl.blog.interceptors;

import com.cyl.blog.entity.User;
import com.cyl.blog.helper.CookieRemberManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by youliang.cheng on 2018/9/5.
 */
public class UserIsLoginInterceptor extends  AbstractDetectingUrlInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(UserIsLoginInterceptor.class);

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String user = (String) request.getSession().getAttribute("comment_author");
        User user = CookieRemberManager.extractValidRememberMeCookieUser(request, response);
        LOG.info("currentUser: "+ user);
        //LOG.info("name: {}"+current.getNickName());

        if(user == null) {
            if(request.getRequestURI() != null ) {
                LOG.info("request url(): {}"+request.getRequestURI());
                response.sendRedirect("/");
            }
            LOG.info("SessionBindInterceptor user is null.");
            return true;
        } else {
            LOG.info("===>>> user :{} is login", user.getNickName());
        }
        return true;
    }
}
