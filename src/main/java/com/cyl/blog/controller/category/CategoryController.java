package com.cyl.blog.controller.category;

import com.cyl.blog.constants.WebConstants;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Category;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by youliang.cheng on 2018/8/8.
 */
@Controller
@RequestMapping("/category")
public class CategoryController extends BaseController{

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BlogHelper blogHelper;

    @RequestMapping("/{categoryName}/{page}")
    public ModelAndView getBlogsByCategory(@PathVariable("categoryName") String categoryName,
                                           @PathVariable(value = "page") int page) {
        ModelAndView mv = new ModelAndView("index/index");
        Category category = categoryService.loadByName(categoryName);
        if(category != null){
            PageIterator<BlogVo> blogVoPageIterator = blogHelper.getBlogVosByCategory(category, page, 20);
            mv.addObject("pages", blogVoPageIterator.getData());
            mv.addObject("totalPages", blogVoPageIterator.getTotalPages());
            mv.addObject("currentPage", page);
        }
        mv.addObject("domain", getGlobal().getDomain() + "category/" + categoryName);
        mv.addObject("category", categoryName);
        mv.addObject(WebConstants.PRE_TITLE_KEY, categoryName);
        return mv;
    }
}
