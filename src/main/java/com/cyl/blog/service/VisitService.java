package com.cyl.blog.service;

import com.cyl.blog.entity.VisitInfo;
import com.cyl.blog.entity.VisitInfo2;
import com.cyl.blog.plugin.PageIterator;

import java.util.Date;

/**
 * Created by youliang.cheng on 2018/9/14.
 */
public interface VisitService {

    boolean insertVisitRecord(VisitInfo visitInfo);

    int countPvByDate(Date date);

    int countUvByDate(Date date);

    int countLastDayPv();

    int countLastDayUv();

    int getPvByDate(Date date);

    int getUvByDate(Date date);

    PageIterator<VisitInfo> getVisitInfo(Date date, int page, int pageSize);

    VisitInfo2 getVisitInfo2(Date date);
}
