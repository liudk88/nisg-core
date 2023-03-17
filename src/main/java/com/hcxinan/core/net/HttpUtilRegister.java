package com.hcxinan.core.net;
/**使用HttpUtil工具的注册接口规范，一般来说，一款对接工具有些信息都是唯一的，所以只要注册一次，采用这种设计方式的好处一是避免了在
 * 每个方法中都重新设置或作为参数传入这些信息
*@Description
*@Author liudk
*@Date 19-9-2
*@Time 下午4:35
*/
public interface HttpUtilRegister {
    /**设置访问地址的域名，相当于协议+ip+端口+服务名，如http://192.168.22.1:8080/web
    *@Description
    *@Return String:返回域名
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午4:37
    */
    String getDomain();
    /**是否打开https，使用安全协议
    *@Description
    *@Return boolean，true:打开；false：不打开
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午4:39
    */
    boolean isOpenSSL();
}
