package com.cyl.blog.service.impl;

import com.cyl.blog.entity.VisitInfo;
import com.cyl.blog.entity.VisitInfo2;
import com.cyl.blog.plugin.PageIterator;
import com.cyl.blog.service.VisitService;
import com.cyl.blog.service.mapper.VisitMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by youliang.cheng on 2018/9/14.
 */
@Service("visitService")
public class VisitServiceImpl implements VisitService {
    private static final Logger log = LoggerFactory.getLogger(VisitServiceImpl.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private VisitMapper visitMapper;

    @Override
    public boolean insertVisitRecord(VisitInfo visitInfo) {
        return visitMapper.insertVisitRecord(visitInfo) > 0;
    }

    @Override
    public int countPvByDate(Date date) {
        Map<String, Object> data = new HashMap<>();
        data.put("startDate", getStartDate(date));
        data.put("endDate", getEndDate(date));
        return visitMapper.countPvByDate(data);
    }

    @Override
    public int countUvByDate(Date date) {
        Map<String, Object> data = new HashMap<>();
        data.put("startDate", getStartDate(date));
        data.put("endDate", getEndDate(date));
        return visitMapper.countUvByDate(data);
    }

    @Override
    public int countLastDayPv() {
        Map<String, Object> data = new HashMap<>();
        data.put("startDate", getLastDayStartDate());
        data.put("endDate", getLastDayEndDate());
        return visitMapper.countPvByDate(data);
    }

    @Override
    public int countLastDayUv() {
        Map<String, Object> data = new HashMap<>();
        data.put("startDate", getLastDayStartDate());
        data.put("endDate", getLastDayEndDate());
        return visitMapper.countUvByDate(data);
    }

    @Override
    public int getPvByDate(Date date) {
        String searchDate = dateFormat.format(date);
        return visitMapper.getPvByDate(searchDate);
    }

    @Override
    public int getUvByDate(Date date) {
        String searchDate = dateFormat.format(date);
        return visitMapper.getUvByDate(searchDate);
    }

    @Override
    public PageIterator<VisitInfo> getVisitInfo(Date date, int page, int pageSize) {
        if(page <= 0 || pageSize <= 0) {
            page  = 1;
            pageSize = 20;
        }
        Map<String, Object> data = new HashMap<>();
        data.put("startDate", getStartDate(date));
        data.put("endDate", getEndDate(date));
        data.put("offset", (page-1)*pageSize);
        data.put("limit", pageSize);
        int count = visitMapper.countVisitInfo(data);
        PageIterator<VisitInfo> pages = PageIterator.createInstance(page, pageSize, count);
        pages.setData(visitMapper.getVisitInfo(data));
        return pages;
    }

    @Override
    public VisitInfo2 getVisitInfo2(Date date) {
        String searchDate = dateFormat.format(date);
        return visitMapper.getVisitInfo2(searchDate);
    }

    private String getStartDate(Date date) {
        return new DateTime(date).withTime(0, 0, 0, 0).toString("yyyy-MM-dd HH:mm:ss");
    }

    private String getEndDate(Date date) {
        return new DateTime(date).withTime(23, 59, 59, 0).toString("yyyy-MM-dd HH:mm:ss");
    }

    private String getLastDayStartDate() {
        Date now = Calendar.getInstance().getTime();
        return new DateTime(now).plusDays(-1).withTime(0, 0, 0, 0).toString("yyyy-MM-dd HH:mm:ss");
    }

    private String getLastDayEndDate() {
        Date now = Calendar.getInstance().getTime();
        return new DateTime(now).plusDays(-1).withTime(23, 59, 59, 0).toString("yyyy-MM-dd HH:mm:ss");
    }
}
