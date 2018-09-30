package com.cyl.blog.entity.builder;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.BlogContent;
import com.cyl.blog.entity.BlogV1;

import java.io.Serializable;

/**
 * Created by youliang.cheng on 2018/9/30.
 */
public class BlogBuilder implements Serializable{
    private static final long serialVersionUID = -4670657896878614379L;
    private int id;
    private Blog entity;

    private Blog getEntity() {
        if (this.entity == null) {
            this.entity = new Blog();
        }
        return this.entity;
    }

    public BlogBuilder(BlogV1 blogV1) {
        if(blogV1 == null || Integer.valueOf(blogV1.getId()) <= 0) {
            return;
        }
        this.id = Integer.valueOf(blogV1.getId());
        withBlogV1(blogV1);
    }

    public Blog build() {
        return this.entity;
    }

    public void withBlogContent(BlogContent blogContent) {
        if(blogContent == null || blogContent.getBlog_id() <= 0) {
            return;
        }
        getEntity().setExcerpt(blogContent.getExcerpt());
        getEntity().setContent(blogContent.getContent());
    }



    private void withBlogV1(BlogV1 blogV1) {
        getEntity().setId(blogV1.getId());
        getEntity().setTitle(blogV1.getTitle());
        getEntity().setType(blogV1.getType());
        getEntity().setParent(blogV1.getParent());
        getEntity().setCategoryid(blogV1.getCategoryid());
        getEntity().setPstatus(blogV1.getPstatus());
        getEntity().setCstatus(blogV1.getCstatus());
        getEntity().setCcount(blogV1.getCcount());
        getEntity().setRcount(blogV1.getRcount());
        getEntity().setCreator(blogV1.getCreator());
        getEntity().setBlog_type(blogV1.getBlog_type());
        getEntity().setCreateTime(blogV1.getCreateTime());
        getEntity().setLastUpdate(blogV1.getLastUpdate());
    }
}
