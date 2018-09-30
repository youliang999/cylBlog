package com.cyl.blog.entity.parse;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.BlogContent;
import com.cyl.blog.entity.BlogV1;
import com.cyl.blog.util.StringUtils;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/30.
 */
public class BlogParse implements Serializable {
    private static final long serialVersionUID = -8323998523813537964L;
    private Blog blog;

    public BlogParse(Blog blog) {
        this.blog = blog;
    }

    public BlogV1 parseBlogV1() {
        BlogV1 blogV1 = new BlogV1();
        blogV1.setId(blog.getId());
        blogV1.setTitle(blog.getTitle());
        blogV1.setType(blog.getType());
        blogV1.setParent(blog.getParent());
        blogV1.setCategoryid(blog.getCategoryid());
        blogV1.setPstatus(blog.getPstatus());
        blogV1.setCstatus(blog.getCstatus());
        blogV1.setCcount(blog.getCcount());
        blogV1.setRcount(blog.getRcount());
        blogV1.setCreator(blog.getCreator());
        blogV1.setBlog_type(blog.getBlog_type());
        blogV1.setCreateTime(blog.getCreateTime());
        blogV1.setLastUpdate(blog.getLastUpdate());
        return blogV1;
    }

    public BlogContent parseBlogContent() {
        BlogContent blogContent = new BlogContent();
        blogContent.setBlog_id(Integer.valueOf(blog.getId()));
        blogContent.setContent(StringUtils.isBlank(blog.getContent()) ? "" : blog.getContent());
        blogContent.setExcerpt(StringUtils.isBlank(blog.getExcerpt()) ? "" : blog.getExcerpt());
        return blogContent;
    }
}
