package com.cyl.blog.controller.index;

import com.cyl.blog.constants.WebConstants;
import com.cyl.blog.controller.BaseController;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Category;
import com.cyl.blog.entity.solr.BlogIndex;
import com.cyl.blog.entity.solr.BlogQueryBuilder;
import com.cyl.blog.entity.solr.SearchResult;
import com.cyl.blog.helper.ShiroFunctions;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.solr.service.BlogIndexService;
import com.cyl.blog.util.CookieUtil;
import com.cyl.blog.util.TagVO;
import org.apache.solr.client.solrj.response.FacetField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
@Controller
public class IndexController extends BaseController{
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private BlogHelper blogHelper;
    @Autowired
    private IndexDataLoader dataLoader;
    @Autowired
    private BlogIndexService blogIndexService;

    @RequestMapping("/")
    public ModelAndView index1(){
        ModelAndView mv = new ModelAndView("index/index");
        getIndexPage(1, mv);
        return mv;
    }

    @RequestMapping(value = {"/{page}"})
    public ModelAndView index(@PathVariable(value = "page") int page) {
        long st = System.currentTimeMillis();
        ModelAndView mv = new ModelAndView("index/index");
        getIndexPage(page, mv);
        log.info("===>>> index page cost:{} ms", System.currentTimeMillis() - st);
        return mv;
    }

    private void getIndexPage(int page, ModelAndView mv) {
        PageIterator<BlogVo> blogVoPageIterator;
        if(page > 1) {
            long start = System.currentTimeMillis();
            blogVoPageIterator = blogHelper.listPost(page, 20);
            log.info("===>>> cost:{} ms", System.currentTimeMillis() - start);
        } else {
            blogVoPageIterator = (PageIterator<BlogVo>)dataLoader.get(IndexDataLoader.INDEX_PAGE_DATA);
            mv.addObject("totalPages", blogHelper.getTotalCount()/20 -1);
        }
        if (blogVoPageIterator == null || blogVoPageIterator.getData() == null) {
            mv.addObject("pages", Collections.EMPTY_LIST);
            mv.addObject("totalPages", blogVoPageIterator.getTotalPages());
        } else {
            log.info("===>>> data:{}", blogVoPageIterator.getData());
            mv.addObject("domain", getGlobal().getDomain());
            mv.addObject("pages", blogVoPageIterator.getData());
            mv.addObject("currentPage", page);
            mv.addObject("recentBlogs", (List<Blog>) dataLoader.get(IndexDataLoader.RECENT_BLOG));
            mv.addObject("tagVOs", (List<TagVO>) dataLoader.get(IndexDataLoader.TAG));
            mv.addObject("categorys", (List<Category>) dataLoader.get(IndexDataLoader.CATEGORY));
        }
    }
    @RequestMapping("/blog/{blogid}")
    public ModelAndView getBlog(@PathVariable("blogid") String bid, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("blog");
        BlogVo blogVo = blogHelper.getBlogVo(bid);
        shiroFunction(mv);
        if(blogVo != null) {
            mv.addObject(WebConstants.PRE_TITLE_KEY, blogVo.getTitle());
            mv.addObject("blog", blogVo);
            mv.addObject("commentVos",
                    blogHelper.getCommentsAsTree(bid, new CookieUtil(request, null).getCookie("comment_author")));
            /* 上一篇，下一篇 */
            mv.addObject("next", blogHelper.getNextBlogVo(bid));
            mv.addObject("prev", blogHelper.getPrevBlogVo(bid));
            mv.addObject("comment_author", new CookieUtil(request, null).getCookie("comment_author"));
        }
        mv.addObject("recentBlogs", (List<Blog>)dataLoader.get(IndexDataLoader.RECENT_BLOG));
        mv.addObject("tagVOs", (List<TagVO>)dataLoader.get(IndexDataLoader.TAG));
        mv.addObject("categorys", ( List<Category>) dataLoader.get(IndexDataLoader.CATEGORY));
        return mv;
    }
    @RequestMapping("/search/{word}")
    public ModelAndView searchIndex(@PathVariable(value="word") String word) {
        int page = 1;
        ModelAndView mv = new ModelAndView("index/index");
        setIndexPageData(word, page, mv);
        mv.addObject("domain", getGlobal().getDomain() +"/search/" + word);
        mv.addObject("search", word);
        mv.addObject(WebConstants.PRE_TITLE_KEY, word);
        return mv;
    }

    @RequestMapping(value = {"/search/{word}/{page}"})
    public ModelAndView search(@PathVariable(value="word") String word,
                               @PathVariable(value = "page") Integer page) {
        if(page == null) {
            page = 1;
        }
        ModelAndView mv = new ModelAndView("index/index");
        setIndexPageData(word, page, mv);
        mv.addObject("domain", getGlobal().getDomain() +"/search/" + word);
        mv.addObject("search", word);
        mv.addObject(WebConstants.PRE_TITLE_KEY, word);
        return mv;
    }

    private void setIndexPageData(String word, int page, ModelAndView mv) {
        BlogQueryBuilder builder = new BlogQueryBuilder();
        builder.withTitle(word);
        builder.withStart((page-1)*20);
        builder.withRows(20);
        SearchResult<BlogIndex> indexResult = blogIndexService.getIndexResult(builder);
        builder.withFacets(new String[]{"createTime"});
        List<FacetField> facetFields = blogIndexService.getFacetField(builder);
        log.info("===>>> facetFields:{}", facetFields);
        if (indexResult != null) {
            int totalCount = indexResult.getTotalCount();
            int start = (page-1)*20;
            int rows = Math.max(20, 1);
            int currentPage = Math.max(1, start / rows + 1);
            int totalPages = (totalCount + 20 - 1) / 20;
            List<String> bids = Optional.ofNullable(indexResult.getData())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(blogIndex -> String.valueOf(blogIndex.getId())).collect(Collectors.toList());
            log.info("===>>> bids：{}", bids);
            List<BlogVo> blogVos = blogHelper.getBlogVOs(bids);
            mv.addObject("pages", blogVos);
            mv.addObject("totalCount", totalCount);
            mv.addObject("totalPages", totalPages);
            mv.addObject("currentPage", currentPage);
        }
    }


    private void shiroFunction(ModelAndView mv) {
        Map<String, Object> sf = new HashMap<>();
        sf.put("isUser", ShiroFunctions.isUser());
        mv.addObject("sf", sf);
    }
}
