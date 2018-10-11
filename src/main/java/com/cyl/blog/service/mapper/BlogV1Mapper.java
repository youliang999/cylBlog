package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.BlogV1;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/29.
 */
public interface BlogV1Mapper extends BaseMapper{

    int insertBlogV1(BlogV1 blogv1);

    BlogV1 getById(int id);

    List<BlogV1> getBlogs(Map<String, Object> data);

    List<BlogV1> getAllBlogs(Map<String, Object> map);

    int countBlog();

    int countBlogIdsByCategory(List<String> categoryIds);

    List<String> getBlogIdsByCategory(Map<String, Object> data);

    List<BlogV1> getBlogsByIds(List<String> ids);

    String getNextBid(String bid);

    String getPrevBid(String bid);

    List<BlogV1> getRecentBlogs(int limit);

    List<BlogV1> getBlogByTitle(String title);

    int addCcount(@Param("commentid") String commentid, @Param("count") int count);

    int phydeleteById(String id);
}
