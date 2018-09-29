package com.cyl.blog.task;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.helper.ApplicationContextUtil1;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

/**
 * Created by youliang.cheng on 2018/9/29.
 */
public class ImportDataTask {
    private static final Logger log = LoggerFactory.getLogger(ImportDataTask.class);
    private static final BlogService blogService = (BlogService) ApplicationContextUtil1.getInstance().getBlogService();
    private int start = 40032;

    public static void main(String[] args) {
        ImportDataTask task = new ImportDataTask();
        task.execute();
        System.exit(0);
    }

    private void execute() {
        while(true) {
            List<Blog> blogs = getBlogs();
            log.info("===>>> size:{}", blogs.size());
            if(CollectionUtils.isEmpty(blogs)) {
                break;
            }
            for (Blog blog : blogs) {
                int bid = Integer.valueOf(blog.getId());
                if(blog == null) {
                    continue;
                }
                if(bid > start) {
                    start = bid;
                }
                boolean res = blogService.insertBlogV2(blog);
                log.info("===>>> execute result:{}", res);
            }

        }
    }

    private List<Blog> getBlogs() {
        List<Blog> blogs = blogService.getBlogByCondition(start, 2000);
        if(blogs == null || CollectionUtils.isEmpty(blogs)) {
            return Collections.EMPTY_LIST;
        }
        return blogs;
    }

}
