package com.hcxinan.core.inte.asset;

import com.hcxinan.core.inte.system.IUser;

import java.io.Serializable;
import java.util.Map;

/**
*@Description 资产接口。（系统资产）
*@Param
*@Return
*@Author liudk
*@DateTime 20-10-31 上午11:02
*/
public interface IAsset {
    Serializable getAssetId();//获取资产id

    String getAssetName();//获取资产名称

    String getAssetType();//获取资产类型

    String getBelongOrg();//获取资产所属单位

    String getStatus();//获取资产状态
    /*
    * 通过资产属性获取到值。考虑到资产属性太多，不可能把所有的属性穷尽，通过这个方法能获取到属性的值
    * */
    Object getVal(String attrName);
    //获取所有的动态属性值
    Map<String,Object> getAllAttrVal();
    //获取资产负责人
    IUser getCharger();
}
