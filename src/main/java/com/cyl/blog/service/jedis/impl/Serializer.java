package com.cyl.blog.service.jedis.impl;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public interface Serializer<R, T> {

        R serialize(T t);

        T deserialize(R r);
}
