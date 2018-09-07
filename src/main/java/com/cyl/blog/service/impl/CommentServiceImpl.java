package com.cyl.blog.service.impl;

import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.CommentService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.CommentMapper;
import com.cyl.blog.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by youliang.cheng on 2018/8/2.
 */
@Service("commentService")
public class CommentServiceImpl extends BlogBaseServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageIterator<CommentVO> listByStatus(int pageIndex, int pageSize, Collection<String> status) {
        return null;
    }

    @Override
    public CommentVO listCountByGroupStatus() {
        return null;
    }

    @Override
    public List<CommentVO> listRecent() {
        return null;
    }

    @Override
    public List<CommentVO> getByPost(String bid, String creator) {
        return commentMapper.listByPost(bid, creator);
    }

    @Override
    public int setStatus(String commentid, String newStatus) {
        return commentMapper.setStatus(commentid, newStatus);
    }

    @Override
    public BaseMapper getMapper() {
        return commentMapper;
    }
}
