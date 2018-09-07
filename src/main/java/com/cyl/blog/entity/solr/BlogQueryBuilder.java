package com.cyl.blog.entity.solr;

import com.dajie.common.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by youliang.cheng on 2018/8/21.
 */
public class BlogQueryBuilder {
    private static final String FQ_OR = " ";
    private static final String DEFAULT_QUERY = "*:*";
    private static final String iTemplate = "%s:%d";
    private static final String sTemplate = "%s:\"%s\"";
    private static final String aTemplate = "(%s)";
    private static final String openInterval = "%s:[%s TO %s]";

    private String query = DEFAULT_QUERY;

    private SolrQuery solrQuery = new SolrQuery();

    public BlogQueryBuilder withBids(List<Integer> bids) {
        fill(iTemplate, BlogIndex.FIELD_ID, bids);
        return this;
    }

    public BlogQueryBuilder withTitle(String title) {
        fill(sTemplate, BlogIndex.FIELD_TITLE, title);
        return this;
    }

    public BlogQueryBuilder withExcerpt(String excerpt) {
        fill(sTemplate, BlogIndex.FIELD_EXCERPT, excerpt);
        return this;
    }

    public BlogQueryBuilder withType(String type) {
        fill(sTemplate, BlogIndex.FIELD_TYPE, type);
        return this;
    }

    public BlogQueryBuilder withParent(String parent) {
        fill(sTemplate, BlogIndex.FIELD_PARENT, parent);
        return this;
    }

    public BlogQueryBuilder withCategoryid(String categoryid) {
        fill(sTemplate, BlogIndex.FIELD_CATEGORYID, categoryid);
        return this;
    }

    public BlogQueryBuilder withPstatus(int pstatus) {
        fill(iTemplate, BlogIndex.FIELD_PSTAUS, pstatus);
        return this;
    }

    public  BlogQueryBuilder withCstatus(int cstatus) {
        fill(iTemplate, BlogIndex.FIELD_CSTATUS, cstatus);
        return this;
    }

    public BlogQueryBuilder withCcount(int ccount) {
        fill(iTemplate, BlogIndex.FIELD_CCOUNT, ccount);
        return this;
    }

    public BlogQueryBuilder withRcount(int rcount) {
        fill(iTemplate, BlogIndex.FIELD_RCOUNT, rcount);
        return this;
    }

    public BlogQueryBuilder withCreator(String creator) {
        fill(sTemplate, BlogIndex.FIELD_CREATOR, creator);
        return this;
    }

    public BlogQueryBuilder withCreatetime(List<DateQueryRange> dateRanges) {
        if (CollectionUtils.isNotEmpty(dateRanges)) {
            for (DateQueryRange dateRange : dateRanges) {
                fillDate(dateRange.getType().getField(), dateRange.getStartDate(), dateRange.getEndDate());
            }
        }
        return this;
    }

    public BlogQueryBuilder withFacets(String[] facets) {
        if (ArrayUtils.isNotEmpty(facets)) {
            this.solrQuery.setFacet(true).addFacetField(facets);
        }
        return this;
    }

    public BlogQueryBuilder withStart(int start) {
        solrQuery.setStart(start);
        return this;
    }

    public BlogQueryBuilder withRows(int rows) {
        solrQuery.setRows(rows);
        return this;
    }

    private void fillDate(String filed, Date startDate, Date endDate) {
        Long start = getTime(startDate);
        Long end = getTime(endDate);
        if (start != null || end != null) {
            this.solrQuery.addFilterQuery(String.format(openInterval, filed, start != null ? start : "*", end != null ? end : "*"));
        }
    }

    private static Long getTime(Date date) {
        return null == date ? null : date.getTime();
    }

    private <T> void fill(String template, String field, T t) {
        if (null != t) {
            String formatQuery = String.format(template, field, t);
            if (t instanceof Integer && (Integer) t < 0) {
                formatQuery = formatNegativeQuery(formatQuery);
            }
            this.solrQuery.addFilterQuery(formatQuery);
        }
    }

    private <T> void fill(String template, String field, List<T> ts) {
        if (CollectionUtils.isNotEmpty(ts)) {
            List<String> conditions = new ArrayList<String>();
            for (T t : ts) {
                if (t == null || (t instanceof String && StringUtil.isEmpty((String) t))) {
                    continue;
                }
                String formatQuery = String.format(template, field, t);
                if (t instanceof Integer && (Integer) t < 0) {
                    formatQuery = formatNegativeQuery(formatQuery);
                }
                conditions.add(formatQuery);
            }
            if (CollectionUtils.isEmpty(conditions)) {
                return;
            }
            if (BlogIndex.FIELD_ID.equals(field)) {
                query = String.format(aTemplate, StringUtil.join(conditions, FQ_OR));
            } else {
                solrQuery.addFilterQuery(String.format(aTemplate, StringUtil.join(conditions, FQ_OR)));
            }
        }
    }

    private String formatNegativeQuery(String s) {
        Pattern pattern = Pattern.compile(":(-\\d+)");
        Matcher matcher = pattern.matcher(s);
        if (matcher.find()) {
            return s.replace(matcher.group(1), "\\" + matcher.group(1));
        }
        return s;
    }

    public SolrQuery build() {
        return solrQuery.setQuery(query);
    }

}
