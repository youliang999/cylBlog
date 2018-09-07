package com.cyl.blog.service;

import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.vo.CommentVO;

import java.util.Collection;
import java.util.List;

/**
 * Created by youliang.cheng on 2018/8/2.
 */
public interface CommentService extends BlogBaseService {

    PageIterator<CommentVO> listByStatus(int pageIndex, int pageSize, Collection<String> status);

    CommentVO listCountByGroupStatus();

    List<CommentVO> listRecent();

    List<CommentVO> getByPost(String bid, String creator);

    int setStatus(String commentid, String newStatus);

}
