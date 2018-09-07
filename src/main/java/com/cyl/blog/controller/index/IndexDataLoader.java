package com.cyl.blog.controller.index;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.cyl.blog.controller.helper.BlogHelper;
import com.cyl.blog.controller.index.model.BlogVo;
import com.cyl.blog.plugin.PageIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/7/31.
 */
@Component
public class IndexDataLoader implements InitializingBean{
    private static final Logger log = LoggerFactory.getLogger(IndexDataLoader.class);
    private final Map<String, Object> cache = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService SCHEDULE_TASK = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    private static final Integer UPDATE_PERIOD = 6;
    private static final TimeUnit UPDATE_TIME_UNIT = TimeUnit.HOURS;
    private static final Integer pageDataPeriod = 5;
    private static final TimeUnit pageUpdateTimeUnit = TimeUnit.MINUTES;

    /**
     * 首页文章
     */
    public static final String INDEX_PAGE_DATA = "indexPageData";

    /**
     * 近期文章
     */
    public static final String RECENT_BLOG = "recentBlog";

    /**
     * 标签
     */
    public static final String TAG = "tag";

    /**
     * 文章归档
     */
    public static final String CATEGORY = "category";

    @Autowired
    private BlogHelper blogHelper;

    @Override
    public void afterPropertiesSet() throws Exception {
        schemeTask();
    }

    private void schemeTask () {
        long start = System.currentTimeMillis();
        SCHEDULE_TASK.scheduleAtFixedRate(() -> {
            try {
                put(INDEX_PAGE_DATA, blogHelper.listPost(1, 20));
            } catch (Exception e) {
                log.error("===>>> task INDEX_PAGE_DATA catch a exception: {}", e.toString());
            }
        }, 0, pageDataPeriod, pageUpdateTimeUnit);

        SCHEDULE_TASK.scheduleAtFixedRate(() -> {
            try {
                put(RECENT_BLOG, blogHelper.getRecentBlog(20));
            } catch (Exception e) {
                log.error("===>>> task RECENT_BLOG catch a exception:{}", e.toString());
            }
        }, 0, UPDATE_PERIOD, UPDATE_TIME_UNIT);

        SCHEDULE_TASK.scheduleAtFixedRate(() -> {
            try {
                put(TAG, blogHelper.getTags(100));
            } catch (Exception e) {
                log.error("===>>> task TAG catch a exception:{}", e.toString());
            }
        }, 0, UPDATE_PERIOD, UPDATE_TIME_UNIT);

        SCHEDULE_TASK.scheduleAtFixedRate(() -> {
            try {
                put(CATEGORY, blogHelper.getCategory(20));
            } catch (Exception e) {
                log.error("===>>> task CATEGORY catch a exception:{}", e.toString());
            }
        }, 0, UPDATE_PERIOD, UPDATE_TIME_UNIT);
        log.info("===>>> load INDEX_PAGE_DATA cost {} ms", System.currentTimeMillis() - start);
    }


    private void put(String key, Object value) {
        cache.put(key, value);
        log.info("success put {}", key);
    }


    public Object get(String key) {
        if(StringUtils.isEmpty(key)) {
            return null;
        }
        if(key.equals(INDEX_PAGE_DATA)) {
            PageIterator<BlogVo> blogVoPageIterator = (PageIterator<BlogVo>)cache.get(INDEX_PAGE_DATA);
            if(blogVoPageIterator == null || blogVoPageIterator.getData() == null) {
                put(INDEX_PAGE_DATA, blogHelper.listPost(1, 20));
            }
        }
        return cache.get(key);
    }
}
