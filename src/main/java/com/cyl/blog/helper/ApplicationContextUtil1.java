package com.cyl.blog.helper;

import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.service.TagService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.solr.service.BlogIndexService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by youliang.cheng on 2018/8/15.
 */
public final class ApplicationContextUtil1 {

    private static final String[] CONFIG_FILES = new String[]{
            "classpath:config/*.xml"
    };
    private static final ApplicationContextUtil1 INSTANCE = new ApplicationContextUtil1();
    private final ClassPathXmlApplicationContext applicationContext;

    private ApplicationContextUtil1() {
        this.applicationContext = new ClassPathXmlApplicationContext(CONFIG_FILES);
        this.applicationContext.start();
    }

    public static ApplicationContextUtil1 getInstance() {
        return INSTANCE;
    }


    public CategoryService getCategoryService() {
        return (CategoryService) applicationContext.getBean("categoryService");
    }

    public BlogService getBlogService() {
        return (BlogService) applicationContext.getBean("blogService");
    }

    public UserService getUserService() {
        return (UserService) applicationContext.getBean("userService");
    }

    public TagService getTagService() {
        return  (TagService) applicationContext.getBean("tagService");
    }

    public BlogIndexService getBlogIndexService() {
        return (BlogIndexService) applicationContext.getBean("blogIndexService");
    }
}
