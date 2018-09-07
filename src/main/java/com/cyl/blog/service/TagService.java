package com.cyl.blog.service;


import com.cyl.blog.entity.Tag;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.util.TagVO;

import java.util.List;

public interface TagService extends BlogBaseService{

    List<String> listTagsByPost(String postid);

    int insertBatch(List<Tag> tags);

    int deleteByPostid(String postid);

    List<String> getPostsByTag(String name);

    PageIterator<String> getPostsByTag(String name, int page, int pageSize);

    List<TagVO> getTags(int limit);

    List<String> getTagStrs(int limit);


    boolean isExist(String postid);
}
