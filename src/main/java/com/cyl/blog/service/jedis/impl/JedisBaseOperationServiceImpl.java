package com.cyl.blog.service.jedis.impl;

import com.cyl.blog.service.jedis.RedisOperation;
import manager.JedisManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public class JedisBaseOperationServiceImpl<K> implements RedisOperation {
    private static final Logger log = LoggerFactory.getLogger(JedisBaseOperationServiceImpl.class);
//    private final String name;
    private final Jedis jedisClient = JedisManager.instance.getJedis();

    @Override
    public String getName() {
        return this.jedisClient.clientGetname();
    }

    protected <R> R runInClient(JedisClientCallback<R> clientCallback){
        try {
            return clientCallback.doInClient(jedisClient);
        } catch (RuntimeException e) {
            log.error("failed to run {}.", clientCallback);
            throw e;
        }
    }

    interface JedisClientCallback<R> {
        public R doInClient(Jedis client);
    }
}
