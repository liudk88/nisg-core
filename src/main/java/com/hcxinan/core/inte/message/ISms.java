package com.hcxinan.core.inte.message;

/**
*@Description 短信消息的实体接口
*@Param 
*@Return 
*@Author liudk
*@DateTime 21-3-18 下午5:36
*/
public interface ISms {
    //获取发送人
    String getSender();
    //获取发送的手机号码
    String getPhones();
    //获取短信内容
    String getSmsContent();
}
