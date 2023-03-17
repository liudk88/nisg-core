package com.hcxinan.core.inte.system;

import java.util.List;
import java.util.Map;

/**
 * 数据权限控制接口.
 * 以下关于权限结果值通过1、2、4位运算得到，对应读、写、执行
 * @Author liudk by 2022/1/19 下午2:31
 */
public interface IDataPowerService {

    /**
     * 判断是否拥有所有权限，建议先用这个方法判断是否有所有权限，当不是的时候，才在获取用户或单位权限
     * @Author liudk by 2022/1/19 下午8:57
     *
     * @Param powerUser：需要查询的权限用户
     * @Param module：模块id
     *
     * @Return
     */
    boolean hasAllPower(IUser powerUser,String module);
    /**
     * 获取当前用户在指定模块下的能控制的用户范围
     * @Author liudk by 2022/1/19 下午2:35
     *
     * @Param powerUser：需要查询的权限用户
     * @Param module：模块id
     *
     * @Return: 所能控制的所有权限用户及其对应的权限值
     */
    Map<IUser,Integer> getUserScope(IUser powerUser,String module);

    /**
     * 获取当前用户在指定模块下的能控制的单位范围
     * @Author liudk by 2022/1/19 下午2:37
     *
     * @Param powerUser：需要查询的权限用户
     * @Param module：模块id
     *
     * @Return: 所能控制的所有权限单位及其对应的权限值
     */
    Map<IOrg,Integer> getOrgScope(IUser powerUser, String module);
    
    /**
     * 获取当前用户在指定模块下能控制的所属区范围
     * @Author liudk by 2022/1/27 下午4:24
     *
     * @Param powerUser：需要查询的权限用户
     * @Param module：模块id
     *
     * @Return :每个区的权限控制结果
     */
    Map<String,Integer> getRegionScope(IUser powerUser, String module);
}
