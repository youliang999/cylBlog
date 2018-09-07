package com.cyl.blog.solr.service;

import com.cyl.blog.entity.solr.BlogIndex;
import com.cyl.blog.entity.solr.BlogQueryBuilder;
import com.cyl.blog.entity.solr.SearchResult;
import org.apache.solr.client.solrj.response.FacetField;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/8/21.
 */
public interface BlogIndexService {

    boolean addIndex(BlogIndex index);

    boolean addIndexs(List<BlogIndex> indexList);

    boolean deleteIndex(int blogId);

    boolean deleteIndexes(List<Integer> bids);

    boolean updateIndex(BlogIndex index);

    SearchResult<BlogIndex> getIndexResult(BlogQueryBuilder builder);

    List<FacetField> getFacetField(BlogQueryBuilder builder);
}
