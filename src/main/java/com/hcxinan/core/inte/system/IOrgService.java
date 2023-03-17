package com.hcxinan.core.inte.system;

import java.util.List;
import java.util.Map;

public interface IOrgService {
    //查询系统所有单位
    List<? extends IOrg> getAllOrg();
    /**
     * @Author liudk by 2022/6/7 上午8:34
     * @description：分页获取系统单位
     *
     * @Param pageSize：每页数据大小
     * @Param current：当前页
     *
     * @Throws
     *
     * @Return
     */
    List<Map> getOrgs(long pageSize,long current);
}
