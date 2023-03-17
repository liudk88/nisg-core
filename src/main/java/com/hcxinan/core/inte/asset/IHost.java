package com.hcxinan.core.inte.asset;

import java.util.List;

/**
*@Description 主机资产接口
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-1-28 下午2:46
*/
public interface IHost extends IIPAsset{
    //获取操作系统
    IOsystem getOs();

    //获取关联信息系统
    List<IMsgSystem> getRefMsgSystem();
}
