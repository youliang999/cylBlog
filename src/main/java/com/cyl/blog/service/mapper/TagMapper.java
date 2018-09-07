package com.cyl.blog.service.mapper;


import com.cyl.blog.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagMapper extends BaseMapper {

  public int insertBatch(List<Tag> list);
  
  public int deleteByPostid(String postid);
  
  public List<String> getTagsByPost(String postid);

  List<String> getPostsByTag(String name);

  List<String> getPostsByTagLimit(Map<String, Object> data);

  int countPostsByTag(String name);

  List<String> getTags(int limit);

  List<String> isExist(String postid);

  List<Integer> getPostIdsByTags(List<String> name);

}
