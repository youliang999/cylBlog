package com.cyl.blog.solr.service.impl;

import com.cyl.blog.entity.solr.BlogIndex;
import com.cyl.blog.entity.solr.BlogQueryBuilder;
import com.cyl.blog.entity.solr.SearchResult;
import com.cyl.blog.solr.SolrClientServiceImpl;
import com.cyl.blog.solr.service.BlogIndexService;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by youliang.cheng on 2018/8/21.
 */
@Service("blogIndexService")
public class BlogIndexServiceImpl implements BlogIndexService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogIndexServiceImpl.class);
    private static final HttpSolrClient solrClient = SolrClientServiceImpl.instance.getSolrClient();

    @Override
    public boolean addIndex(BlogIndex index) {
        try {
            UpdateResponse response = solrClient.addBean(index );
            solrClient.commit();
            if (response != null && response.getStatus() == 0) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean addIndexs(List<BlogIndex> indexList) {
        try {
            UpdateResponse response = solrClient.addBeans(indexList);
            solrClient.commit();
            if (response != null && response.getStatus() == 0) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean deleteIndex(int blogId) {
        try {
            UpdateResponse response = solrClient.deleteById(String.valueOf(blogId));
            solrClient.commit();
            if (response != null && response.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean deleteIndexes(List<Integer> bids) {
        List<String> bidStrs = bids.stream().map(bid -> String.valueOf(bid)).collect(Collectors.toList());
        try {
            UpdateResponse response = solrClient.deleteById(bidStrs);
            solrClient.commit();
            if (response != null && response.getStatus() == 0) {
                return true;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateIndex(BlogIndex index) {
        return addIndex(index);
    }

    @Override
    public SearchResult<BlogIndex> getIndexResult(BlogQueryBuilder builder) {
        try {
            SearchResult<BlogIndex> result = new SearchResult<BlogIndex>();
            QueryResponse response = solrClient.query(builder.build(), SolrRequest.METHOD.POST);
            result.setData(response.getBeans(BlogIndex.class));
            result.setTotalCount((int) response.getResults().getNumFound());
            return result;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<FacetField> getFacetField(BlogQueryBuilder builder) {
        try {
            QueryResponse response = solrClient.query(builder.build(), SolrRequest.METHOD.POST);
            return response.getFacetFields();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }
}
