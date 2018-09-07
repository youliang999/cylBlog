package com.cyl.blog.service.jedis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public interface JedisOperationService<K, V> extends KeyOperation<K>, RedisOperation {

    V get(K key);

    V getOrLoadAndSet(K key, SingleLoader<K, V> loader, long duration, TimeUnit timeUnit);

    V getSet(K key, V value);

    V getSet(K key, V value, long duration, TimeUnit timeUnit);

    List<V> batchGet(K... keys);

    List<V> batchGetOrLoadAndSet(Collection<K> keys,  SingleLoader<K, V> loader, long duration, TimeUnit timeUnit);

    void set(K key, V value);

    void set(K key, V value, long duration, TimeUnit timeUnit);

    //  SET if Not eXists
    Boolean setNx(K key, V value);

    Boolean setNx(K key, V value, long duration, TimeUnit timeUnit);

    void setEx(K key, long seconds, V value);

    void batchSet(Map<K, V> map);

    void batchSet(Map<K, V> map, long duration, TimeUnit timeUnit);

    void batchSetNx(Map<K, V> map);

    void batchSetNx(Map<K, V> map, long duration, TimeUnit timeUnit);


}
