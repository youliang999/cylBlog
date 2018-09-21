package com.cyl.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by youliang.cheng on 2018/9/14.
 */
@Data
public class VisitInfo2 implements Serializable {
    private static final long serialVersionUID = 1175950021455856344L;
    private Date visitDate;
    private int pv;
    private int uv;
}
