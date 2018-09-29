package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
public interface BlogMapper extends BaseMapper {

    int insertBlog(Blog blog);

    List<Blog> getBlogsByCondition(Map<String, Object> map);

    List<Blog> getBlogs(Map<String, Object> map);

    List<Blog> getAllBlogs(Map<String, Object> map);

    Blog getBlogById(String id);

    List<Blog> getBlogsByIds(List<String> ids);

    int countBlog();

    String getNextBid(String bid);

    String getPrevBid(String bid);

    List<Blog> getRecentBlogs(int limit);

    List<Blog> getBlogByTitle(String title);

    List<String> getBlogVosByTag(String tagName);

    int countBlogIdsByCategory(List<String> categoryIds);

    List<String> getBlogIdsByCategory(Map<String, Object> data);


    int addCcount(@Param("commentid") String commentid, @Param("count") int count);
}
