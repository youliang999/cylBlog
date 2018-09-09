package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.vo.Link;
import com.cyl.blog.backend.vo.LinkFormValidator;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.helper.WebContextFactory;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.LinkService;
import com.cyl.blog.util.IdGenerator;
import com.cyl.blog.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Controller
@RequestMapping("/backend/links")
@RequiresRoles("admin")
public class LinkController extends BaseController{
    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "page") int page){
        ModelAndView mv = new ModelAndView("backend/new/link-list");
        PageIterator<Link> pageIterator = linkService.list(page, 15);
        mv.addObject("links", pageIterator.getData());
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", pageIterator.getTotalPages());
        mv.addObject("totalCount", pageIterator.getTotalCount());
        mv.addObject("domain", getGlobal().getDomain() +  "/backend/links" );
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView insert(Link link){
        ModelAndView mv = new ModelAndView("backend/new/link-edit");
        Map<String, Object> form = LinkFormValidator.validateInsert(link);
        if(!form.isEmpty()){
            mv.addObject("form", form);
            return mv;
        }

        link.setCreateTime(new Date());
        link.setLastUpdate(link.getCreateTime());
        link.setCreator(WebContextFactory.get().getUser().getNickName());
        link.setId(IdGenerator.uuid19());
        link.setVisible(true);
        linkService.insert(link);
        mv.setViewName("redirect:/backend/links");
        return mv;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView update(Link link){
        ModelAndView mv = new ModelAndView("backend/new/link-edit");
        Map<String, Object> form = LinkFormValidator.validateUpdate(link);
        if(!form.isEmpty()){
            mv.addObject("link", link);
            mv.addObject("msg", form.get("msg"));
            return mv;
        }

        link.setLastUpdate(new Date());
        linkService.update(link);
        mv.setViewName("redirect:/backend/links");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/{linkid}", method = RequestMethod.DELETE)
    public Object remove(@PathVariable("linkid") String linkid){
        linkService.deleteById(linkid);
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return data;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String lid){
        ModelAndView mv = new ModelAndView("backend/new/link-edit");
        if(!StringUtils.isBlank(lid))
            mv.addObject("link", linkService.loadById(lid));

        return mv;
    }
}
