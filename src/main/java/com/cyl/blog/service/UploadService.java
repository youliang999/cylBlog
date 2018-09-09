package com.cyl.blog.service;


import com.cyl.blog.backend.vo.Upload;
import com.cyl.blog.backend.vo.UploadVO;
import com.cyl.blog.plugin.PageIterator;

import java.util.List;

public interface UploadService extends BlogBaseService{

    PageIterator<UploadVO> list(int pageIndex, int pageSize);

    List<Upload> listByPostid(String postid);

    void updatePostid(String postid, List<String> imgpaths);

    void setNullPostid(String postid);

    void deleteByPostid(String postid);
}
