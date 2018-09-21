package com.cyl.blog.interceptors;


import com.cyl.blog.databind.DataBind;
import com.cyl.blog.databind.DataBindManager;
import com.cyl.blog.databind.DataBindTypeEnum;
import com.cyl.blog.entity.User;
import com.cyl.blog.helper.CookieRemberManager;
import com.cyl.blog.util.ServletUtils;
import com.cyl.blog.vo.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by youliang.cheng on 2018/1/25.
 */
public class SessionBindInterceptor extends AbstractDetectingUrlInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(SessionBindInterceptor.class);
    private static final DataBind<User> LOGIN_USER_BIND = DataBindManager.getInstance().getDataBind(DataBindTypeEnum.LOGIN_USER);
    private static final DataBind<Global> global = DataBindManager.getInstance().getDataBind(DataBindTypeEnum.GLOBAL);

//    @Autowired
//    @Qualifier("configCache")
//    private JedisOperationService<String, String> configCache;

//    private final SessionContext sessionContext = SessionContext.getInstance();

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        User user = (User)request.getSession().getAttribute("comment_author");
        User user = CookieRemberManager.extractValidRememberMeCookieUser(request, response);
        global.put(new Global(ServletUtils.getDomain(request)));
        if(user != null) {
            LOG.info("currentUser: " + user.getNickName());
            //sessionContext.bind(user);
            LOGIN_USER_BIND.put(user);
        }
        //LOG.info("name: {}"+current.getNickName());
        String ip = getRemortIP(request);
        LOG.info("===>>> user ip:{}", ip);

       // configCache.set(ip, new Date().toString());
//        LOG.info("===>>> user ip2:{}", getIpAddr(request));
        return true;
    }

    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }

//    public String getIpAddr(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip;
//    }
}
