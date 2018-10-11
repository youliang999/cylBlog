package com.cyl.blog.task;

import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.entity.Blog;
import com.cyl.blog.entity.Tag;
import com.cyl.blog.entity.User;
import com.cyl.blog.helper.ApplicationContextUtil1;
import com.cyl.blog.service.BlogService;
import com.cyl.blog.service.CategoryService;
import com.cyl.blog.service.TagService;
import com.cyl.blog.service.UserService;
import com.cyl.blog.task.util.SpilderUtil;
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
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.cyl.blog.task.util.SpilderUtil.filterEmoji;

/**
 * Created by youliang.cheng on 2018/10/8.
 */
public class IBMSpliderTask {
    private static final Logger log = LoggerFactory.getLogger(IBMSpliderTask.class);
    private ConcurrentLinkedQueue<BlogVo> spiderQueue = new ConcurrentLinkedQueue<>();
    private String cookie ="optimizely-user-id=919is5h29r.i; lp-sync-42327359-vid=U0YjAyYzdhYzFhM2Q5NzAw; lp-sync-42327359-sid=hdT2VJIfSl-a7lqiC_CKiw; optimizelyEndUserId=oeu1537942773932r0.17780994316209964; BMAID=bbdac179-8410-46fc-97a0-f7f10e21be8a; CoreID6=60715060075915379422023&ci=50200000|DEVWRKS_50200000|IBM_GlobalMarketing_52640000|IBM_GlobalMarketing_50200000|IBM_IndustryPlatforms_52640000|IBM_IndustryPlatforms_50200000|GLOBAL_50200000|IBM_GTS_52640000|IBM_GTS_52640000|DEVWRKS; CoreM_State=52~-1~-1~-1~-1~3~3~5~3~3~7~7~|~~|~~|~~|~||||||~|~~|~~|~~|~~|~~|~~|~~|~; CoreM_State_Content=6~|~~|~|; cmTPSet=Y; _hjIncludedInSample=1; JSESSIONID=0000WPBGBlhMan-INesxvuFgj9w:1a625o1kc; OPTOUTMULTI=0:0%7Cc1:1%7Cc2:0%7Cc3:0; notice_behavior=implied|eu; utag_main=v_id:0166147f9f050009e577571515780506c0030064009dc$_sn:5$_ss:0$_st:1538980534873$is_country_member_of_eu:false$dc_visit:5$ses_id:1538978704499%3Bexp-session$_pn:6%3Bexp-session$mm_sync:1%3Bexp-session$dc_event:6%3Bexp-session$dc_region:ap-northeast-1%3Bexp-session; 50200000_clogin=l=78429041538978705195&v=33&e=1538980534880; 52640000_clogin=l=79151181538978705200&v=33&e=1538980534881";
    private static final String serializeName = "IBMJAVA";
    private static final ThreadPoolExecutor spiderThread = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1), new ThreadPoolExecutor.AbortPolicy());
    public static CookieStore cookieStore = null;
    private static CloseableHttpClient client = HttpClients.custom()
            .setDefaultCookieStore(cookieStore).build();


    private static final CategoryService categoryService = (CategoryService) ApplicationContextUtil1.getInstance().getCategoryService();
    private static final BlogService blogService = (BlogService) ApplicationContextUtil1.getInstance().getBlogService();
    private static final UserService userService = (UserService) ApplicationContextUtil1.getInstance().getUserService();
    private static final TagService tagService = (TagService) ApplicationContextUtil1.getInstance().getTagService();


    public IBMSpliderTask() {
        setCookieStore(cookie);
    }

    public static void main(String[] args) {
        IBMSpliderTask task = new IBMSpliderTask();
        task.execute();
        System.exit(0);
    }

    private void execute() {
//        makeUser("IBM-POST");
       // serializePage();
        deserializePage();
    }

    private void deserializePage() {
        try {
            Thread.sleep(1000);
            ConcurrentLinkedQueue<BlogVo> queue =  (ConcurrentLinkedQueue<BlogVo>) MySpiderUtil.deserializeObject("src/main/resources/" + serializeName);
            spiderQueue.addAll(queue);
            startParse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startParse() {
        try {
            Thread.sleep(1000);
//            for (int i =0; i<10 ; i++) {
//                spiderThread.execute(new Runnable() {
//                    @Override
//                    public void run() {
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
                            blog.setCreator("RcmrjZgkHtLHwKBvciM");
                            blog.setBlog_type("java");
                            blog.setCreateTime(new Date());
                            blog.setLastUpdate(new Date());
                            HttpResponse response = null;
                            try {
                                response = getResponse(spiderUrl);
                                dealResponseGetContentInfo(response, blogVo, spiderUrl);
                            } catch (IOException e) {
                                log.warn("===>>> happen error...");
                                e.printStackTrace();
                            }
                            blog.setCategoryid("kphIbS8DXL7iNJ8RaLy");
                            blog.setContent(filterEmoji(blogVo.getContent()));
                            blogService.insertBlog(blog);
                            List<String> tags = Arrays.asList("java, 技术");
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
//                    }
//                });
//            }
        } catch (InterruptedException e) {
            log.info("===>> error...");
            e.printStackTrace();
        }


    }

    private String dealResponseGetContentInfo(HttpResponse response, BlogVo blogVo, String url) throws IOException {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String responseString = EntityUtils.toString(entity, Charset.forName("utf-8"));
            Document document = Jsoup.parse(responseString);
            String co = document/*.select("div[class=postBody]")*/.select("div[id=ibm-content-main]").select("div[class=ibm-col-6-4]").html();
            String urlSuffix = url.replace("index.html", "");
            String replaceContent = SpilderUtil.repairContent(co, urlSuffix);
            blogVo.setContent(replaceContent);
//            log.info("===>>> co:{}", co);
            log.info("===>>> get content.");
            return responseString;
        }
        return "";
    }



    private void serializePage() {
        try {
            for (int i = 1; i <= 20; i++) {
                String ajaxUrl = "";
                if(i<=1) {
                    ajaxUrl = "https://www.ibm.com/developerworks/cn/views/java/libraryview.jsp?site_id=10&contentarea_by=Java&sort_by=&sort_order=2&start=1&end=100&topic_by=&product_by=&type_by=%E6%89%80%E6%9C%89%E7%B1%BB%E5%88%AB&show_abstract=true&search_by=&industry_by=&series_title_by=";
                } else { //106876   108696
                    ajaxUrl = "https://www.ibm.com/developerworks/cn/views/java/libraryview.jsp?site_id=10&contentarea_by=Java&sort_by=&sort_order=2&start=" +((i-1)*100+1) + "&end=" + i*100 +"&topic_by=&product_by=&type_by=%E6%89%80%E6%9C%89%E7%B1%BB%E5%88%AB&show_abstract=true&search_by=&industry_by=&series_title_by=";
                }
                HttpResponse response = getResponse(ajaxUrl);
                dealResponseGetBaseInfo(response);
                log.warn("===>>> page: " + i + "spider over.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//
        MySpiderUtil.serializeObject(spiderQueue, "src/main/resources/" + serializeName);
    }


    private HttpResponse getResponse(String url) throws IOException {
        log.info("===>>> url: {}", url);
        HttpGet get = new HttpGet(url);
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");
        get.addHeader("Referer", "https://www.ibm.com/developerworks/cn/views/java/libraryview.jsp");
        HttpResponse httpResponse = client.execute(get);
        return httpResponse;
    }


    private void dealResponseGetBaseInfo(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            String responseString = EntityUtils.toString(entity, Charset.forName("utf-8"));
            //log.info("responseString:　{}" , responseString);
            Document document = Jsoup.parse(responseString);
            Elements elements = document./*select("div[id=post_list]").*/select("tbody").select("tr");
            for (Element element : elements) {
                BlogVo blogVo = new BlogVo();
                String title = element.select("td").select("a").text();
                String url = element.select("td").select("a").attr("href").trim();
                String excerpt = element.select("td").select("div").text();
//                log.info("===>>> title:{}, url:{}, excerpt:{}", title, url, excerpt);
                //String author = element.select("div[class=post_item_body]").select("div[class=post_item_foot]").select("a[class=lightblue]").text();
                blogVo.setTitle(title);
                blogVo.setUrl(url);
                blogVo.setExcerpt(excerpt);
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

    private User makeUser(String author) {
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
        User user = new User();
        if (nickNames.contains(author) || realNames.contains(author)) {
        } else {
            if("Leo-Yang".equals(author) || "Ray Liang".equals(author) || "东方".equals(filterEmoji(author)) || "骨月枫".equals(filterEmoji(author))) {
                return null;
            }

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
        }
        return user;
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
