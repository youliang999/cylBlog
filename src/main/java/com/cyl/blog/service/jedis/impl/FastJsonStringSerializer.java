package com.cyl.blog.service.jedis.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public class FastJsonStringSerializer<T> extends StringSerializer<T> {
    private static final String NULL_STR = "(null)";
    private final Class<T> cls;

    public FastJsonStringSerializer(Class<T> cls) {
        this.cls = cls;
    }

    public FastJsonStringSerializer() {
        this.cls = null;
    }

    @Override
    public String serialize(T t) {
        if (t == null) {
            return NULL_STR;
        }
        if (this.cls != null && this.cls.isAssignableFrom(t.getClass())) {
            return JSON.toJSONString(t);
        } else {
            return JSON.toJSONString(t, SerializerFeature.WriteClassName);
        }
    }

    @Override
    public T deserialize(String s) {
        if (NULL_STR.equals(s)) {
            return null;
        }
        if (this.cls != null) {
            return (T) JSON.parseObject(s, this.cls);
        } else {
            return (T) JSON.parse(s);
        }
    }
}

