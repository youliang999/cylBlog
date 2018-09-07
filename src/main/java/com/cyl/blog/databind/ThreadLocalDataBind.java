package com.cyl.blog.databind;

/**
 * User:tao.li
 * Date: 11-11-24
 * Time: 下午12:17
 */
public class ThreadLocalDataBind<T> implements DataBind<T> {
    private final ThreadLocal<T> threadLocal = new ThreadLocal<T>();

    @Override
    public void put(T t) {
        threadLocal.set(t);
    }

    @Override
    public T get() {
        return threadLocal.get();
    }

    @Override
    public void remove() {
        threadLocal.remove();
    }
}
