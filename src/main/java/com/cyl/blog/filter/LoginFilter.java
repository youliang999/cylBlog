package com.cyl.blog.filter;

import com.cyl.blog.constants.Constants;
import com.cyl.blog.constants.WebConstants;
import com.cyl.blog.helper.CookieRemberManager;
import com.cyl.blog.helper.WebContext;
import com.cyl.blog.helper.WebContextFactory;
import com.cyl.blog.shiro.StatelessToken;
import com.cyl.blog.util.ServletUtils;
import com.cyl.blog.util.Threads;
import com.cyl.blog.vo.Global;
import com.dajie.common.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.util.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by youliang.cheng on 2018/7/30.
 */
public class LoginFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
    private static final String redisKey = "GlobalData";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        LOG.info("===>>> LoginFilter... {}", request.getRequestURL());
        String requestUrl = request.getRequestURL().toString();
        if(StringUtil.isEmpty(requestUrl)) {
            return;
        }
        if(requestUrl.endsWith(".js") ||
                requestUrl.endsWith(".css") ||
                requestUrl.endsWith(".png") ||
                requestUrl.endsWith(".ico") ||
                requestUrl.endsWith(".jpg") ||
                requestUrl.endsWith(".gif") ||
                requestUrl.contains(".js") ||
                requestUrl.contains(".ttf") ||
                requestUrl.contains(".css") ||
                requestUrl.contains(".png") ||
                requestUrl.contains(".ico") ||
                requestUrl.contains(".jpg") ||
                requestUrl.contains(".gif") ||
                requestUrl.contains(".eot") ||
                requestUrl.contains(".svg") ||
                requestUrl.contains(".woff") ||
                requestUrl.contains(".otf") ||
                requestUrl.contains(".swf")
                ) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpServletRequest req1 = new XssHttpServletRequestWrapper(request);
        WebContext context = WebContextFactory.get();
        if(context != null)
            return;

        try{
            context = new WebContext(request, response);
            context.setUser(CookieRemberManager.extractValidRememberMeCookieUser(request, response));
            // 保存上下文
            WebContextFactory.set(context);

            accessControl();
            boolean ajax = ServletUtils.isAjax(request);

            if(!ajax){
                request.setAttribute("g", new Global(ServletUtils.getDomain(request)));
               // LOG.info("------------------------- g.isAllowComment:{}", new Global(ServletUtils.getDomain(request)).isAllowComment());
            }

      /* 设置domain */
            WebConstants.setDomain(ServletUtils.getDomain(request));

            filterChain.doFilter(request, response);
            HttpServletRequest req2 = new XssHttpServletRequestWrapper(request);
        }catch(Exception e){
            if(ServletUtils.isAjax(request)){
                logger.error("error happend", e);
                response.setContentType("application/json");
                response.setCharacterEncoding(Constants.ENCODING_UTF_8.name());
                response.getWriter().write("{'status':'500','success':false,'msg':'操作失败,服务端出错'}");
            }else{
                handleException(Threads.getRootCause(e), request, response);
            }
        }finally{
            WebContextFactory.remove();
            cleanup();
        }
    }

    private void accessControl(){
        WebContext context = WebContextFactory.get();

        if(context.isLogon()){
      /* 注意：此处要委托给Realm进行登录 */
            logger.info("logon-->" + context.getRequest().getRequestURI());
            SecurityUtils.getSubject().login(new StatelessToken(context.getUser().getId(), context.getUser().getPassword()));
        }
    }

    /**
     * 此处需要unbind,同见
     * {@link org.apache.shiro.web.servlet.AbstractShiroFilter#doFilterInternal}中
     * subject.execute(..) ,不能Subject.logout()(并未移除ThreadContext中的Subject对象)
     *
     * @see <a href="http://shiro.apache.org/subject.html">Subject</a>
     */
    private void cleanup(){
        ThreadContext.unbindSubject();
    }

    /**
     * 统一处理controller的异常，这里不使用springmvc的异常处理体系
     *
     * @param t
     * @param response
     */
    private void handleException(Throwable t, HttpServletRequest request, HttpServletResponse response){
        if(t instanceof AuthorizationException){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // String uri = StringUtils.emptyDefault(request.getRequestURI(), "/");
            // String encodeURL = UrlUtil.encode(uri
            // + (StringUtils.isBlank(request.getQueryString()) ? "" : "?" +
            // request.getQueryString()));
            //
            // ServletUtils.sendRedirect(response, "/backend/login?redirectURL=" +
            // encodeURL);
            ServletUtils.sendRedirect(response, "/resource/error/unauthenticated.html");
        }else{
            logger.error("error happend", t);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ServletUtils.sendRedirect(response, "/resource/error/500.html");
        }
    }

}

