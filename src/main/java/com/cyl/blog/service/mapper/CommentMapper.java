package com.cyl.blog.service.mapper;

import com.cyl.blog.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper {

  List<CommentVO> listRecent();

  List<CommentVO> listCountByGroupStatus();

  /**
   * 根据postid获取被批准的评论和指定creator的评论
   * 
   * @param postid
   * @param creator
   * @return
   */
  List<CommentVO> listByPost(@Param("postid") String postid, @Param("creator") String creator);

  /**
   * 更改comment的状态
   * 
   * @param commentid
   */
  int setStatus(@Param("commentid") String commentid, @Param("status") String status);

}
