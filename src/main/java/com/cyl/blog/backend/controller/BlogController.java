package com.cyl.blog.backend.controller;

import com.cyl.blog.backend.vo.PostFormValidator;
import com.cyl.blog.constants.PostConstants;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.helper.WebContextFactory;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.JsoupUtils;
import com.cyl.blog.util.PostTagHelper;
import com.cyl.blog.util.StringUtils;
import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

/**
 * Created by youliang.cheng on 2018/9/6.
 */
@RequestMapping("/backend/posts")
@RequiresRoles(value = { "admin", "editor" }, logical = Logical.OR)
@Controller(value = "adminBlogController")
public class BlogController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private BlogHelper blogHelper;
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    public ModelAndView index(@PathVariable(value = "page") int page){
        long start = System.currentTimeMillis();
        ModelAndView mv = new ModelAndView("backend/new/list");
        PageIterator<BlogVo> pageModel = blogService.getAllBlogVos(page, 15);
        mv.addObject("blogs", pageModel.getData());
        mv.addObject("currentPage", page);
        mv.addObject("totalPages", pageModel.getTotalPages());
        mv.addObject("totalCount", pageModel.getTotalCount());
        mv.addObject("domain", getGlobal().getDomain() +  "/backend/posts" );
        mv.addObject("categorys", blogHelper.getCategory(20));
        log.info("===>>> get blogs cost:{} ms", System.currentTimeMillis() - start);
        return mv;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Object insert(Blog post, String tags){
        log.info("===>>> blog:{}", new Gson().toJson(post.getTitle()));
        Map<String,Object> form = PostFormValidator.validatePublish(post);
        if(!form.isEmpty()){
            log.info("===>>> form:{}", new Gson().toJson(form));
            form.put("success", false);
            return form;
        }

        //post.setId(blogHelper.getNextBlogVo(post.getId()).getId());
        post.setCreator(WebContextFactory.get().getUser().getId());
        post.setCreateTime(new Date());
        post.setLastUpdate(post.getCreateTime());

    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
//        String content = HtmlUtils.htmlUnescape(post.getContent());
        String content = post.getContent();
        post.setContent(JsoupUtils.filter(content));
        String cleanTxt = JsoupUtils.plainText(content);
        post.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0,
                PostConstants.EXCERPT_LENGTH) : cleanTxt);
        Map<String, Object> data = new HashMap<String, Object>();
        List<Blog> bs = blogService.getBlogByTitle(post.getTitle());
        if( bs != null && CollectionUtils.isNotEmpty(bs)) {
            data.put("success", false);
            data.put("msg", "博客已存在!");
            return new Gson().toJson(data);
        }
        blogHelper.insertBlog(post, PostTagHelper.from(post, tags, post.getCreator()));
        data.put("success", true);
        return new Gson().toJson(data);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(Blog post, String tags){
        Map<String, Object> form = PostFormValidator.validateUpdate(post);
        if(!form.isEmpty()){
            return form.put("success", false);
        }

    /* 由于加入xss的过滤,html内容都被转义了,这里需要unescape */
        String content = HtmlUtils.htmlUnescape(post.getContent());
        post.setContent(JsoupUtils.filter(content));
        String cleanTxt = JsoupUtils.plainText(content);
        post.setExcerpt(cleanTxt.length() > PostConstants.EXCERPT_LENGTH ? cleanTxt.substring(0,
                PostConstants.EXCERPT_LENGTH) : cleanTxt);

        post.setType(PostConstants.TYPE_POST);
        post.setLastUpdate(new Date());
        blogHelper.updateBlog(post, PostTagHelper.from(post, tags, WebContextFactory.get().getUser().getId()));
        log.info("===>>> update succes..");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        return new Gson().toJson(data);
    }

    @ResponseBody
    @RequestMapping(value = "/fast", method = RequestMethod.PUT)
    public Object fast(Blog post, String tags){
        Map<String, Object> form = PostFormValidator.validateFastUpdate(post);
        if(!form.isEmpty()){
            return form.put("success", false);
        }

        Blog old = blogService.loadById(post.getId());
        if(old == null){
            form.put("success", false);
            form.put("msg", "非法请求");
            return form;
        }

        post.setContent(old.getContent());
        post.setExcerpt(old.getExcerpt());

        post.setType(PostConstants.TYPE_POST);
        post.setLastUpdate(new Date());
         blogHelper.updateBlog(post, PostTagHelper.from(post, tags, WebContextFactory.get().getUser().getId()), true);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        return new Gson().toJson(data);
    }

    @ResponseBody
    @RequestMapping(value = "/{postid}", method = RequestMethod.DELETE)
    public Object remove(@PathVariable("postid") String postid){
        blogHelper.removeBlog(postid, PostConstants.TYPE_POST);
        return new HashMap<String, Object>().put("success", true);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public ModelAndView edit(String pid){
        ModelAndView mv = new ModelAndView("backend/new/post/ueedit");
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
