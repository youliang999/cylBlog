package com.cyl.blog.service.jedis.util;

import manager.JedisManager;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Client;
import redis.clients.jedis.Jedis;
import redis.clients.util.Slowlog;

import java.util.List;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Component
public class RedisUtil {
    private static Jedis jedisClient = JedisManager.instance.getJedis();

    // 获取redis 服务器信息
    public String getRedisInfo() {

        try {
            Client client = jedisClient.getClient();
            client.info();
            String info = client.getBulkReply();
            return info;
        } finally {
            if(jedisClient != null) {
                // 返还到连接池
              //  jedisClient.close();
            }
        }
    }

    // 获取日志列表
    public List<Slowlog> getLogs(long entries) {
        try {
            List<Slowlog> logList = jedisClient.slowlogGet(entries);
            return logList;
        } finally {
            // 返还到连接池
            if(jedisClient != null) {
                // 返还到连接池
                //jedisClient.close();
            }
        }
    }

    // 获取日志条数
    public Long getLogsLen() {
        try {
            long logLen = jedisClient.slowlogLen();
            return logLen;
        } finally {
            // 返还到连接池
            if(jedisClient != null) {
                // 返还到连接池
              //  jedisClient.close();
            }
        }
    }

    // 清空日志
    public String logEmpty() {
        try {
            return jedisClient.slowlogReset();
        } finally {
            // 返还到连接池
            if(jedisClient != null) {
                // 返还到连接池
             //   jedisClient.close();
            }
        }
    }

    // 获取占用内存大小
    public Long dbSize() {
        try {
            // TODO 配置redis服务信息
            Client client = jedisClient.getClient();
            client.dbSize();
            return client.getIntegerReply();
        } finally {
            // 返还到连接池
            if(jedisClient != null) {
                // 返还到连接池
              //  jedisClient.close();
            }
        }
    }
}
