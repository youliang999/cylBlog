package com.cyl.blog.filter;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Pattern;

/**
 * User: baocheng.chen
 * Date: 2018-09-21
 * Time: 下午14:46
 */
public class XSSUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(XSSUtil.class);

    private static final List<String> CONTENT_BAD_WORDS = Lists.newArrayList(
            "script",
            "0x736372697074",
            "src",
            "\\(",
            "%28",
            "\\)",
            "%29",
            "eval",
            "expression",
            "javascript:",
            "vbscript:",
            "onload",
            "%3D",
            "prompt",
            "confirm",
            "onmousemove",
            "onerror",
            "onmouseover",
            "onclick",
            "location",
            //null
            "%0a",
            "%0d",
            "%00",
            "`",
            "%60",
            "<",
            "%3C",
            ">",
            "%3E",
            "'",
            "%27",
            "%c0",
            "%bf",
            "%5c",
            "\0",
            "\\\\",
            "%5C",
            "/\\*",
            "\\*/",
            "%2F",
            "#",
            "%23",
            "%",
            "src",
            "fromCharCode",
            "jscript",
            "!",
            "%21",
            "\\[",
            "%5B",
            "\\]",
            "%5D"
    );

    private static final List<String> BAN_DOMAINS = Lists.newArrayList(
            "luiot\\.com\\.cn",
            "ejdang\\.cn",
            "whaimixiu\\.com",
            "wsipx\\.cn",
            "fangniuw\\.cn",
            "bbabcf\\.cn",
            "wsiqx\\.cn",
            "bbbcea\\.cn",
            "ganxibao007\\.net",
            "wsi.*\\.cn"
    );

    private static final Pattern BAN_DOMAINS_PATTERN = Pattern.compile(Joiner.on("|").join(BAN_DOMAINS), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

    public static String filterRedirDomain(String value) {
        String after = value;
//        if (value != null && !DAJIE_URL_PATTERN.matcher(value).find()) {
//            after = "";
//        }
        LOGGER.debug("=====>before:{}, after:{}", value, after);
        return after;
    }

    public static String[] filterRedirDomain(String[] value) {
        if (ArrayUtils.isEmpty(value)) {
            return value;
        }
        String[] arr = new String[value.length];
        for (int i = 0; i < value.length; i++) {
            arr[i] = filterRedirDomain(value[i]);
        }
        return arr;
    }

    /**
     * 防止编码攻击方法，检查传入的参数 也可以当做工具类使用
     * <img%20src%3D%26%23x6a;%26%23x61;%26%23x76;%26
     * %23x61;%26%23x73;%26%23x63;%
     * 26%23x72;%26%23x69;%26%23x70;%26%23x74;%26%23x3a
     * ;alert%26%23x28;27111%26%23x29;>
     */
    public static String stripXSS(String value) {
        if (value == null || value.length() <= 20) {
            return value;
        }
        if (BAN_DOMAINS_PATTERN.matcher(value).find()) {
            return "";
        }
        for (String badWord : CONTENT_BAD_WORDS) {
            value = value.replaceAll(badWord, "");
        }
        return filterColon(value);
    }

    private static String filterColon(String value) {
        if (value.contains(":") && ("http".equalsIgnoreCase(StringUtils.substring(value, value.indexOf(':') - 4, value.indexOf(':'))) || "https".equalsIgnoreCase(StringUtils.substring(value, value.indexOf(':') - 5, value.indexOf(':'))))) {
            return value;
        } else if (value.contains("%3A") && ("http".equalsIgnoreCase(StringUtils.substring(value, value.indexOf("%3A") - 4, value.indexOf("%3A"))) || "https".equalsIgnoreCase(StringUtils.substring(value, value.indexOf("%3A") - 5, value.indexOf("%3A"))))) {
            return value;
        }
        value = value.replaceAll("%3A", "");
        value = value.replaceAll(":", "");
        return value;
    }
}
