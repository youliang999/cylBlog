package com.cyl.blog.controller.helper;

import com.cyl.blog.constants.PostConstants;
import com.cyl.blog.constants.WebConstants;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Category;
import com.cyl.blog.entity.Tag;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.plugin.TreeUtils;
import com.cyl.blog.service.*;
import com.cyl.blog.service.jedis.JedisOperationService;
import com.cyl.blog.service.jedis.SingleLoader;
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.JsoupUtils;
import com.cyl.blog.util.TagVO;
import com.cyl.blog.vo.CommentVO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/7/31.
 */
@Component
public class BlogHelper {
    private static final long CACHE_DURATION = 60;
    private static final TimeUnit CACHE_TIME_UNIT = TimeUnit.MINUTES;
    private static final Logger log = LoggerFactory.getLogger(BlogHelper.class);
    private static final SimpleDateFormat datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;

    @Autowired
    @Qualifier("baseCache")
    private JedisOperationService<String, Map<String, Object>> baseCache;


    public PageIterator<BlogVo> listPost(int page, int pageSize) {
        return blogService.getBlogVos(page, pageSize);
    }

    public int getTotalCount() {
        return blogService.countBlog();
    }

    public BlogVo getBlogVo(String bid) {
        return blogService.getBlogVoById(bid);
    }

    public List<BlogVo> getBlogVOs(List<String> bids) {
        return blogService.getBlogVoByIds(bids);
    }

    public List<CommentVO> getCommentsAsTree(String bid, String creator) {
        List<CommentVO> commentVOS = commentService.getByPost(bid, creator);
        TreeUtils.rebuildTree(commentVOS);
        log.info("===>>> commentVos:{}", new Gson().toJson(commentVOS));
        return commentVOS;
    }

    public BlogVo getNextBlogVo(String bid) {
        return blogService.getNextBlogVo(bid);
    }

    public BlogVo getPrevBlogVo(String bid) {
        return blogService.getPrevBlogVo(bid);
    }

    public List<Blog> getRecentBlog(int limit) { return blogService.getRecentBlogs(limit);}

    public List<TagVO> getTags(int limit) { return
            tagService.getTags(limit);
    }

    public List<Category> getCategory(int limit) {
        return categoryService.getCategorys(limit);
    }

    public PageIterator<BlogVo> getBlogVosByTagName(String tagName, int page, int pageSize) {
        return blogService.getBlogVosByTag(tagName, page, pageSize);
    }

    public PageIterator<BlogVo> getBlogVosByCategory(Category category, int page, int pageSize) {
        return blogService.getBlogVosByCategory(category, page, pageSize);
    }

    public void getBaseInfo(ModelAndView mv) {
        /* 基本站点统计信息 */

       Map<String, Object> data =  baseCache.getOrLoadAndSet("baseinfo", new SingleLoader<String, Map<String, Object>>() {
            @Override
            public Map<String, Object> load(String s) {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("userCount", userService.count());
                data.put("postCount", getTotalCount());
                data.put("commentCount", commentService.count());
                data.put("uploadCount", uploadService.count());
                data.put("posts", getRecentBlog(10));
                data.put("comments", listRecent());
                return data;
            }
        }, CACHE_DURATION, CACHE_TIME_UNIT);
        mv.addObject("userCount", data.get("userCount"));
        mv.addObject("postCount", data.get("postCount"));
        mv.addObject("commentCount", data.get("commentCount"));
        mv.addObject("uploadCount", data.get("uploadCount"));
        mv.addObject("posts", data.get("posts"));
        mv.addObject("comments", data.get("comments"));
    }

    /**
     * 最近留言
     *
     * @return
     */
    public List<CommentVO> listRecent(){
        List<CommentVO> list = commentService.listRecent();
        for(CommentVO cvo : list){
            Blog post = blogService.loadById(cvo.getPostid());
            cvo.setCreateDate(datefo.format(cvo.getCreateTime()));
            cvo.setPost(post);
        }

        return list;
    }


    /**
     * 插入文章，同时更新上传文件的postid
     *
     * @param post
     * @param tags
     */
    @Transactional
    public void insertBlog(Blog post, List<Tag> tags){
        post.setBlog_type("java");
        blogService.insertBlog(post);
    /* 查找当前html中所有图片链接 */
        List<String> imgs = extractImagepath(JsoupUtils.getImagesOrLinks(post.getContent()));
        if(!CollectionUtils.isEmpty(imgs)){
            //uploadService.updatePostid(post.getId(), imgs);
        }

        if(PostConstants.TYPE_POST.equals(post.getType()) && !CollectionUtils.isEmpty(tags)){
            tagService.insertBatch(tags);
        }
    }
    /**
     * 去掉图片中src地址的http域名前缀
     *
     * @param imgs
     * @return
     */
    private static List<String> extractImagepath(List<String> imgs){
        List<String> imgpaths = new ArrayList<>(imgs.size());
        String domain = WebConstants.getDomain();
        for(String imgUrl : imgs){
            if(imgUrl.startsWith(domain)){
                imgpaths.add(imgUrl.substring(domain.length()));
            }
        }

        return imgpaths;
    }


    /**
     * 更新文章,先重置以前文件对应的附件的postid,再更新文章对应的postid
     *
     * @param post
     * @param tags
     * @return 更新文章影响记录数
     */
    @Transactional
    public boolean updateBlog(Blog post, List<Tag> tags){
        return updateBlog(post, tags, true);
    }

    @Transactional
    public boolean updateBlog(Blog post, List<Tag> tags, boolean fast){
        if(!fast){
           // uploadService.setNullPostid(post.getId());
            List<String> imgs = extractImagepath(JsoupUtils.getImagesOrLinks(post.getContent()));
            if(!CollectionUtils.isEmpty(imgs)){
                //uploadService.updatePostid(post.getId(), imgs);
            }
        }
        Blog blog = blogService.getBlogById(post.getId());
        Blog nBlog = blog.copy();
        post.doUpdate(nBlog);

        blogService.updateBlog(nBlog);

        if(PostConstants.TYPE_POST.equals(post.getType()) && !CollectionUtils.isEmpty(tags)){
            tagService.deleteByPostid(post.getId());
            tagService.insertBatch(tags);
        }

        return true;
    }

    /**
     * 删除文章,同时删除文章对应的上传记录,及其文件
     *
     * @param postid
     * @param postType
     *          post类型(文章or页面),注：此参数为给aop使用
     */
    @Transactional
    public void removeBlog(String postid, String postType){
        //List<Upload> list = uploadService.listByPostid(postid);
       // uploadService.deleteByPostid(postid);
        blogService.delById(postid);

//        for(Upload upload : list){
//            File file = new File(WebConstants.APPLICATION_PATH, upload.getPath());
//            file.delete();
//        }
    }


}
