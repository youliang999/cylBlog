package com.cyl.blog.entity.solr;

import com.cyl.blog.constants.BlogConstants;
import com.cyl.blog.entity.Blog;
import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/8/21.
 */
@Data
public class BlogIndex implements Serializable {
    private static final long serialVersionUID = -999579039765634214L;
    static final String FIELD_ID = "id";
    static final String FIELD_TITLE = "title";
    static final String FIELD_EXCERPT = "excerpt";
//    static final String FIELD_CONTENT = "content";
    static final String FIELD_TYPE = "type";
    static final String FIELD_PARENT = "parent";
    static final String FIELD_CATEGORYID = "categoryid";
    static final String FIELD_PSTAUS = "pstatus";
    static final String FIELD_CSTATUS = "cstatus";
    static final String FIELD_CCOUNT = "ccount";
    static final String FIELD_RCOUNT = "rcount";
    static final String FIELD_CREATOR = "creator";
    static final String FIELD_CREATETIME = "createTime";
    static final String FIELD_LASTUPDATE = "lastUpdate";

    @Field(FIELD_ID)
    private int id;

    @Field(FIELD_TITLE)
    private String title;

    @Field(FIELD_EXCERPT)
    private String excerpt;

//    @Field(FIELD_CONTENT)
//    private String content;

    @Field(FIELD_TYPE)
    private String type = BlogConstants.TYPE_POST;

    @Field(FIELD_PARENT)
    private String parent = BlogConstants.DEFAULT_PARENT;

    @Field(FIELD_CATEGORYID)
    private String categoryid;

    @Field(FIELD_PSTAUS)
    private int pstatus = 1;//BlogConstants.POST_PUBLISH;

    @Field(FIELD_CSTATUS)
    private String cstatus = BlogConstants.COMMENT_OPEN;

    @Field(FIELD_CCOUNT)
    private int ccount = 0;

    @Field(FIELD_RCOUNT)
    private int rcount = 0;

    @Field(FIELD_CREATETIME)
    private Date createTime;

    @Field(FIELD_CREATOR)
    private String creator;

    @Field(FIELD_LASTUPDATE)
    private Date lastUpdate;



    public static BlogIndex fromEntity(Blog blog) {
        BlogIndex index = new BlogIndex();
        index.setId(Integer.valueOf(blog.getId()));
        index.setTitle(blog.getTitle());
        index.setExcerpt(blog.getExcerpt());
        index.setType(blog.getType());
        index.setParent(blog.getParent());
        index.setCategoryid(blog.getCategoryid());
        index.setPstatus(blog.getPstatus());
        index.setCstatus(blog.getCstatus());
        index.setCcount(blog.getCcount());
        index.setRcount(blog.getRcount());
        index.setCreateTime(blog.getCreateTime());
        index.setCreator(blog.getCreator());
        index.setLastUpdate(blog.getLastUpdate());
        return index;
    }
}
