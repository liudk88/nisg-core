package com.hcxinan.core.inte;

import java.util.Map;
/**
*@Description 数据转换器
*@Param 
*@Return 
*@Author liudk
*@DateTime 20-11-1 下午6:36
*/
public interface IDataConvertor<SOURCE, DIST> {
    //转换对象
    DIST convert(SOURCE source);

    //转换对象,并记录痕迹
    default DIST convertWithMark(SOURCE source){
        DIST dist = convert(source);
        recordMark(source,dist);//记录痕迹
        return dist;
    };
    //记录痕迹。每转换一个对象都会留有痕迹
    default void recordMark(SOURCE source, DIST dist){};
    //获取痕迹
    default Map<String,Object> getMark(SOURCE source){
        return null;
    };

}
