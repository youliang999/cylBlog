package com.cyl.blog.filter;

import com.google.common.collect.Sets;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class XssFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(XssFilter.class);
    private Map<String, Set<String>> excludeUrlRules;
    private Map<String, Set<String>> allowedDomains;
    private int validLength = 20;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludeUrls = filterConfig.getInitParameter("excludeUrls");
        if (StringUtils.isNotBlank(excludeUrls)) {
            excludeUrlRules = parseInitParam(excludeUrls);
        }
        String redirAllowedDomains = filterConfig.getInitParameter("redirAllowedDomains");
        if (StringUtils.isNotBlank(redirAllowedDomains)) {
            allowedDomains = parseInitParam(redirAllowedDomains);
        }
        this.validLength = NumberUtils.toInt(filterConfig.getInitParameter("validLength"), validLength);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        XSSRequestWrapper xssRequestWrapper = new XSSRequestWrapper(httpServletRequest, excludeUrlRules, allowedDomains, validLength);
        chain.doFilter(xssRequestWrapper, response);
    }

    @Override
    public void destroy() {
    }

    private Map<String, Set<String>> parseInitParam(String paramStr) {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        String[] rules = paramStr.split(";");
        for (String line : rules) {
            if (StringUtils.isBlank(line)) {
                continue;
            }
            line = line.trim();
            String[] fields = line.split("\\s{1,}");
            if (ArrayUtils.isEmpty(fields)) {
                continue;
            }
            String url = fields[0];
            Set<String> paramNames = new HashSet<String>();
            if (fields.length > 1 && !fields[1].equalsIgnoreCase("*")) {
                paramNames = Sets.newHashSet(fields[1].split(","));
            }
            map.put(url, paramNames);
        }
        LOGGER.debug("=====>IniitParam:{}", map);
        return map;
    }
}
