package com.cyl.blog.service.impl;

import com.cyl.blog.backend.vo.Upload;
import com.cyl.blog.backend.vo.UploadVO;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.UploadService;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.UploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */

@Service("uploadService")
public class UploadServiceImpl extends BlogBaseServiceImpl implements UploadService {
    @Autowired
    private UploadMapper uploadMapper;

    public PageIterator<UploadVO> list(int pageIndex, int pageSize){
        int count = uploadMapper.countUpload();
        PageIterator<UploadVO> pageModel = PageIterator.createInstance(pageIndex, pageSize, count);
        Map<String, Object> data = new HashMap<>();
        data.put("offset", (pageIndex - 1) * pageSize);
        data.put("limit",  pageSize);
        pageModel.setData(uploadMapper.getList(data));
        return pageModel;
    }

    public List<Upload> listByPostid(String postid){
        return uploadMapper.listByPostid(postid);
    }

    public void updatePostid(String postid, List<String> imgpaths){
        uploadMapper.updatePostid(postid, imgpaths);
    }

    /**
     * 将所有postid的记录置空,非删除记录
     *
     * @param postid
     */
    public void setNullPostid(String postid){
        uploadMapper.setNullPostid(postid);
    }

    public void deleteByPostid(String postid){
        uploadMapper.deleteByPostid(postid);
    }

    @Override
    public BaseMapper getMapper(){
        return uploadMapper;
    }

}
