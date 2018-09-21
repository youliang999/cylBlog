package com.cyl.blog.backend.controller;

import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

/**
 * Created by youliang.cheng on 2018/9/12.
 */
@Controller
@RequestMapping("/backend/blog")
public class UeditorController {

    @Autowired
    private BlogHelper blogHelper;

    @RequestMapping("/ueditor")
    public ModelAndView markdown(String pid) {
        ModelAndView mv = new ModelAndView("/backend/new/post/ueedit");
        if(!StringUtils.isBlank(pid)){
            BlogVo blogVo = blogHelper.getBlogVo(pid);
            mv.addObject("post", blogVo);
            mv.addObject("tags", join(blogVo.getTags(), ","));
        }
        mv.addObject("categorys", blogHelper.getCategory(20));
        return mv;
    }

    public static String join(Collection<String> collect, String delimiter){
        return CollectionUtils.isEmpty(collect) ? null : StringUtils.join(collect, delimiter);
    }
}
