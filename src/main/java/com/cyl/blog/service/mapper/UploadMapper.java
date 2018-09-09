package com.cyl.blog.service.mapper;

import com.cyl.blog.backend.vo.Upload;
import com.cyl.blog.backend.vo.UploadVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UploadMapper extends BaseMapper {
  
  List<Upload> listByPostid(String postid);

  /**
   * 更新上传文件记录对应的文章ID
   * 
   * @param postid
   * @param imgpaths
   * @return
   */
  public int updatePostid(@Param("postid") String postid, @Param("imgpaths") List<String> imgpaths);

  /**
   * 将所有postid的记录置空,非删除记录
   * 
   * @param postid
   */
  public void setNullPostid(String postid);

  public int deleteByPostid(String postid);

  int countUpload();

  List<UploadVO> getList(Map<String, Object> map);

}
