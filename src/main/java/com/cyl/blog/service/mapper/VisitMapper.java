package com.cyl.blog.service.mapper;

import com.cyl.blog.entity.VisitInfo;
import com.cyl.blog.entity.VisitInfo2;

import java.util.List;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/14.
 */
public interface VisitMapper {

    int insertVisitRecord(VisitInfo visitInfo);

    int countPvByDate(Map<String, Object> data);

    int countUvByDate(Map<String, Object> data);

    int getPvByDate(String searchDate);

    int getUvByDate(String searchDate);

    int countVisitInfo(Map<String, Object> data);

    List<VisitInfo> getVisitInfo(Map<String, Object> data);

    VisitInfo2 getVisitInfo2(String date);

}
