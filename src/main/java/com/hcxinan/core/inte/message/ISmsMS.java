package com.hcxinan.core.inte.message;

import java.util.List;
import java.util.stream.Collectors;

/**
*@Description 短信发送实现的统一接口
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-3-18 下午5:30
*/
public interface ISmsMS<T extends ISms> {
    default boolean sendSmsByEntity(Object entity){
        return sendSms(getSms(entity));
    }

    default int batchSendSmsByEntitys(List<Object> entitys){
        List<ISms> smss = entitys.stream().map(o->getSms(o)).collect(Collectors.toList());
        return batchSendSms(smss);
    }

    /**
    *@Description 发送单条短信
    *@Param [sms]
    *@Return boolean
    *@Author liudk
    *@DateTime 21-3-18 下午5:37
    */
    boolean sendSms(ISms sms);
    /**
    *@Description 批量发送短信
    *@Param [smss]
    *@Return int
    *@Author liudk
    *@DateTime 21-3-18 下午5:38
    */
    int batchSendSms(List<ISms> smss);
    /**
    *@Description 获取短信实现类
    *@Param [entity]
    *@Return T
    *@Author liudk
    *@DateTime 21-3-18 下午5:57
    */
    T getSms(Object entity);
}
