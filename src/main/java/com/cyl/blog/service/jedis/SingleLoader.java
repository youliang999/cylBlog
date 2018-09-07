package com.cyl.blog.service.jedis;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public interface SingleLoader<K, V> {
    V load(K k);
}