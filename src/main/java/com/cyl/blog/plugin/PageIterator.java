package com.cyl.blog.plugin;

import java.io.Serializable;
import java.util.List;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
public class PageIterator<T> implements Serializable {

    private static final long serialVersionUID = 1100034894592704355L;
    private int page;
    private int pageSize;
    private int totalPages;
    private int totalCount;
    private String params;
    private List<T> data;

    public static <T> PageIterator<T> createInstance(int page, int pageSize, int totalCount) {
        PageIterator<T> pageBean = new PageIterator<T>();
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPages((totalCount + pageSize - 1) / pageSize);
        pageBean.setPageSize(pageSize);
        if (page > pageBean.totalPages) {   //totalPages有可能是0
            page = pageBean.getTotalPages();
        }
        if (page <= 0) {    //保证page一定大于0
            pageBean.setPage(1);
        } else {
            pageBean.setPage(page);
        }
        return pageBean;
    }

    public static <T> PageIterator<T> createInstance(int page, int pageSize, List<T> data) {
        PageIterator<T> pageBean = new PageIterator<T>();
        if (data != null && data.size() > 0) {
            pageBean.setTotalCount(data.size());
            pageBean.setTotalPages((data.size() + pageSize - 1) / pageSize);
            pageBean.setData(data);
            pageBean.setPageSize(pageSize);
            if (page <= 0) {
                pageBean.setPage(1);
            } else if (page > pageBean.totalPages) {
                pageBean.setPage(pageBean.getTotalPages());
            } else {
                pageBean.setPage(page);
            }
        } else {
            pageBean.setData(data);
        }

        return pageBean;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getPageList(int pageNum) {
        List<T> pageList;
        if (data == null) {
            return null;
        }
        if (pageNum <= 0) {
            setPage(1);
            if (totalCount > pageSize) {
                pageList = data.subList(0, pageSize);
            } else {
                pageList = data.subList(0, totalCount);
            }
        } else if (pageNum * pageSize <= totalCount) {
            setPage(pageNum);
            pageList = data.subList((pageNum - 1) * pageSize, pageNum * pageSize);
        } else {
            setPage(totalPages);
            pageList = data.subList((totalPages - 1) * pageSize, totalCount);
        }
        return pageList;
    }

}
