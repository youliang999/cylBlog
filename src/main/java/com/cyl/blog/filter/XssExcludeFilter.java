package com.cyl.blog.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * Created by youliang.cheng on 2018/9/24.
 */
public class XssExcludeFilter extends XssFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XssExcludeFilter.class);
    private static final String BLOG_POST_PREFIX = "/backend/posts";


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request1 = (HttpServletRequest) request;
        String requestUrl = request1.getRequestURI();
        LOGGER.info("===>>> requestUrl:{}", requestUrl);
        if (requestUrl.startsWith(BLOG_POST_PREFIX)) {
            LOGGER.info("===>>> url:{} not fiter", requestUrl);
            chain.doFilter(request, response);
        } else {
            super.doFilter(request, response, chain);
        }
    }

    @Override
    public void destroy() {
    }
}
