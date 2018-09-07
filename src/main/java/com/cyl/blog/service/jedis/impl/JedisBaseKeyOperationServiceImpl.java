package com.cyl.blog.service.jedis.impl;

import com.cyl.blog.service.jedis.KeyOperation;
import com.dajie.common.util.StringUtil;
import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by youliang.cheng on 2018/8/1.
 */
public class JedisBaseKeyOperationServiceImpl<K> extends JedisBaseOperationServiceImpl implements KeyOperation<K> {
    @Override
    public Boolean exists(K key) {
        if(!(key instanceof String)) {
            return false;
        }
        if(StringUtil.isEmpty((String) key)) {
            return false;
        }
        final String k = (String) key;
        return (Boolean) runInClient(new JedisClientCallback() {
            @Override
            public Boolean doInClient(Jedis client) {
                return client.exists(k);
            }
        });
    }

    @Override
    public Long del(K... keys) {
        final long[] result = {0};
        Arrays.asList(keys).forEach(key -> {
            if(!(key instanceof String)) {
                return;
            }
            if(StringUtil.isEmpty((String) key)) {
                return;
            }
            String k = (String) key;
            result[0] += (Long) runInClient(new JedisClientCallback() {
                @Override
                public Long doInClient(Jedis client) {
                    return client.del(k);
                }
            });
        });
        return result[0];
    }

    @Override
    public Set<String> keys(String pattern) {
        return null;
    }

    @Override
    public String randomKey() {
        return null;
    }

    @Override
    public void rename(K oldName, K newName) {

    }

    @Override
    public Boolean renameNX(K oldName, K newName) {
        return null;
    }

    @Override
    public Boolean expire(K key, long seconds) {
        if(!(key instanceof String)) {
            return false;
        }
        if(StringUtil.isEmpty((String) key)) {
            return false;
        }
        final String k = (String) key;
        return (Boolean) runInClient(new JedisClientCallback() {
            @Override
            public Object doInClient(Jedis client) {
                return client.expire(k, (int)seconds) == 1;
            }
        });
    }

    @Override
    public Boolean expire(K key, long duration, TimeUnit timeUnit) {
        return expire(key, timeUnit.toSeconds(duration));
    }

    @Override
    public Boolean expireAt(K key, long unixTime) {
        if(!(key instanceof String)) {
            return false;
        }
        if(StringUtil.isEmpty((String) key)) {
            return false;
        }
        final String k = (String) key;
        return (Boolean) runInClient(new JedisClientCallback() {
            @Override
            public Object doInClient(Jedis client) {
                return client.expireAt(k, unixTime) == 1;
            }
        });
    }

    @Override
    public Boolean persist(K key) {
        if(!(key instanceof String)) {
            return false;
        }
        if(StringUtil.isEmpty((String) key)) {
            return false;
        }
        final String k = (String) key;
        return (Boolean) runInClient(new JedisClientCallback() {
            @Override
            public Object doInClient(Jedis client) {
                return client.persist(k);
            }
        });
    }

    @Override
    public Boolean move(K key, int dbIndex) {
        return false;
    }

    @Override
    public Long ttl(K key) {
        if(!(key instanceof String)) {
            return null;
        }
        if(StringUtil.isEmpty((String) key)) {
            return null;
        }
        final String k = (String) key;
        return (Long) runInClient(new JedisClientCallback() {
            @Override
            public Object doInClient(Jedis client) {
                return client.ttl(k);
            }
        });
    }
}
