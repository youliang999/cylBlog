package com.cyl.blog.entity;

import com.cyl.blog.constants.BlogConstants;
import com.cyl.blog.controller.index.model.BaseBlog;
import com.cyl.blog.util.StringUtils;
import lombok.Data;

import java.io.*;

/**
 * Created by youliang.cheng on 2018/9/29.
 */
@Data
public class BlogV1 extends BaseBlog implements Serializable{
    private static final long serialVersionUID = 6904426404190307798L;

    private String title;
    /* 摘录,当type为页面该项为null */

    /* 文章类型（post/page等） */
    private String type = BlogConstants.TYPE_POST;
    /* 父文章ID，主要用于PAGE,只支持两级 */
    private String parent = BlogConstants.DEFAULT_PARENT;
    /* 分类ID,主要用于POST */
    private String categoryid;
    /* 文章状态 */
    private int pstatus = 1;//BlogConstants.POST_PUBLISH;
    /* 评论状态 */
    private String cstatus = BlogConstants.COMMENT_OPEN;
    /* 评论数 */
    private int ccount = 0;
    /* 阅读数 */
    private int rcount = 0;

    private String blog_type;

    private String createDate;

    public Blog copy() {
        ByteArrayOutputStream byteOut = null;
        ObjectOutputStream objOut = null;
        ByteArrayInputStream byteIn = null;
        ObjectInputStream objIn = null;
        try {
            byteOut = new ByteArrayOutputStream();
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(this);
            byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            objIn = new ObjectInputStream(byteIn);
            return (Blog) objIn.readObject();
        } catch (IOException e) {
            throw new RuntimeException("Clone Object failed in IO.", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found.", e);
        } finally {
            try {
                byteIn = null;
                byteOut = null;
                if (objOut != null) objOut.close();
                if (objIn != null) objIn.close();
            } catch (IOException e) {
            }
        }
    }

    public void doUpdate(Blog oBlog) {
        if(!StringUtils.isBlank(title)) {
            oBlog.setTitle(title);
        }
    }
}
