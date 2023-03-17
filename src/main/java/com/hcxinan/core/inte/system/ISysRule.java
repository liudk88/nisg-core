package com.hcxinan.core.inte.system;

/**
 * @author liudk
 * @Description: 字典规则
 * @date 21-8-16 下午1:29
 */
public interface ISysRule {
    String getKey();//获取键

    String getVal();//获取值

    Integer getSeq();//获取排序号

    String getParams1();//获取备用参数1

    String getParams2();//获取备用参数2

    String getDes();//获取备注信息

    Boolean getValid();//是否有效
}
