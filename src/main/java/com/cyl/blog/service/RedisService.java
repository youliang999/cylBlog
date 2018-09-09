package com.cyl.blog.service;

import com.cyl.blog.entity.OperateLog;
import com.cyl.blog.entity.RedisInfoDetail;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/7.
 */
public interface RedisService {

    List<RedisInfoDetail> getRedisInfo();

    List<OperateLog> getLogs(long entries);

    Long getLogLen();

    String logEmpty();

    Map<String,Object> getKeysSize();

    Map<String,Object> getMemeryInfo();

}
