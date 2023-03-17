package com.hcxinan.core.inte.message;

import java.util.List;

/**发送短信接口
 * @author liudk
 * @Description:
 * @date 21-9-8 下午3:55
 */
public interface ISmsSender {
    /**
     *@Description 给多个手机号发送短信
     *@Param [phones：多个手机号, contents：发送的短信内容]
     *@Return boolean：是否发送成功
     *@Author liudk
     *@DateTime 21-9-8 下午5:20
    */
    boolean sendMessage(List<String> phones, String contents);
}
