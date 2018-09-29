package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.editor.Ueditor;
import com.cyl.blog.backend.helper.ServletRequestReader;
import com.cyl.blog.backend.helper.UploadHelper;
import com.cyl.blog.backend.vo.Upload;
import com.cyl.blog.backend.vo.UploadVO;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.helper.WebContextFactory;
import com.cyl.blog.plugin.PageIterator;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Controller
@RequestMapping("/backend/uploads")
@RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
public class UploadController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private Ueditor ueditor;
    @Autowired
    private UploadHelper uploadHelper;

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "page") int page){
        ModelAndView mv = new ModelAndView("backend/new/upload-list");
        PageIterator<UploadVO> pages = uploadHelper.list(page, 15);
//        log.info("===>>> pages:{}", new Gson().toJson(pages.getData()));
        mv.addObject("uploads", pages.getData());
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", pages.getTotalPages());
        mv.addObject("totalCount", pages.getTotalCount());
        mv.addObject("domain", getGlobal().getDomain() +  "/backend/uploads" );
        return mv;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object insert(MultipartFile file){
        log.info("===>>> upload fileName:{}", file.getName());
        Upload upload = null;
        try(InputStream in = file.getInputStream()){
            upload = uploadHelper.insertUpload(new InputStreamResource(in), new Date(), file.getOriginalFilename(),
                    WebContextFactory.get().getUser().getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("success", upload != null);
        return data;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(){
        return "backend/new/upload-edit";
    }

    @ResponseBody
    @RequestMapping(value = "/{uploadid}", method = RequestMethod.DELETE)
    public Object remove(@PathVariable("uploadid") String uploadid){
        uploadHelper.removeUpload(uploadid);
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/ueditor")
    public Object ueditor(ServletRequestReader reader){
        return ueditor.server(reader);
    }


}
