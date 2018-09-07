package com.cyl.blog.service.jedis.impl;

import com.cyl.blog.service.jedis.KeyOperation;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public class ValueSerializeBaseServiceImpl<K, V> extends JedisBaseKeyOperationServiceImpl<K> implements KeyOperation<K> {

    public ValueSerializeBaseServiceImpl(StringSerializer<V> serializer) {
        this.serializer = serializer;
    }

    private final StringSerializer<V> serializer;



    public V deserialize(String r) {
        return this.serializer.deserialize(r);
    }

    public String serialize(V t) {
        return this.serializer.serialize(t);
    }


}
