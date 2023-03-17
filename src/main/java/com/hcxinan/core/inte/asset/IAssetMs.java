package com.hcxinan.core.inte.asset;

import com.hcxinan.core.inte.system.IUser;


import java.util.List;
import java.util.Map;

/**
*@Description 作为资产模块对外开发的消息服务接口,也是系统中其他模块想调用资产模块的消息服务接口
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-1-8 下午9:40
*/
public interface IAssetMs {

    /**查询所有资产
     * @return
     * 以资产类型为key,资产数据集合为value的map。具体的资产类型编码有项目自己决定。
     */
    Map<String,List<Map>> queryAllAssets(String ... assetTypes);

    /**
     * @Author liudk by 2022/9/28 下午6:33
     * @description：构建资产查询
     *
     * @Param 
     *
     * @Throws
     *
     * @Return 
     */
    IAssetQuery createAssetQuery();
    
    /**
    *@Description 获取主机资产
    *@Param [params],查询参数
     * orgids (String[]) : 资产所属单位的ID,过滤单位
     * assetname: (String) 资产名称,支撑模糊查询
     * assetids: (String[]) 资产id
     * inner_ips (String[]) : 资产内网IP
     * outer_ips (String[]) : 资产外网IP
     * listSystem (String) : 是否同时查询出其关联的信息系统，1:是，其他情况不是
    *@Return java.util.List<IHost>
    *@Author liudk
    *@DateTime 21-1-8 下午9:42
    */
//    List<IHost> queryHostAsset(Map<String,Object> params);

    /**
     *@Description 获取网站|业务系统资产
     *@Param [params],查询参数
     * orgids (String[]) : 资产所属单位的ID,过滤单位
     * assetname: (String) 资产名称,支撑模糊查询
     * assetids: (String[])  资产名id
     * inner_ips (String[]) : 资产内网IP
     * outer_ips (String[]) : 资产外网IP
     * urls (String[]) : 网站地址
     *@Return java.util.List<IMsgSystem>
     *@Author liudk
     *@DateTime 21-1-8 下午9:55
     */
    List<IMsgSystem> queryMsgSysAsset(Map<String,Object> params);
    /**
     * @Author liudk by 2022/10/1 下午9:25
     * @description：保存资产,资产类型和保存的数据集合一一对应
     *
     * @Param datas:
     *
     * @Throws
     *
     * @Return
     */
    void saveAssets(Map<String,List<Map<String,Object>>> datas, IUser operator);

    default void saveAssets(Map<String,List<Map<String,Object>>> datas, List<Map<String,Object>> deptDatas,IUser operator){};

    /**
     * @Author liudk by 2022/10/1 下午8:55
     * @description：保存资产
     *
     * @Param assetType：需要保存的资产类型
     * @Param assets：需要保存的资产数据
     *
     * @Throws
     *
     * @Return
     */
    void saveAssets(String assetType, List<Map<String, Object>> assets, IUser operator);
}
