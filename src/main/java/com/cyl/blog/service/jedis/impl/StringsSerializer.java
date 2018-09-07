package com.cyl.blog.service.jedis.impl;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public class StringsSerializer extends StringSerializer<String> {
    @Override
    public String serialize(String s) {
        return s;
    }

    @Override
    public String deserialize(String s) {
        return s;
    }
}
