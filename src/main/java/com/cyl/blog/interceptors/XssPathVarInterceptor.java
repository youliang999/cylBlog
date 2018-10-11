package com.cyl.blog.interceptors;

import com.cyl.blog.filter.XSSUtil;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


public class XssPathVarInterceptor extends AbstractDetectingUrlInterceptor {
    @Override
    protected boolean doPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> pathVariables = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (pathVariables == null) {
            return true;
        }
        Map<String, Object> newParameterMap = new HashMap<String, Object>();
        for (String key : pathVariables.keySet()) {
            Object value = pathVariables.get(key);
            if (value instanceof String) {
                newParameterMap.put(key, XSSUtil.stripXSS((String) value));
            } else {
                newParameterMap.put(key, value);
            }
        }
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, newParameterMap);
        return true;
    }
}
