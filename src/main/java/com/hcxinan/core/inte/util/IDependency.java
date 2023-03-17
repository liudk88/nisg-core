package com.hcxinan.core.inte.util;

import java.util.List;

/**
 * @author liudk
 * @Description:依赖定义接口
 * @date 21-8-25 下午5:50
 */
public interface IDependency<T> {
    default void setParent(T parent){};

    /**
     *@Description 获取父,不采用get开头是因为前后端分离后作为对象返回时，容易出现
     * 父展示子，子展示父这样的死循环，在controler里返回会报错。
     * （当然也可以用@JSONField注解，先转为json,再转化为对象再输出，但这样就操作复杂了）
     *@Param []
     *@Return com.form.engine.inte.IComponent
     *@Author liudk
     *@DateTime 21-7-13 上午11:05
     */
    default T parent(){
        return null;
    };

    default IDependency addChild(T child){
        return null;
    };

    default List<? extends T> getChildren(){
        return null;
    }
}
