package com.cyl.blog.service.impl;

import com.cyl.blog.entity.Tag;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.TagService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.TagMapper;
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("tagService")
public class TagServiceImpl extends BlogBaseServiceImpl implements TagService {
    private static final Integer PAGESIZE = 20;

  @Autowired
  private TagMapper tagMapper;
  @Autowired
  private BlogService blogService;

  public List<String> listTagsByPost(String postid){
    return tagMapper.getTagsByPost(postid);
  }

  /**
   * 插入文章标签记录
   * 
   * @param tags
   * @return
   */
  public int insertBatch(List<Tag> tags){
    return tagMapper.insertBatch(tags);
  }

  public int deleteByPostid(String postid){
    return tagMapper.deleteByPostid(postid);
  }

  @Override
  public List<String> getPostsByTag(String name) {
    return tagMapper.getPostsByTag(name);
  }

    @Override
    public PageIterator<String> getPostsByTag(String name, int page, int pageSize) {
        if(page <=0 || pageSize <= 0) {
            page = 1;
            pageSize = PAGESIZE;
        }
        int totalCount = tagMapper.countPostsByTag(name);
        PageIterator<String> pageIterator = PageIterator.createInstance(page, pageSize, totalCount);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("name", name);
        data.put("offset", (page-1)*pageSize);
        data.put("limit", pageSize);
        pageIterator.setData(tagMapper.getPostsByTagLimit(data));
        return pageIterator;
    }

    @Override
  public List<TagVO> getTags(int limit) {
    List<String> tags = tagMapper.getTags(limit);
    List<TagVO> tagVOs = Optional.ofNullable(tags).orElse(Collections.emptyList())
            .stream()
            .map(tag -> {
              TagVO tagVO = new TagVO();
              Tag tag1 = new Tag();
              tag1.setName(tag);
              tagVO.setTag(tag1);
              tagVO.setWeight(blogService.getVisitCountByids(tagMapper.getPostsByTag(tag)));
              return tagVO;
            })
            .collect(Collectors.toList());
    return tagVOs;
  }

    @Override
    public List<String> getTagStrs(int limit) {
        return tagMapper.getTags(limit);
    }

    @Override
    public boolean isExist(String tagName) {
        return CollectionUtils.isNotEmpty(tagMapper.isExist(tagName));
    }

    @Override
  public BaseMapper getMapper(){
    return tagMapper;
  }

}
