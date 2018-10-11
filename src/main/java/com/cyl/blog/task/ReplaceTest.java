package com.cyl.blog.task;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.helper.ApplicationContextUtil1;
import com.cyl.blog.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youliang.cheng on 2018/10/8.
 */
public class ReplaceTest {
    private static final Logger log = LoggerFactory.getLogger(IBMSpliderTask.class);
    private static final BlogService blogService = (BlogService) ApplicationContextUtil1.getInstance().getBlogService();

    public static void main(String[] args) {
            blogService.delById(String.valueOf(49388));
            System.exit(0);
    }

    //48935

    private void update() {
        Blog blog = blogService.getBlogById("49118");
        String content = blog.getContent();
        log.info("c :{}", content);
        String newC = repairContent(content, "https://www.ibm.com/developerworks/cn/java/j-lo-audit-xss/");
        blog.setContent(newC);
        blogService.updateBlog(blog);
    }


    /**
     *将img标签中的src进行二次包装
     *@paramcontent 内容
     *@paramreplaceHttp 需要在src中加入的域名
     *@paramsize 需要在src中将文件名加上_size
     *@return
     */
    public static String repairContent(String content,String replaceHttp){
        String patternStr="<img\\s*([^>]*)\\s*src=\\\"(.*?)\\\"\\s*([^>]*)>";
        Pattern pattern=Pattern.compile(patternStr,Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(content);
        String result=content;
        while(matcher.find()){
            String src=matcher.group(2);
            System.out.println("patternstring:"+src);
            String replaceSrc="";
            if(src.lastIndexOf(".")>0){
                replaceSrc=src.substring(0,src.lastIndexOf("."))+src.substring(src.lastIndexOf("."));
            }
            if(!src.startsWith("http://")&&!src.startsWith("https://")){
                replaceSrc=replaceHttp+replaceSrc;
            }
            result=result.replaceAll(src,replaceSrc);
        }
        return result;
    }
}
