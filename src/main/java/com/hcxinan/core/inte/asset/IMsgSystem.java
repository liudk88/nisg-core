package com.hcxinan.core.inte.asset;

import java.util.List;
/**
*@Description 信息系统资产接口
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-1-28 下午2:46
*/
public interface IMsgSystem extends IIPAsset{
    //获取网站访问地址
    List<String> getWebSites();//获取网站地址
}
