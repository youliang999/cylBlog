package com.cyl.blog.backend.helper;

import com.cyl.blog.constants.CommentConstants;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Comment;
import com.cyl.blog.plugin.TreeUtils;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CommentService;
import com.cyl.blog.vo.CommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Component
public class CommentHelper {
    private static final Logger log = LoggerFactory.getLogger(CommentHelper.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    public List<CommentVO> getAsTree(String postid, String creator){
        List<CommentVO> list = commentService.getByPost(postid, creator);
        TreeUtils.rebuildTree(list);
        log.info("===>>> list:{}", list);
        return list;
    }

    /**
     * 最近留言
     *
     * @return
     */
    public List<CommentVO> listRecent(){
        List<CommentVO> list = commentService.listRecent();
        for(CommentVO cvo : list){
            Blog post = blogService.loadById(cvo.getPostid());
            cvo.setPost(post);
        }

        return list;
    }

    /**
     * 更改评论状态，同时更改该评论对应的post的评论数
     *
     * @param commentid
     * @param newStatus
     */
    @Transactional
    public int setStatus(String commentid, String newStatus){
        Comment comment = commentService.loadById(commentid);
        int result = -1;
        if(comment != null){
            commentService.setStatus(commentid, newStatus);
            int count = CommentConstants.TYPE_APPROVE.equals(newStatus) ? 1 : -1;
            result = blogService.addCcount(commentid, count);
        }

        return result;
    }

    /**
     * 删除评论，同时更改对应文章的评论数
     *
     * @param commentid
     */
    @Transactional
    public int deleteComment(String commentid){
        Comment comment = commentService.loadById(commentid);
        int result = -1;
        if(comment != null){
            blogService.addCcount(commentid, -1);
            result = commentService.deleteById(commentid);
        }

        return result;
    }

}
