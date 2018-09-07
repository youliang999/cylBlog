package com.cyl.blog.entity.solr;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/8/21.
 */
@Data
public class DateQueryRange implements Serializable {
    private static final long serialVersionUID = 5698371445103523705L;
    private DateType type;
    private Date startDate;
    private Date endDate;

    public DateQueryRange(DateType type, Date startDate, Date endDate) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static enum DateType {
        CREATE_TIME("createTime"),
        LAST_UPDATE("lastUpdate"),
        UPDATE_DATE("update_date"),
        CREATE_DATE("create_date"),
        AUDIT_DATE("audit_date"),
        SCHEDULE_BEGIN("schedule_begin"),
        SCHEDULE_END("schedule_end");

        private String field;

        DateType(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }
    }
}
