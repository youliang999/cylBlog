package com.cyl.blog.vo;

import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Comment;
import com.cyl.blog.plugin.TreeItem;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * 评论业务对象
 * 
 * @author
 *
 */
@ToString
public class CommentVO extends Comment implements TreeItem<CommentVO>,Serializable {
  private static final long serialVersionUID = 6593096533331366003L;
  private Blog blog;
  private List<CommentVO> children;
  private String createDate;

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public Blog getBlog(){
    return blog;
  }

  public void setPost(Blog blog){
    this.blog = blog;
  }

  public void setChildren(List<CommentVO> children){
    this.children = children;
  }

  @Override
  public List<CommentVO> getChildren(){
    return children;
  }

  @Override
  public void addChild(CommentVO comment){
    if(children == null)
      setChildren(new ArrayList<CommentVO>());

    getChildren().add(comment);
  }

}
