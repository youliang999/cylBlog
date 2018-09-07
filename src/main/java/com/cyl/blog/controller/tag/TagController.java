package com.cyl.blog.controller.tag;

import com.cyl.blog.constants.WebConstants;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.plugin.PageIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by youliang.cheng on 2018/8/8.
 */
@Controller
@RequestMapping("/tag")
public class TagController extends BaseController{

    @Autowired
    private BlogHelper blogHelper;

    @RequestMapping("/{tagName}/{page}")
    public ModelAndView getBlogsByTags(@PathVariable("tagName") String tagName,
                                       @PathVariable(value = "page") int page) {
        ModelAndView mv = new ModelAndView("index/index");
        PageIterator<BlogVo> blogPageIterator = blogHelper.getBlogVosByTagName(tagName, page, 10);
        mv.addObject("pages", blogPageIterator.getData());
        mv.addObject("domain", getGlobal().getDomain() +"/tag/" + tagName);
        mv.addObject("tag", tagName);
        mv.addObject("totalPages", blogPageIterator.getTotalPages());
        mv.addObject("currentPage", page);
        mv.addObject(WebConstants.PRE_TITLE_KEY, tagName);
        return mv;
    }
}
