package com.hcxinan.core.inte.system;

import com.morph.cond.Condition;

import java.util.List;
import java.util.Map;

/**
 * @author liudk
 * @Description:
 * @date 21-9-24 下午7:05
 */
public interface IUserService {
    List<? extends IUser> queryUsers(Condition userCond,Condition deptCond);

    List<? extends IUser> queryUsers(Condition condition);
    //通过用户id获取用户
    List<IUser> getUsersById(String... userId);
    //通过用户账号获取用户
    List<IUser> getUsersByAccount(String... account);

    List<IUser> getUsersByRoleId(String... roleId);

    List<IUser> getUsersByOrgId(String... orgId);

    void updateUser(Map<String,Object> updateUser);
}
