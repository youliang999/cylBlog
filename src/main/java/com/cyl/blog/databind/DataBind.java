package com.cyl.blog.databind;

/**
 * User:tao.li
 * Date: 11-11-24
 * Time: 下午12:18
 */
public interface DataBind<T> {
    void put(T t);

    T get();

    void remove();
}
