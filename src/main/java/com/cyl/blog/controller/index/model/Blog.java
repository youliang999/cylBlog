package com.cyl.blog.controller.index.model;

import lombok.Data;
import com.cyl.blog.constants.BlogConstants;
import java.io.Serializable;
/**
 * Created by youliang.cheng on 2018/7/11.
 */
@Data
public class Blog extends BaseBlog implements Serializable {
    private static final long serialVersionUID = -1644834357614259925L;
    private String title;
    /* 摘录,当type为页面该项为null */
    private String excerpt;
    private String content;
    /* 文章类型（post/page等） */
    private String type = BlogConstants.TYPE_POST;
    /* 父文章ID，主要用于PAGE,只支持两级 */
    private String parent = BlogConstants.DEFAULT_PARENT;
    /* 分类ID,主要用于POST */
    private String categoryid;
    /* 文章状态 */
    private String pstatus = BlogConstants.POST_PUBLISH;
    /* 评论状态 */
    private String cstatus = BlogConstants.COMMENT_OPEN;
    /* 评论数 */
    private int ccount = 0;
    /* 阅读数 */
    private int rcount = 0;
}
