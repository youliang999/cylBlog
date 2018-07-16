package com.cyl.blog.controller.index;

import com.cyl.blog.controller.index.model.Blog;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.BlogService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
@Controller
public class IndexController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private BlogService blogService;

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        PageIterator<Blog> blogPageIterator = blogService.getBlogs(1, 20);
        log.info("===>>> data:{}", new Gson().toJson(blogPageIterator));
        mv.addObject("blogs", new Gson().toJson(blogPageIterator));
        return mv;
    }


}
