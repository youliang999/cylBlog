package com.cyl.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by youliang.cheng on 2018/10/12.
 */
@RequestMapping("/search")
public class SearchController extends BaseController {

    @RequestMapping
    public ModelAndView searchView() {
        return new ModelAndView("");
    }

}
