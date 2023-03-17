package com.hcxinan.core.inte.system;

import java.util.Map;

/**
 * @author liudk
 * @Description: 调度接口
 * @date 21-9-19 下午4:46
 */
public interface ISchedule {

    void run(Map<String, String> params);
}
