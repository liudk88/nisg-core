package com.hcxinan.core.inte.entity;

import com.hcxinan.core.inte.system.IUser;

/**
 * @author liudk
 * @Description:
 * @date 21-7-27 下午8:02
 */
public class ToDoSource<T> {
    //发送代办的用户
    private IUser user;
    //发送实体
    private T entity;

    public ToDoSource(IUser user, T entity) {
        this.user = user;
        this.entity = entity;
    }

    public IUser getUser() {
        return user;
    }

    public void setUser(IUser user) {
        this.user = user;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
