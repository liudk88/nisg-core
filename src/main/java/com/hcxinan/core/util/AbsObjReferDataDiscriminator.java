package com.hcxinan.core.util;

/**
*@Description 具有参照物的数据划分类
 * 泛型T：分析的对象是一个数据集合，T代表集合元素的数据类型
 * 泛型REF:参照物对象类型，通过分析集合每个元素，对比参照物来判断是新增还是更新
*@Param
*@Return 
*@Author liudk
*@DateTime 20-10-30 上午8:28
*/
public abstract class AbsObjReferDataDiscriminator<T, REF> extends AbsDataDiscriminator<T> {

    protected REF referObj;//参照物，用来判断数据是否已经存在
    /**
     *@Description 对比参照物，分析数据是否是新增的
     *@Param [data, referObj]
     *@Return boolean
     *@Author liudk
     *@DateTime 20-10-30 上午9:33
     */
    abstract protected boolean isNew(T data, REF referObj);

    @Override
    public boolean isNew(T data) {
        //转移到通过参照物来分析结果
        return isNew(data,referObj);
    }

    public REF getReferObj() {
        return referObj;
    }

    public void setReferObj(REF referObj) {
        this.referObj = referObj;
    }
}
