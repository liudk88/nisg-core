package com.hcxinan.core.inte.asset;

import com.morph.inte.query.IQuery;

import java.io.Serializable;

/**
 * @Author liudk by 2022/9/28 下午1:48
 * @description：资产查询对象接口
 *
 * @Param 
 *
 * @Throws
 *
 * @Return 
 */
public interface IAssetQuery extends IQuery<IAssetQuery,IAsset> {
    IAssetQuery assetId(Serializable assetId);

    IAssetQuery assetIdIn(Serializable... assetIds);

    IAssetQuery assetType(String assetType);

    IAssetQuery assetNameLike(String assetName);

    IAssetQuery ip(String ip);

    //查找包含于指定ip的ip段（非ip段的匹对用ip方法，本方法只用于单位ip段的计算）
    IAssetQuery containIp(String ip);

    IAssetQuery innerNet(String ip);//内网ip

    IAssetQuery outerNet(String ip);//外网ip

    IAssetQuery domain(String domain);

    IAssetQuery domainLike(String domain);

    //查询无效数据（逻辑删除的），默认所有查询都是有效的，可以通过设置查询无效数据
    IAssetQuery invalid();

    /*
    * 查询列表的时候使用，在查询列表时默认不显示所有的属性（为了性能考虑，如在与第三方工具配对时仅仅想做过滤，并不需要返回所有字段，当数据量比较大时，性能会有一定影响）
    * 而调用本方法，可以使得查询列表是带出所有动态属性
    * */
    IAssetQuery withDynaVal();
}
