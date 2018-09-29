package com.cyl.blog.backend.vo;


import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * 附件业务对象
 * 
 * @author zhou
 *
 */
@Data
public class UploadVO extends Upload implements Serializable{
  private Blog post;
  private User user;
  private String fileExt;
  private String suffix;
}
