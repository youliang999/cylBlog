package com.cyl.blog.service;

import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.vo.CommentVO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/8/2.
 */
public interface CommentService extends BlogBaseService {

    PageIterator<Map> listByStatus(int pageIndex, int pageSize, Collection<String> status);

    Map listCountByGroupStatus();

    List<CommentVO> listRecent();

    List<CommentVO> getByPost(String bid, String creator);

    int setStatus(String commentid, String newStatus);

}
