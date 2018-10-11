package com.cyl.blog.filter;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;


public class XSSRequestWrapper extends HttpServletRequestWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(XSSRequestWrapper.class);
    private static final List<String> FORCE_CHECK_PARAM = Lists.newArrayList("redir", "url", "return");
    private PathMatcher pathMatcher = new AntPathMatcher();
    private Map<String, Set<String>> excludeUrlRules;
    private Map<String, Set<String>> redirAllowedDomains;
    private String currentUri;
    private int validLength;

    XSSRequestWrapper(HttpServletRequest request, Map<String, Set<String>> excludeUrlRules, Map<String, Set<String>> redirAllowedDomains, int validLength) {
        super(request);
        this.currentUri = request.getRequestURI();
        this.excludeUrlRules = excludeUrlRules;
        this.redirAllowedDomains = redirAllowedDomains;
        this.validLength = validLength;
    }

    @Override
    public String[] getParameterValues(String parameter) {
        return checkAndFilter(parameter, super.getParameterValues(parameter));
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        if (parameterMap == null) {
            return parameterMap;
        }
        Map<String, String[]> newParameterMap = new HashMap<String, String[]>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            newParameterMap.put(entry.getKey(), checkAndFilter(entry.getKey(), entry.getValue()));
        }
        return Collections.unmodifiableMap(newParameterMap);
    }

    /**
     * 对参数value进行xss 过滤
     */
    @Override
    public String getParameter(String parameter) {
        return checkAndFilter(parameter, super.getParameter(parameter));
    }

    /**
     * 对参数value进行xss 过滤
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (name.equalsIgnoreCase("referer")) {
            return XSSUtil.filterRedirDomain(value);
        }
        return XSSUtil.stripXSS(value);
    }

    private String[] checkAndFilter(String key, String[] values) {
        if (values == null) {
            return values;
        }
        int len = values.length;
        String[] newValues = new String[len];
        for (int i = 0; i < len; i++) {
            newValues[i] = checkAndFilter(key, values[i]);
        }
        return newValues;
    }

    private String checkAndFilter(String key, String value) {
        if (value == null || value.length() <= validLength) {
            return value;
        }
        if (checkUriRules(key)) {
            LOGGER.debug("=====>skip check. key:{}, value:{}", key, value);
            return value;
        }
        if (needCheckUrl(key)) {
            return filterDomain(key, value);
        }
        return XSSUtil.stripXSS(value);
    }

    private boolean needCheckUrl(String key) {
        return key != null && (FORCE_CHECK_PARAM.contains(key.toLowerCase()) || redirAllowedDomains != null && redirAllowedDomains.containsKey(key.toLowerCase()));
    }

    private String filterDomain(String key, String value) {
        LOGGER.debug("key:{}, value:{}", key, value);
        if (StringUtils.isBlank(value)) {
            return value;
        }
        if (redirAllowedDomains != null && redirAllowedDomains.containsKey(key)) {
            Set<String> domains = redirAllowedDomains.get(key);
            for (String domain : domains) {
                if (value.toLowerCase().contains(domain.toLowerCase())) {
                    LOGGER.debug("=====>paramName:{}, value:{}, allowedDomain:{}", key, value, domain);
                    return value;
                }
            }
        }
        return XSSUtil.filterRedirDomain(value);
    }

    private boolean checkUriRules(String requestParamName) {
        if (this.excludeUrlRules != null) {
            for (String urlPattern : excludeUrlRules.keySet()) {
                Set<String> whiteParamNames = excludeUrlRules.get(urlPattern);
                LOGGER.debug("=====>urlPattern:{}, whiteParamNames:{}, currentUri:{}", urlPattern, whiteParamNames, currentUri);
                if (pathMatcher.match(urlPattern, currentUri)) {
                    if (CollectionUtils.isEmpty(whiteParamNames)) {
                        return true;
                    }
                    for (String whiteParamName : whiteParamNames) {
                        if (requestParamName.equalsIgnoreCase(whiteParamName)) {
                            LOGGER.debug("=====>urlPattern:{}, whiteParamNames:{}, currentUri:{}", urlPattern, whiteParamNames, currentUri);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
