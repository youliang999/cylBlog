package com.cyl.blog.task;

import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Category;
import com.cyl.blog.entity.Tag;
import com.cyl.blog.entity.User;
import com.cyl.blog.helper.ApplicationContextUtil1;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.service.TagService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.solr.service.BlogIndexService;
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.IdGenerator;
import com.cyl.blog.util.MySpiderUtil;
import com.dajie.common.util.StringUtil;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by youliang.cheng on 2018/8/23.
 */
public class CnblogsSpiderTask1 {
    private static final Logger log = LoggerFactory.getLogger(CnblogsSpiderTask1.class);
    private static final Pattern postIdPattern =  Pattern.compile("[http|https]+[://]+[0-9A-Za-z:/](\\d{0,10}).html");
    private static final Pattern getPostIdPattern = Pattern.compile(",[cb_entryId]+[=](\\d{0,10})");
    private static final Pattern blogIdPattern =  Pattern.compile(",[cb_blogId]+[=](\\d{0,10})");
    private static final Pattern blogappPattern = Pattern.compile("[currentBlogApp]+[\\s{0, 2}][=][\\s{0,2}]'(.*)',");
    //private static final Pattern categoryTagPattern =  Pattern.compile("[\"Tags\"]+[:](.*[,])[\"Categories\"]+([:].*)");
    private static final Pattern categoryTagPattern =  Pattern.compile("[\"Tags\"]+[:](.*)[,][\"Categories\"]+[:](.*)[}]");

    private static CnblogsSpiderTask1 instance = new CnblogsSpiderTask1();
    private static final String cookie = "_ga=GA1.2.1831294803.1534906593; _gid=GA1.2.597318180.1534906593";
    public static CookieStore cookieStore = null;
    private static final ThreadPoolExecutor spiderThread = new ThreadPoolExecutor(10, 10, 0l, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardPolicy());
    public static CnblogsSpiderTask1 getInstance() {
        return instance;
    }

    private static CloseableHttpClient client = HttpClients.custom()
            .setDefaultCookieStore(cookieStore).build();
    private ConcurrentLinkedQueue<BlogVo> spiderQueue = new ConcurrentLinkedQueue<>();


    private CnblogsSpiderTask1() {
        setCookieStore(cookie);
    }

    //private static final CategoryService categoryService =  (CategoryService)ApplicationContextUtil.getBean("categoryService");

    private static final CategoryService categoryService = (CategoryService) ApplicationContextUtil1.getInstance().getCategoryService();
    private static final BlogService blogService = (BlogService) ApplicationContextUtil1.getInstance().getBlogService();
    private static final UserService userService = (UserService) ApplicationContextUtil1.getInstance().getUserService();
    private static final TagService tagService = (TagService) ApplicationContextUtil1.getInstance().getTagService();

    private static final BlogIndexService blogIndexService = (BlogIndexService) ApplicationContextUtil1.getInstance().getBlogIndexService();
    public static void main(String[] args) {
        instance.startCrawler();
    }


    private void startCrawler() {

                    try {
                        for (int i = 1; i <= 200; i++) {
                            String ajaxUrl = "";
                            if(i<=1) {
                                    ajaxUrl = "https://www.cnblogs.com/cate/Engineering/";
                            } else { //106876   108696
                                ajaxUrl = "https://www.cnblogs.com/mvc/AggSite/PostList.aspx" + "?CategoryId=106889&CategoryType=SiteCategory&ItemListActionName=PostList&ParentCategoryId=108709&TotalPostCount=248&PageIndex=" + i;
                            }
                            HttpResponse response = getResponse(ajaxUrl);
                            dealResponseGetBaseInfo(response);
                            log.warn("===>>> page: " + i + "spider over.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//
         MySpiderUtil.serializeObject(spiderQueue, "src/main/resources/cnblogs-proxies-Engineering)");


//        String spiderUrl = "https://www.cnblogs.com/Java3y/p/8664874.html";
//        HttpResponse response = null;
//        try {
//            response = getResponse(spiderUrl);
//            BlogVo blogVo = new BlogVo();
//            //dealResponseGetContentInfo(response, blogVo);
//            getCategoryTags(response, blogVo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



        try {
            Thread.sleep(10000);
            ConcurrentLinkedQueue<BlogVo> queue =  (ConcurrentLinkedQueue<BlogVo>) MySpiderUtil.deserializeObject("src/main/resources/cnblogs-proxies-Engineering)");
            spiderQueue.addAll(queue);
            startParse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startParse(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i =0; i<100 ; i++) {
            spiderThread.execute(new Runnable() {
                @Override
                public void run() {
                    do {
                        log.warn("===>>> size:{}", spiderQueue.size());
                        BlogVo blogVo = spiderQueue.poll();
                        //System.out.print("===>>> blogVo: " +blogVo);
                        String spiderUrl = blogVo.getUrl();
                        Blog blog = new Blog();
                        blog.setTitle(blogVo.getTitle());
                        if (blogService.getBlogByTitle(blog.getTitle()).size() > 0) {
                            log.warn("=== blog:{} have exist", blog.getTitle());
                            continue;
                        }
                        blog.setExcerpt(filterEmoji(blogVo.getExcerpt()));
                        log.info("===>>> excerpt‘s size: {}", filterEmoji(blogVo.getExcerpt()).length());
                        blog.setType("post");
                        blog.setParent("Root");


                        blog.setPstatus(1);
                        blog.setCstatus("open");
                        blog.setCcount(0);
                        blog.setRcount(0);
                        blog.setCreator(blogVo.getUser().getId());
                        blog.setBlog_type("Engineering");
                        blog.setCreateTime(new Date());
                        blog.setLastUpdate(new Date());
                        String content = "";
                        HttpResponse response = null;
                        String contentStr = "";
                        try {
                            response = getResponse(spiderUrl);
                            contentStr=  dealResponseGetContentInfo(response, blogVo);
                        } catch (IOException e) {
                            log.warn("===>>> happen error.");
                            e.printStackTrace();
                        }
                      //  log.info("===>>> coontentStr:{}", contentStr);
                        makegetCategoryTagUrl(spiderUrl,  contentStr, blogVo);

                        blog.setCategoryid("WP8eNpwKaeyWnVP5sKU");

                        //System.out.println("c: " + content);
                        blog.setContent(filterEmoji(blogVo.getContent()));
                        //System.out.println("===>>> blog: " + blog);

                        blogService.insert(blog);
                        List<String> tags = blogVo.getTags();
                        if(CollectionUtils.isNotEmpty(tags)) {
                            Blog b = (blogService.getBlogByTitle(blogVo.getTitle()) == null ||  blogService.getBlogByTitle(blogVo.getTitle()).size() == 0) ? null : blogService.getBlogByTitle(blogVo.getTitle()).get(0);
                            if(b != null) {
                            for (String tag : tags) {
                                Tag tag1 = new Tag();
                                    if (!tagService.isExist(b.getId())) {
                                        tag1.setId(IdGenerator.uuid19());
                                        tag1.setName(tag);
                                        tag1.setPostid(b.getId());
                                        tag1.setCreateTime(new Date());
                                        tagService.insert(tag1);
                                        log.warn("===>>> insert success.");
                                    }
                                }
                            }
                        } else {
                            log.error("===>>> title:{}, tags is null", blogVo.getTitle());
                        }
                        log.warn("===>>> insert a blog into table success, title:{} id:{}", blog.getTitle());
                        log.warn("===>>> size:{}", spiderQueue.size());
                    } while (spiderQueue.size() > 0);
                }
            });
        }


    }

    private void makegetCategoryTagUrl(String url, String content, BlogVo blogVo) {

        if(StringUtil.isEmpty(getPostId(content)) || StringUtil.isEmpty(getBlogId(content)) || StringUtil.isEmpty(content)) {
            return ;
        }
        String baseUrl ="https://www.cnblogs.com/mvc/blog/CategoriesTags.aspx?blogApp=" + getApp(content) +"&blogId=" + getBlogId(content) +"&postId="+  getPostId(content);
        log.info("===>>> baseUrl:{}", baseUrl);
        try {
            HttpResponse response = getResponse(baseUrl);
            getCategoryTags(response, blogVo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPostId(String content) {
        Matcher m = getPostIdPattern.matcher(content);
        if(m.find()) {
            System.out.println("===>>> ss" + m.group(1));
            return m.group(1);
        } else {
            return "0";
        }
    }

    private String getBlogId(String content) {
        Matcher m = blogIdPattern.matcher(content);
        if(m.find()) {
            System.out.println("===>>> aa " + m.group(1));
            return m.group(1);
        }
        return "0";
    }

    private String getApp(String content) {
        Matcher m = blogappPattern.matcher(content);
        if(m.find()) {
            System.out.println("===>>> cc " + m.group(1));
            return m.group(1);
        }
        return "";
    }



    private void getCategoryTags(HttpResponse response, BlogVo blogVo) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseString = new String(EntityUtils.toString(entity, Charset.forName("utf-8")).getBytes("utf-8"), "UTF-8");
            log.info("===>>> categorySss:{}", responseString);
            Matcher tm = categoryTagPattern.matcher(responseString);
            String categoryStr = responseString;
            String tagStr = responseString;
            if(tm.find()) {
                try {
                    System.out.println("tag: {}" + tm.group(1));
                    System.out.println("category: {}" + tm.group(2));
                    categoryStr = tm.group(2);
                    tagStr = tm.group(1);
                } catch (Exception e) {
                    log.error("-----error------");
                }
            }
            Document cdocument = Jsoup.parse(categoryStr);
            Document tdocument = Jsoup.parse(tagStr);
            String cstr = cdocument.select("a").text();
            String cstr1 = tdocument.select("a").text();
            Elements tagEles = tdocument.select("a");
            List<String> tags = tagEles.stream().map(ele -> ele.text()).collect(Collectors.toList());
            log.info("===>>> categoryStr:{}, cstr1:{} tags:{}", cstr, cstr1, new Gson().toJson(tags));
            blogVo.setTags(tags);
            String categ = "";
            if(StringUtil.isEmpty(cstr)) {
                if (!CollectionUtils.isEmpty(tags)) {
                    categ = tags.get(0);
                }
            } else {
                categ = categoryStr;
            }
            if(StringUtil.isEmpty(categoryStr)) {
                return;
            }
            List<Category> categories = categoryService.getCategorys(100);
            List<String> cates = Optional.ofNullable(categories)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(Category -> Category.getName())
                    .collect(Collectors.toList());
            if (!cates.contains(categ)) {
                Category category = new Category();
                category.setId(IdGenerator.uuid19());
                category.setName(categ);
                category.setCreateTime(new Date());
                category.setLastUpdate(category.getCreateTime());
                categoryService.insertChildren(category, "Root");
                Category newCategory = categoryService.loadByName(categ);
                blogVo.setCategory(newCategory);
            } else {
                blogVo.setCategory(categoryService.loadByName(categ));
            }
        }
    }

    private String dealResponseGetContentInfo(HttpResponse response, BlogVo blogVo) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseString = EntityUtils.toString(entity, Charset.forName("utf-8"));
            Document document = Jsoup.parse(responseString);
            String co = document/*.select("div[class=postBody]")*/.select("div[id=cnblogs_post_body]").html();
            blogVo.setContent(co);
//            log.info("===>>> co:{}", co);
            return responseString;
        }
        return "";
    }

    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;

        }
    }

    private void dealResponseGetBaseInfo(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity, Charset.forName("utf-8"));
            //log.info("responseString:　{}" , responseString);
            Document document = Jsoup.parse(responseString);
            Elements elements = document./*select("div[id=post_list]").*/select("div[class=post_item]");
            for (Element element : elements) {
                BlogVo blogVo = new BlogVo();
                String title = element.select("div[class=post_item_body]").select("h3").text();
                String url = element.select("div[class=post_item_body]").select("h3").select("a").attr("href").trim();
                String excerpt = element.select("div[class=post_item_body]").select("p[class=post_item_summary]").text();
                String author = element.select("div[class=post_item_body]").select("div[class=post_item_foot]").select("a[class=lightblue]").text();
                blogVo.setTitle(title);
                blogVo.setUrl(url);
                blogVo.setExcerpt(excerpt);
                List<User> users = userService.getUser();
                List<String> nickNames = Optional.ofNullable(users)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(User -> User.getNickName())
                        .collect(Collectors.toList());
                List<String> realNames = Optional.ofNullable(users)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(User -> User.getRealName())
                        .collect(Collectors.toList());
                if (nickNames.contains(author) || realNames.contains(author)) {
                    blogVo.setUser(userService.getUserByNickName(author));
                } else {
                    if("Leo-Yang".equals(author) || "Ray Liang".equals(author) || "东方".equals(filterEmoji(author)) || "骨月枫".equals(filterEmoji(author))) {
                        return;
                    }
                    User user = new User();
                    user.setId(IdGenerator.uuid19());
                    user.setNickName(filterEmoji(author));
                    user.setRealName(filterEmoji(author));
                    user.setPassword("111111");
                    user.setEmail("unknown@cyl.com");
                    user.setStatus("N");
                    user.setRole("contributor");
                    user.setDescription(filterEmoji(author));
                    user.setCreateTime(new Date());
                    user.setLastUpdate(new Date());
                    userService.insert(user);
                    blogVo.setUser(user);
                }
                blogVo.setCreator(author);
                //System.out.println("blogVo: " + new Gson().toJson(blogVo));
                log.info("===>>> title:{}", title);
                if(spiderQueue.contains(blogVo)) {
                    continue;
                }
                spiderQueue.add(blogVo);
            }
            log.warn("===>>> queue size: " + spiderQueue.size());
        }
    }

    private HttpResponse getResponse(String url) throws IOException {
        log.info("===>>> url: {}", url);
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        get.addHeader("Referer", "https://www.cnblogs.com/cate/python/");
//        get.addHeader(":authority", "www.cnblogs.com");
//        get.addHeader(":method", "get");
//        get.addHeader(":path", "/cate/java/");
//        get.addHeader(":scheme", "https");
        HttpResponse httpResponse = client.execute(get);
//        log.info("===>>> response:{}", httpResponse.getEntity().getContent());
        return httpResponse;
    }


    public static void setCookieStore(String cook) {
        System.out.println("----setCookieStore");
        cookieStore = new BasicCookieStore();
        if (cook.length() <= 0) {
            return;
        }
        String[] cs = cook.split(";");
        if (cs.length <= 0) {
            return;
        }
        for (String coo : cs) {
            String[] coos = coo.split(":");
            if (coos != null && coos.length == 2) {
                String name = coos[0];
                String value = coos[1];
                BasicClientCookie cookie = new BasicClientCookie(name,
                        value);
                cookieStore.addCookie(cookie);
            }

        }
    }

}
