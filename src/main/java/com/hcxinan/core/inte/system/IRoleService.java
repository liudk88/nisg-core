package com.hcxinan.core.inte.system;

import java.util.List;

/**
 * @author liudk
 * @Description: 角色接口
 * @date 21-9-24 下午5:38
 */
public interface IRoleService {
    List<IRole> getRolesById(String... roleId);
}
