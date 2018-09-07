package com.cyl.blog.service.jedis.impl;

import com.cyl.blog.service.jedis.JedisOperationService;
import com.cyl.blog.service.jedis.SingleLoader;
import com.dajie.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public class JedisOperationServiceImpl<K, V> extends ValueSerializeBaseServiceImpl<K, V> implements JedisOperationService<K, V> {
    private static final Logger log = LoggerFactory.getLogger(JedisOperationServiceImpl.class);
    private static final long DEFAULT_DURATION = 365;
    private static final TimeUnit DEFAULT_TIME_UNIT = TimeUnit.DAYS;

    public JedisOperationServiceImpl(StringSerializer<V> serializer) {
        super(serializer);
    }



    @Override
    public V get(K key) {
        if(!(key instanceof String)) {
            return null;
        }
        if(StringUtil.isEmpty((String) key)) {
            return null;
        }
        final String k = (String) key;
        return (V) runInClient(client -> {
            String value = client.get(k);
            return deserialize(value);
        });
    }

    @Override
    public V getOrLoadAndSet(K key, SingleLoader<K, V> loader, long duration, TimeUnit timeUnit) {
        V v = get(key);
//        log.info("===>>> K:{} v:{}", key, new Gson().toJson(v));
        if(v != null) {
            return v;
        }
        v = loader.load(key);
        if(v != null) {
            set(key, v, duration, timeUnit);
        }
        return v;
    }

    @Override
    public V getSet(K key, V value) {
        return getSet(key, value, DEFAULT_DURATION, DEFAULT_TIME_UNIT);
    }

    private V doGetSet(K key, V value) {
        if(!(key instanceof String)) {
            return null;
        }
        if(StringUtil.isEmpty((String) key)) {
            return null;
        }
        final String k = (String) key;
        final String v = serialize(value);
        return (V) runInClient(client -> {
            String value1 = client.getSet(k, v);
            return deserialize(value1);
        });
    }

    @Override
    public V getSet(K key, V value, long duration, TimeUnit timeUnit) {
        V v = doGetSet(key, value);
        if(v != null) {
            expire(key, duration, timeUnit);
        }
        return v;
    }

    @Override
    public List<V> batchGet(K... keys) {
        List<V> result = new ArrayList<V>();
        Arrays.asList(keys).forEach(key -> result.add(get(key)));
        return result;
    }

    @Override
    public List<V> batchGetOrLoadAndSet(Collection<K> keys, SingleLoader<K, V> loader, long duration, TimeUnit timeUnit) {
        List<V> vs = new ArrayList<V>();
        Optional.ofNullable(keys).orElse(Collections.emptyList())
                .stream().forEach(key ->
                    vs.add(getOrLoadAndSet(key, loader, duration, timeUnit)));
        return vs;
    }

    @Override
    public void set(K key, V value) {
        set(key, value, DEFAULT_DURATION, DEFAULT_TIME_UNIT);
    }

    @Override
    public void set(K key, V value, long duration, TimeUnit timeUnit) {
        if(!(key instanceof String)) {
            return;
        }
        if(StringUtil.isEmpty((String) key)) {
            return;
        }
        final String k = (String) key;
        final String v = serialize(value);
        runInClient(client -> client.set(k, v));
        expire(key, duration, timeUnit);
    }

    @Override
    public Boolean setNx(K key, V value) {
        return setNx(key, value, DEFAULT_DURATION, DEFAULT_TIME_UNIT);
    }

    private Boolean doSetNx(K key, V value) {
        final String k = (String) key;
        final String v = serialize(value);
        return (Boolean) runInClient(client ->  (client.setnx(k, v)) == 1);
    }

    @Override
    public Boolean setNx(K key, V value, long duration, TimeUnit timeUnit) {
        Boolean result = doSetNx(key, value);
        if(result != null && result.booleanValue()) {
            expire(key, duration, timeUnit);
        }
        return result;
    }

    @Override
    public void setEx(K key, long seconds, V value) {
        final String k = (String) key;
        final String v = serialize(value);
        runInClient(client -> client.setex(k, (int)seconds, v));
    }

    @Override
    public void batchSet(Map<K, V> map) {
        batchSet(map, DEFAULT_DURATION, DEFAULT_TIME_UNIT);
    }

    @Override
    public void batchSet(Map<K, V> map, long duration, TimeUnit timeUnit) {
        map.entrySet().forEach(kvEntry -> set(kvEntry.getKey(), kvEntry.getValue(), duration, timeUnit));
    }

    @Override
    public void batchSetNx(Map<K, V> map) {
        batchSetNx(map, DEFAULT_DURATION, DEFAULT_TIME_UNIT);
    }

    @Override
    public void batchSetNx(Map<K, V> map, long duration, TimeUnit timeUnit) {
        map.entrySet().forEach(kvEntry -> setNx(kvEntry.getKey(), kvEntry.getValue(), duration, timeUnit));
    }
}
