package com.cyl.blog.interceptors;

import com.cyl.blog.entity.User;
import com.cyl.blog.entity.VisitInfo;
import com.cyl.blog.helper.CookieRemberManager;
import com.cyl.blog.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/9/14.
 */
public class VisitRecordInterceptor extends AbstractDetectingUrlInterceptor  {
    private static final ExecutorService executorService = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.CallerRunsPolicy());
    private static final Logger LOG = LoggerFactory.getLogger(SessionBindInterceptor.class);
    @Autowired
    private VisitService visitService;

    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ip = getIpAddr(request);
        String cookie = getCookie(request, response);
        String visitUrl = request.getRequestURI();
        Date visitDate = new Date();
        VisitInfo visitInfo = new VisitInfo();
        visitInfo.setIp(ip);
        visitInfo.setCookie(cookie);
        visitInfo.setVisitUrl(visitUrl);
        visitInfo.setVisitDate(visitDate);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    visitService.insertVisitRecord(visitInfo);
                    LOG.info("===>>> insert a record:{}", ip);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    public String getCookie(HttpServletRequest request, HttpServletResponse response) {
        User user = CookieRemberManager.extractValidRememberMeCookieUser(request, response);
        if(user != null) {
            return user.getNickName();
        }
        return "";
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
