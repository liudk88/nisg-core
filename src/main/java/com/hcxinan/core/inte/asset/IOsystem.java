package com.hcxinan.core.inte.asset;

import java.util.List;
import java.util.Map;

/**
 * @author liudk
 * @Description: 操作系统
 * @date 21-8-9 下午7:43
 */
public interface IOsystem extends IAsset{
    String getOsname();//获取操作系统名称

    String getVersion();//获取操作系统版本

    Boolean getDomesti();//是否国产

    Map<String,String> getVersions();//获取版本信息，以键值对的方式展示（考虑到版本可能会有编号对应名字的情况）

    List<String> getVsersionNames();//获取所有版本名称
}
