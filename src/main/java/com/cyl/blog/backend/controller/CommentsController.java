package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.helper.CommentHelper;
import com.cyl.blog.constants.CommentConstants;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.CommentService;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Controller("adminCommentController")
@RequestMapping("/backend/comments")
@RequiresRoles("admin")
public class CommentsController extends BaseController{
    private static final Logger log  = LoggerFactory.getLogger(CommentsController.class);
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentHelper commentHelper;

    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "page") int page,
                        @RequestParam(value = "type", defaultValue = "all") String type, Model model){
        ModelAndView mv = new ModelAndView("backend/new/comment-list");
        Collection<String> status = "all".equals(type) ? Arrays.asList(CommentConstants.TYPE_APPROVE,
                CommentConstants.TYPE_WAIT) : Arrays.asList(type);
        log.info("===>>> status:{}", new Gson().toJson(status));
        PageIterator<Map> pageModel = commentService.listByStatus(page, 15, status);
        mv.addObject("comments", pageModel.getData());
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", pageModel.getTotalPages());
        mv.addObject("totalCount", pageModel.getTotalCount());
        mv.addObject("domain", getGlobal().getDomain() +  "/backend/comments" );
        model.addAttribute("type", type);
        model.addAttribute("stat", commentService.listCountByGroupStatus());
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/{commentid}", method = RequestMethod.DELETE)
    public Object remove(@PathVariable("commentid") String commentid){
        commentHelper.deleteComment(commentid);
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return data;
    }

    /**
     * 修改评论状态
     *
     * @param commentid
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/{commentid}", method = RequestMethod.PUT)
    public Object approve(@PathVariable("commentid") String commentid, String status){
        commentHelper.setStatus(commentid, status);
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);
        return data;
    }

}
