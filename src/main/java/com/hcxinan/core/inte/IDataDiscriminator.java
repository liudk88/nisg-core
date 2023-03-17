package com.hcxinan.core.inte;

import java.util.Collection;
import java.util.List;

/**
*@Description 数据区分器接口。
 * 泛型T：分析的对象是一个数据集合，T代表集合元素的数据类型
*@Param 
*@Return 
*@Author liudk
*@DateTime 20-10-29 下午9:39
*/
public interface IDataDiscriminator<T> {
    /**
    *@Description 设置需要区分的数据
    *@Param [datas]
    *@Return void
    *@Author liudk
    *@DateTime 20-10-29 下午10:38
    */
    default void analyseDatas(Collection<T> objDatas){
        for(T data:objDatas){
            if(isNew(data)){
                addAddedData(data);
            }else{
                addUpdatedData(data);
            }
        }
    }
    /**
    *@Description 判断一个数据是否是新增的
    *@Param [objData]
    *@Return boolean
    *@Author liudk
    *@DateTime 20-10-31 下午8:05
    */
    boolean isNew(T objData);
    /**
    *@Description 增加新增的数据
    *@Param [objData]
    *@Return void
    *@Author liudk
    *@DateTime 20-10-31 下午8:06
    */
    void addAddedData(T objData);
    /**
    *@Description 增加更新的数据
    *@Param [objData]
    *@Return void
    *@Author liudk
    *@DateTime 20-10-31 下午8:06
    */
    void addUpdatedData(T objData);

    /**
    *@Description 获取是新增的数据
    *@Param []
    *@Return java.util.List<T>
    *@Author liudk
    *@DateTime 20-10-31 下午8:06
    */
    List<T> getAddedDatas();
    /**
    *@Description 获取是更新的数据
    *@Param []
    *@Return java.util.List<T>
    *@Author liudk
    *@DateTime 20-10-31 下午8:12
    */
    List<T> getUpdatedDatas();
}
