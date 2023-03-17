package com.hcxinan.core.inte.system;

import java.io.Serializable;
import java.util.List;

/**
 * @author liudk
 * @Description: 用户接口
 * @date 21-7-27 下午7:34
 */
public interface IUser extends Serializable {
    //获取用户id（数据库主键）
    String getId();
    //获取账号
    String getAccount();
    //获取名字
    String getName();
    //获取联系手机
    List<String> getTels();
    //获取邮箱
    String getEmail();
    //获取单位id
    String getOrgId();
    //获取用户所在单位
    IOrg getOrg();
    //获取用户的角色
    List<IRole> getRoles();
    //获取一些扩展字段
    Object getParam(String key);

    void setRoles(List<IRole> roles);
}
