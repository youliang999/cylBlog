package com.cyl.blog.entity.solr;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/8/21.
 */
public class SearchResult<T> {
    private List<T> data;
    private int totalCount;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
