package com.cyl.blog.service.handler;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.solr.BlogIndex;
import com.cyl.blog.entity.solr.BlogQueryBuilder;
import com.cyl.blog.entity.solr.SearchResult;
import com.cyl.blog.solr.service.BlogIndexService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by youliang.cheng on 2018/9/12.
 */
@Component("indexHandler")
public class IndexHandler {
    private static final Logger log = LoggerFactory.getLogger(IndexHandler.class);
    @Autowired
    private BlogIndexService blogIndexService;

    public boolean createHandle(Blog t) {
        log.info("handle a create msg:{} ", t.getTitle());
        BlogIndex index = BlogIndex.fromEntity( t);
        log.info("===>>> index title:{}", index.getTitle());
        blogIndexService.addIndex(index);
        log.info("===>>> add a index:{}", index.getId());
        return true;
    }

    public boolean updateHandle(Blog t) {
        log.info("handle a update msg:{} ", t.getTitle());
        BlogIndex index = BlogIndex.fromEntity( t);
        BlogQueryBuilder queryBuilder = new BlogQueryBuilder();
        queryBuilder.withBids(Arrays.asList(Integer.valueOf(t.getId())));
        SearchResult<BlogIndex> blogIndexSearchResult =  blogIndexService.getIndexResult(queryBuilder);
        blogIndexService.updateIndex(index);
        log.info("===>>> update a index  id:{}", index.getId());
        return true;
    }

    public  boolean deleteHandle(String id) {
        log.info("handle a delete msg:{} ", id);
        BlogQueryBuilder queryBuilder = new BlogQueryBuilder();
        queryBuilder.withBids(Arrays.asList(Integer.valueOf(id)));
        SearchResult<BlogIndex> blogIndexSearchResult =  blogIndexService.getIndexResult(queryBuilder);
        blogIndexService.deleteIndex(Integer.valueOf(id));
        log.info("===>>> delete a index  before:{} id:{}", new Gson().toJson(blogIndexSearchResult.getData()), id);
        return true;
    }
}
