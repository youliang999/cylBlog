package com.cyl.blog.service.impl;

import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.*;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.service.TagService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.service.handler.IndexHandler;
import com.cyl.blog.service.mapper.BaseMapper;
import com.cyl.blog.service.mapper.BlogContentMapper;
import com.cyl.blog.service.mapper.BlogMapper;
import com.cyl.blog.service.mapper.BlogV1Mapper;
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.StringUtils;
import com.dajie.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by youliang.cheng on 2018/7/11.
 */
@Service("blogService")
public class BlogServiceImpl extends BlogBaseServiceImpl implements BlogService {
    private static final Logger log = LoggerFactory.getLogger(BlogServiceImpl.class);
    private static final Integer PAGESIZE = 20;
    ///private static final ExecutorService executorService = Executors.newFixedThreadPool(50);
    private static final SimpleDateFormat datefo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;
    @Autowired
    private IndexHandler indexHandler;
    @Autowired
    private BlogV1Mapper blogV1Mapper;
    @Autowired
    private BlogContentMapper blogContentMapper;

    @Override
    public boolean insertBlog(Blog blog) {
        if(blog == null) {
            return false;
        }
        blogMapper.insertBlog(blog);
        log.info("===>>> insert id :{} ", blog.getId());
        String id = blog.getId();
        if(Integer.valueOf(id) > 0) {
            Blog n = blogMapper.getBlogById(String.valueOf(id));
            indexHandler.createHandle(n);
        }
        return Integer.valueOf(id) > 0;
    }

    @Override
    public boolean updateBlog(Blog blog) {
        if(blog == null) {
            return false;
        }
        int id = blogMapper.update(blog);
        if(id > 0) {
            Blog n = blogMapper.getBlogById(String.valueOf(id));
            indexHandler.updateHandle(n);
        }
        return id > 0;
    }

    @Override
    public boolean delById(String id) {
        if(StringUtil.isEmpty(id)) {
            return false;
        }
        blogMapper.deleteById(id);
        indexHandler.deleteHandle(id);
        return true;
    }

    @Override
    public List<Blog> getBlogByCondition(int start, int limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", start);
        map.put("limit", limit);
        return  blogMapper.getBlogsByCondition(map);
    }

    public PageIterator<Blog> getBlogs(int page, int pageSize) {
        if(page <=0 || pageSize <= 0) {
            page = 1;
            pageSize = PAGESIZE;
        }
        int totalCount = countBlog();
        PageIterator<Blog> pageIterator = PageIterator.createInstance(page, pageSize, totalCount);
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page-1)*pageSize);
        map.put("limit", pageSize);
        List<Blog> blogs = blogMapper.getBlogs(map);
        pageIterator.setData(blogs);
        return pageIterator;
    }

    @Override
    public PageIterator<BlogVo> getBlogVos(int page, int pageSize) {
        long start = System.currentTimeMillis();
        if(page <=0 || pageSize <= 0) {
            page = 1;
            pageSize = PAGESIZE;
        }
        int totalCount = countBlog();
        log.info("===>>> get total cost:{} ms", System.currentTimeMillis() - start);
        PageIterator<BlogVo> pageIterator = PageIterator.createInstance(page, pageSize, totalCount);

        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page-1)*pageSize);
        map.put("limit", pageSize);

        List<BlogV1> blogs = blogV1Mapper.getBlogs(map);
        log.info("===>>> !!!get cost:{} ms", System.currentTimeMillis() - start);
//        long start1 = System.currentTimeMillis();
//        List<BlogVo> blogVos = Optional.ofNullable(blogs)
//                .orElse(Collections.emptyList())
//                .stream()
//                .map(blog -> makeBlogVo(blog))
//                .collect(Collectors.toList());

        pageIterator.setData(makeBlogVOs(blogs));
        log.info("===>>> makeBlogVos cost:{} ms", System.currentTimeMillis() - start);
        return pageIterator;
    }

    @Override
    public PageIterator<BlogVo> getAllBlogVos(int page, int pageSize) {
        long start = System.currentTimeMillis();
        if(page <=0 || pageSize <= 0) {
            page = 1;
            pageSize = PAGESIZE;
        }
        int totalCount = countBlog();
        PageIterator<BlogVo> pageIterator = PageIterator.createInstance(page, pageSize, totalCount);
        Map<String, Object> map = new HashMap<>();
        map.put("offset", (page-1)*pageSize);
        map.put("limit", pageSize);
        List<BlogV1> blogs = blogV1Mapper.getAllBlogs(map);
        pageIterator.setData(makeBlogVOs(blogs));
        log.info("===>>> makeBlogVos cost:{} ms", System.currentTimeMillis() - start);
        return pageIterator;
    }

    @Override
    public BlogVo getBlogVoById(String id) {
        log.info("===>>> id:{}", id);
        return getBlogVo(id);
    }

    @Override
    public List<BlogVo> getBlogVoByIds(List<String> bids) {
       List<Blog> blogs = blogMapper.getBlogsByIds(bids);
       return Optional.ofNullable(blogs).orElse(Collections.emptyList())
               .stream().map(blog -> makeBlogVo(blog)).collect(Collectors.toList());
    }

    @Override
    public PageIterator<BlogVo> getBlogVosByTag(String tagName, int page, int pageSize) {
        PageIterator<String> blogPageIterator = tagService.getPostsByTag(tagName, page, pageSize);
        if(blogPageIterator.getTotalCount() < 0) {
            return PageIterator.createInstance(page, pageSize, Collections.emptyList());
        }
        PageIterator<BlogVo> blogVoPageIterator = PageIterator.createInstance(page, pageSize, blogPageIterator.getTotalCount());
        List<BlogVo> blogVos = Optional.ofNullable(blogPageIterator.getData())
                .orElse(Collections.emptyList())
                .stream()
                .map(bid -> getBlogVo(bid))
                .collect(Collectors.toList());
        blogVoPageIterator.setData(blogVos);
        return blogVoPageIterator;
    }

    @Override
    public PageIterator<BlogVo> getBlogVosByCategory(Category category, int page, int pageSize) {
        List<String> categoryIds = categoryService.getCategoryIdsByCategory(category);
        if(CollectionUtils.isEmpty(categoryIds)) {
            return PageIterator.createInstance(page, pageSize, Collections.EMPTY_LIST);
        }
        int count = blogMapper.countBlogIdsByCategory(categoryIds);
        if(count <= 0) {
            return PageIterator.createInstance(page, pageSize, Collections.EMPTY_LIST);
        }
        PageIterator<BlogVo> blogVoPageIterator = PageIterator.createInstance(page, pageSize, count);
        Map<String, Object> data = new HashMap<>();
        data.put("list", categoryIds);
        data.put("offset", (page - 1)*pageSize);
        data.put("limit", pageSize);
       List<BlogVo> blogVos =  Optional.ofNullable(blogMapper.getBlogIdsByCategory(data))
                .orElse(Collections.emptyList())
                .stream()
                .map(bid -> getBlogVo(bid))
                .collect(Collectors.toList());
       blogVoPageIterator.setData(blogVos);
       return blogVoPageIterator;
    }

    @Override
    public int getVisitCountByids(List<String> ids) {
        //log.info("==>>> ids:{}", new Gson().toJson(ids));
        return Optional.ofNullable(blogMapper.getBlogsByIds(ids))
                .orElse(Collections.emptyList())
                .stream()
                .mapToInt(blog -> blog.getRcount())
                .sum();
    }

    public int countBlog() {
        return blogMapper.countBlog();
    }



    @Override
    public BaseMapper getMapper() {
        return blogMapper;
    }


    private BlogVo getBlogVo(String bid) {
       Blog blog = blogMapper.loadById(bid);
//       log.info("====>>> blog:{} ", blog);
       blog.setId(bid);
       return makeBlogVo(blog);
    }

    private BlogVo makeBlogVo(Blog blog) {
        BlogVo blogVo = new BlogVo();

        // log.info("===>>> blog:{}", blog);
        if(blog != null) {
            Category category = categoryService.loadById(blog.getCategoryid());
            User user = userService.loadById(blog.getCreator());
            // log.info("===>>> category:{}", category);
            // log.info("===>>> user:{}", user);
            blogVo.setTitle(blog.getTitle())
                    .setExcerpt(blog.getExcerpt())
                    .setContent(blog.getContent())
                    .setType(blog.getType())
                    .setParent(blog.getParent())
                    .setCategoryid(blog.getCategoryid())
                    .setPstatus(blog.getPstatus())
                    .setCstatus(blog.getCstatus())
                    .setCcount(blog.getCcount())
                    .setRcount(blog.getRcount())
                    .setId(blog.getId())
                    .setCreateTime(blog.getCreateTime())
                    .setCreator(blog.getCreator())
                    .setLastUpdate(blog.getLastUpdate());
            if(category != null) {
                blogVo.setCategory(category);
            }
            if(user != null) {
                blogVo.setUser(user);
            }
        }
        List<String> tags = tagService.listTagsByPost((String)blog.getId());
        //log.info("===>>> tags:{}", new Gson().toJson(tags));
        if(CollectionUtils.isNotEmpty(tags)) {
            blogVo.setTags(tags);
        }
        return blogVo;
    }

    private List<BlogVo> makeBlogVOs(List<BlogV1> blogs) {
        List<Integer> bids = blogs.stream()
                .map(blog -> Integer.valueOf(blog.getId()))
                .collect(Collectors.toList());
        List<String> uids = blogs.stream()
                .map(blog -> blog.getCreator())
                .collect(Collectors.toList());
        List<String> cids = blogs.stream()
                .map(blog -> blog.getCategoryid())
                .collect(Collectors.toList());
        log.info("===>>>bids:{} ", bids);
        Map<Integer, BlogContent> contentMap = Optional.ofNullable(blogContentMapper.getByIds(bids))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(BlogContent::getBlog_id, Function.identity(),  (u1, u2) -> u2));

        Map<String, User> userMap = Optional.ofNullable(userService.getByUserIds(uids))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(User::getId, Function.identity(),  (u1, u2) -> u2));
        Map<String, Category> categoryMap = Optional.ofNullable(categoryService.getCategorysByids(cids))
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(Category::getId, Function.identity(),  (u1, u2) -> u2));
//        Map<String, List<String>> tagsMap = Optional.ofNullable(tagService.getTags())
        return blogs.stream().map(blog -> {
            BlogVo blogVo = new BlogVo();
            if(blog != null) {
                Category category = categoryMap.get(blog.getCategoryid());
                User user = userMap.get(blog.getCreator());
                Integer id = Integer.valueOf(blog.getId());
                BlogContent content = contentMap.get(id);
                blogVo.setTitle(blog.getTitle())
                        .setExcerpt(content == null ? "" : content.getExcerpt())
                        .setContent(content == null ? "" : content.getContent())
                        .setType(blog.getType())
                        .setParent(blog.getParent())
                        .setCategoryid(blog.getCategoryid())
                        .setPstatus(blog.getPstatus())
                        .setCstatus(blog.getCstatus())
                        .setCcount(blog.getCcount())
                        .setRcount(blog.getRcount())
                        .setId(blog.getId())
                        .setCreateTime(blog.getCreateTime())
                        .setCreator(blog.getCreator())
                        .setLastUpdate(blog.getLastUpdate());
                if(category != null) {
                    blogVo.setCategory(category);
                }
                if(user != null) {
                    blogVo.setUser(user);
                }
            }
            return blogVo;
        }).collect(Collectors.toList());
    }

    @Override
    public BlogVo getNextBlogVo(String id) {
        String bid  = blogMapper.getNextBid(id);
        if(StringUtil.isEmpty(bid)) {
            return new BlogVo();
        }
        return getBlogVo(bid);
    }

    @Override
    public BlogVo getPrevBlogVo(String id) {
        String bid = blogMapper.getPrevBid(id);
        if(StringUtil.isEmpty(bid)) {
            return new BlogVo();
        }
        return getBlogVo(bid);
    }

    @Override
    public List<Blog> getRecentBlogs(int limit) {
        List<Blog> blogs = Optional.ofNullable(blogMapper.getRecentBlogs(limit))
                .orElse(Collections.emptyList())
                .stream()
                .map(blog -> blog.setCreateDate(datefo.format(blog.getCreateTime())))
                .collect(Collectors.toList());
        return blogs;
    }

    @Override
    public List<Blog> getBlogByTitle(String title) {
        return blogMapper.getBlogByTitle(title);
    }

    @Override
    public Blog getBlogById(String id) {
        return blogMapper.getBlogById(id);
    }


    /**
     * 更改评论数
     *
     * @param commentid
     * @param count
     */
    public int addCcount(String commentid, int count){
        return blogMapper.addCcount(commentid, count);
    }


    @Override
    public boolean insertBlogV2(Blog blog) {
        int a = 0;
        int b = 0;
        if(blogV1Mapper.getById(Integer.valueOf(blog.getId())) == null) {
            BlogV1 blogV1 = new BlogV1();
            blogV1.setId(blog.getId());
            blogV1.setTitle(blog.getTitle());
            blogV1.setType(blog.getType());
            blogV1.setParent(blog.getParent());
            blogV1.setCategoryid(blog.getCategoryid());
            blogV1.setPstatus(blog.getPstatus());
            blogV1.setCstatus(blog.getCstatus());
            blogV1.setCcount(blog.getCcount());
            blogV1.setRcount(blog.getRcount());
            blogV1.setCreator(blog.getCreator());
            blogV1.setBlog_type(blog.getBlog_type());
            blogV1.setCreateTime(blog.getCreateTime());
            blogV1.setLastUpdate(blog.getLastUpdate());
            a = blogV1Mapper.insertBlogV1(blogV1);
        }
        if(blogContentMapper.getById(Integer.valueOf(blog.getId())) == null) {
            BlogContent blogContent = new BlogContent();
            blogContent.setBlog_id(Integer.valueOf(blog.getId()));
            blogContent.setContent(StringUtils.isBlank(blog.getContent()) ? "" : blog.getContent());
            blogContent.setExcerpt(StringUtils.isBlank(blog.getExcerpt()) ? "" : blog.getExcerpt());
            b = blogContentMapper.insertBlogContent(blogContent);
        }
        if(a==0 && b==0) {
            return true;
        }
        if(a > 0 || b > 0) {
            return true;
        }
        return false;
    }
}
