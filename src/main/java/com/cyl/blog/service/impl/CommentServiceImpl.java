package com.cyl.blog.service.impl;

import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.CommentService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.CommentMapper;
import com.cyl.blog.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/8/2.
 */
@Service("commentService")
public class CommentServiceImpl extends BlogBaseServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public PageIterator<Map> listByStatus(int pageIndex, int pageSize, Collection<String> status) {
        Map<String, Object> data = new HashMap<>();
        data.put("status", status);
        data.put("offset", (pageIndex - 1) * pageSize);
        data.put("limit", pageSize);
        int count = commentMapper.countByStatus(data);
        PageIterator<Map> commentVOPageIterator = PageIterator.createInstance(pageIndex, pageSize, count);
        commentVOPageIterator.setData(commentMapper.listByStatus(data));
        return commentVOPageIterator;
    }

    @Override
    public Map listCountByGroupStatus() {
        List<Map<String, Object>> list = commentMapper.listCountByGroupStatus();
        Map<String, Object> mc = new HashMap<>();
        for(Map<String, Object> temp : list){
            mc.put(String.valueOf(temp.get("status")), temp.get("count"));
        }

        return mc;
    }

    @Override
    public List<CommentVO> listRecent() {
       return commentMapper.listRecent();
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
