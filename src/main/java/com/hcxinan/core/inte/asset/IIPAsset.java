package com.hcxinan.core.inte.asset;

import java.util.List;
/**
*@Description IP资产
*@Param 
*@Return 
*@Author liudk
*@DateTime 20-10-31 上午11:01
*/
public interface IIPAsset extends IAsset {

    List<String> getInnerIps();//获取内网ip

    List<String> getOuterIps();//获取外网ip
}
