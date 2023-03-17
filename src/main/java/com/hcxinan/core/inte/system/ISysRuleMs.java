package com.hcxinan.core.inte.system;

import java.util.List;
import java.util.Map;

/**
 * @author liudk
 * @Description: 系统字典消息服务接口
 * @date 21-8-16 下午1:26
 */
public interface ISysRuleMs {
    List<ISysRule> getRules(String ruleName);//通过规则名称获取规则字典

    Map<String,? extends ISysRule> getRuleMaps(String ruleName);//通过规则名称获取规则字典

    ISysRule getRule(String ruleName,String key);//通过规则名称和键获取规则
    /*
    * 重载指定规则,如果ruleName为空则重载所有
    * */
    void reload(String ruleName);
}
