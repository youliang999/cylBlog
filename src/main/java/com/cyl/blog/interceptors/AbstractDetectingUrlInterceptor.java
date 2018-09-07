package com.cyl.blog.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * User:tao.li
 * Date: 11-12-20
 * Time: 下午6:30
 */
public abstract class AbstractDetectingUrlInterceptor extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDetectingUrlInterceptor.class);
    protected static final int DEFAULT_PORT = 80;

    private PathMatcher pathMatcher = new AntPathMatcher();

    private List<String> excludeUrlPatterns;

    protected abstract boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;

    /**
     * 判定所请求的路径是否属于放行路径，<br />
     * 如果为放行路径，返回为true直接放行<br />
     * 如果非放行路径，执行子类的业务逻辑
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (isExcludeUrl(getUrlPath(request))) {
            LOGGER.info("path {} is exclude url", getUrlPath(request));
            return true;
        }
        return doPreHandle(request, response, handler);
    }

    /**
     * 判定path是否是放行URL
     *
     * @param path 用户请求路径
     * @return
     */
    protected boolean isExcludeUrl(String path) {
        return patternUrl(excludeUrlPatterns, path);
    }

    /**
     * 获取请求中的URL path信息
     *
     * @param request
     * @return
     */
    protected String getUrlPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        if (servletPath == null || "/".equals(servletPath)) {
            servletPath = "";
        }
        String pathInfo = request.getPathInfo();
        if(pathInfo == null || "/".equals(pathInfo)) {
            pathInfo = "";
        }
        String res =  servletPath + pathInfo;
        return res;
    }

    public PathMatcher getPathMatcher() {
        return pathMatcher;
    }

    public void setPathMatcher(PathMatcher pathMatcher) {
        this.pathMatcher = pathMatcher;
    }

    public List<String> getExcludeUrlPatterns() {
        return excludeUrlPatterns;
    }

    public void setExcludeUrlPatterns(List<String> excludeUrlPatterns) {
        this.excludeUrlPatterns = excludeUrlPatterns;
    }

    private boolean patternUrl(List<String> urlPatterns, String path) {
        if (urlPatterns == null || urlPatterns.isEmpty()) {
            return false;
        }
        for (String pattern : urlPatterns) {
            if (pathMatcher.match(pattern, path)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户的请求URL，主要用于URL调整
     *
     * @param request
     * @return
     */
    protected String getForwardUrl(HttpServletRequest request) {
        int port = request.getServerPort();
        String servletPath = request.getServletPath();
        if (servletPath == null || "/".equals(servletPath)) {
            servletPath = "";
        }
        StringBuilder stringBuilder = new StringBuilder(request.getScheme())
                .append("://").append(request.getServerName()).append(port == DEFAULT_PORT ? "" : ":" + port)
                .append(request.getContextPath()).append(servletPath).append(request.getPathInfo());
        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {
            //stringBuilder.append("?").append(HtmlFilterUtil.filterHeaderValue(request.getQueryString()));
        }
        return stringBuilder.toString();
    }
}
