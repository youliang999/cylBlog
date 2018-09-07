package com.cyl.blog.service.jedis;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public interface KeyOperation<K> extends RedisOperation {

    Boolean exists(K key);

    Long del(K... keys);

    Set<String> keys(String pattern);

    String randomKey();

    void rename(K oldName, K newName);

    Boolean renameNX(K oldName, K newName);

    Boolean expire(K key, long seconds);

    Boolean expire(K key, long duration, TimeUnit timeUnit);

    Boolean expireAt(K key, long unixTime);

    Boolean persist(K key);

    Boolean move(K key, int dbIndex);

    Long ttl(K key);
}
