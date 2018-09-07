package com.cyl.blog.databind;


import com.cyl.blog.entity.User;

/**
 * Created by youliang.cheng on 2018/1/24.
 */
public final class SessionContext {
    private final ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();
    private static SessionContext instance = new SessionContext();

    private SessionContext() {
    }

    public static SessionContext getInstance() {
        return instance;
    }

    /**
     * 将登陆信息绑定到当前线程中
     *
     * @param user
     */
    public void bind(User user) {
        userThreadLocal.set(user);
    }

    /**
     * 得到绑定的登陆信息
     *
     * @return
     */
    public User get() {
        return userThreadLocal.get();
    }

    /**
     * 移除绑定的登陆信息
     */
    public void remove() {
        userThreadLocal.remove();
    }
}
