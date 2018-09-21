package com.cyl.blog.service;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Category;
import com.cyl.blog.plugin.PageIterator;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/7/30.
 */
public interface BlogService extends BlogBaseService{

    boolean insertBlog(Blog blog);

    boolean updateBlog(Blog blog);

    boolean delById(String id);

    PageIterator<Blog> getBlogs(int page, int pageSize);

    PageIterator<BlogVo> getBlogVos(int page, int pageSize);

    PageIterator<BlogVo> getAllBlogVos(int page, int pageSize);

    BlogVo getBlogVoById(String id);

    List<BlogVo> getBlogVoByIds(List<String> bids);

    PageIterator<BlogVo> getBlogVosByTag(String tagName, int page, int pageSize);

    PageIterator<BlogVo> getBlogVosByCategory(Category category, int page, int pageSize);

    int getVisitCountByids(List<String> ids);

    int countBlog();

    BlogVo getNextBlogVo(String id);

    BlogVo getPrevBlogVo(String id);

    List<Blog> getRecentBlogs(int limit);

    List<Blog> getBlogByTitle(String title);

    Blog getBlogById(String id);

    int addCcount(String commentid, int count);

}
