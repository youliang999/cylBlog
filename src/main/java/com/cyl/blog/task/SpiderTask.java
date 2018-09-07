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
import com.cyl.blog.util.CollectionUtils;
import com.cyl.blog.util.IdGenerator;
import com.cyl.blog.util.MySpiderUtil;
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
import java.util.stream.Collectors;

/**
 * Created by youliang.cheng on 2018/8/13.
 */
public class SpiderTask {
    private static final Logger log = LoggerFactory.getLogger(SpiderTask.class);
    private static SpiderTask instance = new SpiderTask();
    private static final String cookie = "UM_distinctid=16537bc97f476a-09ba45f4ea181-6315107d-15f900-16537bc97f57bf; CNZZDATA5003572=cnzz_eid%3D1084636199-1534238474-%26ntime%3D1534238474";
    public static CookieStore cookieStore = null;
    private static final ThreadPoolExecutor spiderThread = new ThreadPoolExecutor(10, 10, 0l,TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1000), new ThreadPoolExecutor.DiscardPolicy());
    public static SpiderTask getInstance() {
        return instance;
    }

    private static CloseableHttpClient client = HttpClients.custom()
            .setDefaultCookieStore(cookieStore).build();
    private ConcurrentLinkedQueue<BlogVo> spiderQueue = new ConcurrentLinkedQueue<>();


    private SpiderTask() {
        setCookieStore(cookie);
    }

    //private static final CategoryService categoryService =  (CategoryService)ApplicationContextUtil.getBean("categoryService");

    private static final CategoryService categoryService = (CategoryService) ApplicationContextUtil1.getInstance().getCategoryService();
    private static final BlogService blogService = (BlogService) ApplicationContextUtil1.getInstance().getBlogService();
    private static final UserService userService = (UserService) ApplicationContextUtil1.getInstance().getUserService();
    private static final TagService tagService = (TagService) ApplicationContextUtil1.getInstance().getTagService();
    public static void main(String[] args) {
        instance.startCrawler();
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

    public void startParse(){

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i =0; i<10 ; i++) {
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
                        blog.setCategoryid(blogVo.getCategory().getId());
                        blog.setPstatus(1);
                        blog.setCstatus("open");
                        blog.setCcount(0);
                        blog.setRcount(0);
                        blog.setCreator(blogVo.getUser().getId());
                        blog.setCreateTime(new Date());
                        blog.setLastUpdate(new Date());
                        String content = "";
                        HttpResponse response = null;
                        try {
                            response = getResponse(spiderUrl);
                            content = dealResponseGetContentInfo(response);
                        } catch (IOException e) {
                            log.warn("===>>> happen error.");
                            e.printStackTrace();
                        }

                        //System.out.println("c: " + content);
                        blog.setContent(filterEmoji(content));
                        //System.out.println("===>>> blog: " + blog);

                        blogService.insert(blog);
                        log.warn("===>>> insert a blog into table success, title:{} ", blog.getTitle());
                        log.warn("===>>> size:{}", spiderQueue.size());
                    } while (spiderQueue.size() > 0);
                }
            });
        }


    }

    private HttpResponse getResponse(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        get.addHeader("Referer", "https://ifeve.com/");
        HttpResponse httpResponse = client.execute(get);
        return httpResponse;
    }

    public void startCrawler() {
        try {


//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        for (int i = 2; i < 107; i++) {
//                            String ajaxUrl = "https://ifeve.com/page/" + i;
//                            HttpResponse response = getResponse(ajaxUrl);
//                            dealResponseGetBaseInfo(response);
//                            log.warn("===>>> page: " + i + "spider over.");
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();

//            MySpiderUtil.serializeObject(spiderQueue, "src/main/resources/proxies");
//
            log.info("1111");
            spiderQueue.addAll((ConcurrentLinkedQueue<BlogVo>) MySpiderUtil.deserializeObject("src/main/resources/proxies"));
            log.info("222");
            do {
                log.warn("===>>> size:{}", spiderQueue.size());
                BlogVo bo = spiderQueue.poll();
                List<String> tags = bo.getTags();
                for (String tag : tags) {

                        Tag tag1 = new Tag();
                        Blog b = (blogService.getBlogByTitle(bo.getTitle()) == null ||  blogService.getBlogByTitle(bo.getTitle()).size() == 0) ? null : blogService.getBlogByTitle(bo.getTitle()).get(0);

                        if (b != null) {
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
            } while (spiderQueue.size() > 0);
        //    log.warn("===>>> size:{}", spiderQueue.size());
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(2000);
//                        startParse();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();


        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            // 关闭流并释放资源
//            try {
//                client.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

    }

    private String dealResponseGetContentInfo(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseString = EntityUtils.toString(entity, Charset.forName("utf-8"));
            Document document = Jsoup.parse(responseString);
            String co = document.select("div[class=post_content]").html();
            return co;
        }
        return "";
    }

    private void dealResponseGetBaseInfo(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseString = EntityUtils.toString(entity, Charset.forName("utf-8"));
            // System.out.println("res:　" + responseString);
            Document document = Jsoup.parse(responseString);
            Elements elements = document.select("div[id=left_col]").select("div[class=post_wrap clearfix]");
            //System.out.println("size: " + elements.size());
            for (Element element : elements) {
                BlogVo blogVo = new BlogVo();
                String title = element.select("h3").text();
                String url = element.select("h3").select("a").attr("href").trim();
                String excerpt = element.select("div[class=post_content]").select("p").text();
                String author = element.select("div[class=meta]").select("li[class=post_author]").select("a").text();
                Elements tagEles = element.select("div[class=post_meta clearfix]").select("li");
                List<String> tags = tagEles.stream().map(ele -> ele.select("a").text()).collect(Collectors.toList());
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
                    User user = new User();
                    user.setId(IdGenerator.uuid19());
                    user.setNickName(author);
                    user.setRealName(author);
                    user.setPassword("111111");
                    user.setEmail("unknown@cyl.com");
                    user.setStatus("N");
                    user.setRole("contributor");
                    user.setDescription(author);
                    user.setCreateTime(new Date());
                    user.setLastUpdate(new Date());
                    userService.insert(user);
                    blogVo.setUser(user);
                }
                blogVo.setCreator(author);
                blogVo.setTags(tags);
                String tag = "";
                if (!CollectionUtils.isEmpty(tags)) {
                    tag = tags.get(0);
                }
                List<Category> categories = categoryService.getCategorys(100);
                List<String> cates = Optional.ofNullable(categories)
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Category -> Category.getName())
                        .collect(Collectors.toList());
                if (!cates.contains(tag)) {
                    Category category = new Category();
                    category.setId(IdGenerator.uuid19());
                    category.setName(tag);
                    category.setCreateTime(new Date());
                    category.setLastUpdate(category.getCreateTime());
                    categoryService.insertChildren(category, "Root");
                    Category newCategory = categoryService.loadByName(tag);
                    blogVo.setCategory(newCategory);
                } else {
                    blogVo.setCategory(categoryService.loadByName(tag));
                }
//                System.out.println("t: " +title+ "url: " + url);
//                System.out.println("e: " +excerpt+ "a: " + author);
                spiderQueue.add(blogVo);
//                System.out.println("blogVo: " + new Gson().toJson(blogVo));
            }
            log.warn("===>>> queue size: " + spiderQueue.size());
        }
    }

    public static String filterEmoji(String source) {
        if (source != null && source.length() > 0) {
            return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "");
        } else {
            return source;

        }
    }
}
