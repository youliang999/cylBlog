package com.cyl.blog.databind;

import java.util.concurrent.ConcurrentHashMap;

/**
 * User:tao.li
 * Date: 11-11-24
 * Time: 下午1:36
 */
public final class DataBindManager {
    private static final DataBindManager INSTANCE = new DataBindManager();

    private final ConcurrentHashMap<Enum, DataBind> map = new ConcurrentHashMap<Enum, DataBind>();

    public static DataBindManager getInstance() {
        return INSTANCE;
    }

    private DataBindManager() {
    }

    public <T> DataBind<T> getDataBind(Enum bindType) {
        DataBind<T> dataBind = map.get(bindType);
        if (dataBind == null) {
            DataBind bind = new ThreadLocalDataBind();
            dataBind = map.putIfAbsent(bindType, bind);
            if (dataBind == null) {
                dataBind = bind;
            }
        }
        return dataBind;
    }


    public enum DataBindType {
        LOGIN_USER,
        GLOBAL
    }
}
