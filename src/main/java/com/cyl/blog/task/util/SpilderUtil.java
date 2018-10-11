package com.cyl.blog.task.util;

import com.cyl.blog.util.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youliang.cheng on 2018/10/8.
 */
public class SpilderUtil {
    private static final String cookie = "";
    public static CookieStore cookieStore = null;
    private static CloseableHttpClient client = null;

    public static CookieStore setCookieStore(String cook) {
        System.out.println("----setCookieStore");
        CookieStore cookieStore = new BasicCookieStore();
        if (cook.length() <= 0) {
            return cookieStore;
        }
        String[] cs = cook.split(";");
        if (cs.length <= 0) {
            return cookieStore;
        }
        for (String coo : cs) {
            String[] coos = coo.split(":");
            if (coos != null && coos.length == 2) {
                String name = coos[0];
                String value = coos[1];
                BasicClientCookie cookie = new BasicClientCookie(name,
                        value);
                cookieStore.addCookie(cookie);
            }

        }
        return cookieStore;
    }

    public static CloseableHttpClient getClient(String myCookie) {
        if(StringUtils.isBlank(myCookie)) {
            cookieStore = setCookieStore(cookie);
        } else {
            cookieStore = setCookieStore(myCookie);
        }
        client =  HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();
        return client;
    }

    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;

        }
    }

    /**
     *将img标签中的src进行二次包装
     *@paramcontent 内容
     *@paramreplaceHttp 需要在src中加入的域名
     *@paramsize 需要在src中将文件名加上_size
     *@return
     */
    public static String repairContent(String content,String replaceHttp){
        Set<String> set = new HashSet<>();
        String patternStr="<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern=Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(content);
        String result=content;
        while(matcher.find()){
            String src=matcher.group(2);
            System.out.println("src: " + src);
            if(set.contains(src)) {
                continue;
            }
            set.add(src);
            if(src.startsWith("http://") || src.startsWith("https://") || src.startsWith("//")) {
                continue;
            }
            System.out.println("patternstring:"+src);
            String replaceSrc="";
            if(src.lastIndexOf(".")>0){
                replaceSrc=src.substring(0,src.lastIndexOf("."))+src.substring(src.lastIndexOf("."));
            }
            if(!src.startsWith("http://")&&!src.startsWith("https://")&&!src.startsWith("//")){
                replaceSrc=replaceHttp+replaceSrc;
            }
            System.out.println("===>>> replaceStr: " + replaceSrc);
            content=content.replaceAll(src,replaceSrc);
//            System.out.println("===>>> content: " + content);
        }
        return content;
    }

    public static void main(String[] args) {
        String c = "<p>当出现图\n" +
                "                                                1 中的窗口时，请按下按钮。该应用程序将加载这些类。</p><h5 id=\"N102AF\" class=\"ibm-h5\">图 1. 按下按钮</h5><img src=\"figure1.gif\" class=\"ibm-downsize\" alt=\"加载类的图像\" height=\"95\" width=\"337\"><p class=\"ibm-ind-link ibm-hide\"><a class=\"ibm-popup-link\" onclick=\"IBMCore.common.widget.overlay.show('N102B1');return false;\" href=\"#N102B1\">点击查看大图</a></p><p>加载类之后，应用程序会报告它加载了多少个类和所用的时间，如图 2 所示：</p><h5 id=\"N102B9\" class=\"ibm-h5\">图 2. 结果出来了！</h5><img src=\"figure2.gif\" class=\"ibm-downsize\" alt=\"\" height=\"92\" width=\"335\"><p class=\"ibm-ind-link ibm-hide\"><a class=\"ibm-popup-link\" onclick=\"IBMCore.common.widget.overlay.show('N102BB');return false;\" href=\"#N102BB\">点击查看大图</a></p><p>您会注意到，应用程序每次运行时都可能会更快一点；这得益于操作系统优化。</p>";
        System.out.println("new c:" + repairContent(c, "https://www.ibm.com/developerworks/cn/java/j-class-sharing-openj9/"));
    }
}
