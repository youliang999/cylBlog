package com.cyl.blog.backend.helper;

import com.cyl.blog.entity.OperateLog;
import com.cyl.blog.entity.RedisInfoDetail;
import com.cyl.blog.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
@Component
public class RedisHelper {

    @Autowired
    private RedisService redisService;

    public List<RedisInfoDetail> getClientInfo() {
        return redisService.getRedisInfo();
    }

    public List<OperateLog> getLogs() {
        return redisService.getLogs(100L);
    }

    public long getLogLen() {
        return redisService.getLogLen();
    }

    public String logEmpty() {
        return redisService.logEmpty();
    }

    public Map<String,Object> getKeysSize() {
        return redisService.getKeysSize();
    }

    public Map<String,Object> getMemeryInfo() {
        return redisService.getMemeryInfo();
    }
}
