package com.hcxinan.core.inte.util;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @author liudk
 * @Description:
 * @date 21-8-26 上午10:36
 */
public interface ITree<KEY, DATA> extends IDependency<ITree<KEY, DATA>>{
    KEY getId();

    KEY getPid();

    String getLabel();

    DATA getData();

    void setData(DATA data);

    //可以获取所有子孙节点的数据
    @JSONField(serialize=false)
    List<DATA> getDescendantsDatas();
}
