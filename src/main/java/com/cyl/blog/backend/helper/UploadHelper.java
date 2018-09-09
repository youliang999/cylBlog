package com.cyl.blog.backend.helper;

import com.cyl.blog.backend.vo.Upload;
import com.cyl.blog.backend.vo.UploadVO;
import com.cyl.blog.constants.WebConstants;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.User;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.UploadService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.util.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Controller
public class UploadHelper {

    @Autowired
    private UploadService uploadService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    public PageIterator<UploadVO> list(int pageIndex, int pageSize){
        PageIterator<UploadVO> result = uploadService.list(pageIndex, pageSize);
        if(result == null || CollectionUtils.isEmpty(result.getData())) {
            return PageIterator.createInstance(pageIndex, pageSize, Collections.emptyList());
        }
        for(UploadVO upload : result.getData()){
            if(!StringUtils.isBlank(upload.getPostid())){
                Blog post = blogService.loadById(upload.getPostid());
                upload.setPost(post);
            }
            User user = userService.loadById(upload.getCreator());
            upload.setUser(user);
            upload.setFileExt(fileExt(upload.getName()));
        }

        return result;
    }


    public static String fileExt(String filename){
        return FileUtils.getFileExt(filename).toUpperCase();
    }

    /**
     * 添加上传记录并存储文件
     *
     * @param resource
     * @param create
     * @param fileName
     * @param userid
     * @return 当前上传对象
     */
    public Upload insertUpload(Resource resource, Date create, String fileName, String userid){
        Upload upload = null;
        OutputStream out = null;
        try{
            String yearMonth = DateUtils.formatDate("yyyy/MM", create);
            File parent = new File(WebConstants.APPLICATION_PATH + "/post/uploads", yearMonth);
            if(!parent.exists())
                parent.mkdirs();

            File savePath = FileUtils.determineFile(parent, fileName);
            IOUtils.copy(resource.getInputStream(), out = new FileOutputStream(savePath));

            upload = new Upload();
            upload.setId(IdGenerator.uuid19());
            upload.setCreateTime(create);
            upload.setCreator(userid);
            upload.setName(fileName);
            upload.setPath("/blog/uploads/" + yearMonth + "/" + savePath.getName());

            uploadService.insert(upload);
        }catch(Exception e){
            e.printStackTrace();
            upload = null;
        }finally{
            IOUtils.closeQuietly(out);
        }

        return upload;
    }

    /**
     * 删除记录，同时删除文件
     *
     * @param uploadid
     */
    public void removeUpload(String uploadid){
        Upload upload = uploadService.loadById(uploadid);
        uploadService.deleteById(uploadid);
        File file = new File(WebConstants.APPLICATION_PATH, upload.getPath());
        if(file.exists())
            file.delete();

    /* 注:当前目录为空同时删除父目录,如果父目录包含子文件/夹，会删除失败(File.delete中决定) */
        File parent = file.getParentFile();
        for(int i = 0; i < 2 && parent.list().length == 0; i++){
            parent.delete();
            parent = parent.getParentFile();
        }
    }
}
