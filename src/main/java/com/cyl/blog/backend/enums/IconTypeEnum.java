package com.cyl.blog.backend.enums;

/**
 * Created by youliang.cheng on 2018/10/10.
 */
public enum IconTypeEnum {
    CB100("CB_100", "&#xe616;"),
    CB200("CB_200", "&#xe613;"),
    CB300("CB_300", "&#xe620;"),
    CB400("CB_400", "&#xe60d;"),
    CB500("CB_500", "&#xe62d;"),
    CB600("CB_600", "&#xe61a;"),
    CB700("CB_700", "&#xe62e;"),
    CB800("CB_800", "&#xe62e;");

    IconTypeEnum(String mid, String icon) {
        this.mid = mid;
        this.icon = icon;
    }

    private String mid;
    private String icon;

    public static String getIcon(String mid) {
        for(IconTypeEnum iconTypeEnum : IconTypeEnum.values()) {
            if(iconTypeEnum.mid.equalsIgnoreCase(mid)) {
                return iconTypeEnum.icon;
            }
        }
        return "";
    }
}
