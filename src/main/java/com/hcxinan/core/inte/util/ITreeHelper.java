package com.hcxinan.core.inte.util;

/**
 * @author liudk
 * @Description:
 * @date 21-8-25 下午5:47
 */
@FunctionalInterface
public interface ITreeHelper<KEY, DATA> {
    /**
     *@Description 通过需要绑定的数据，如何构造出一颗树节点（需要指定节点的id，节点父id，节点名称）
     *@Param [data]
     *@Return com.szelink.safesystem.quota.engine.util.Tree<E,T>
     *@Author liudk
     *@DateTime 20-3-24 下午3:46
     */
    ITree<KEY, DATA> getTree(DATA data);
}
