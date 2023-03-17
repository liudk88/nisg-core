package com.hcxinan.core.util;

import java.util.Collection;

/**
*@Description 集合参照物的数据区分器
 * 泛型T：分析的对象是一个数据集合，T代表集合元素的数据类型
 * 泛型K:代表参照物（是个集合）里每个元素的类型
*@Param
*@Return
*@Author liudk
*@DateTime 20-11-1 上午9:55
*/
public abstract class AbsCollectReferDataDiscriminator<T, REF> extends AbsObjReferDataDiscriminator<T, Collection<REF>>{
    /**
    *@Description 判断模板集合中的元素和参照物中的元素是否相同
    *@Param [data, subReferObj]
    *@Return boolean
    *@Author liudk
    *@DateTime 20-11-1 上午10:23
    */
    protected abstract boolean isSame(T objData, REF subReferObj);

    @Override
    protected boolean isNew(T objData, Collection<REF> referObj) {
        if(referObj==null ||referObj.size()==0){
            return true;
        }
        for(REF subRefer:referObj){
            if(isSame(objData,subRefer)){//找到了，那么不是新增，返回false
                return false;
            }
        }
        return true;
    }
}
