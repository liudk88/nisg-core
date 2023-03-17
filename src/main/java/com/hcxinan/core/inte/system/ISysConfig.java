package com.hcxinan.core.inte.system;
/**
 * 系统配置信息
 * @Author liudk by 2022/1/15 下午6:27
 */
public interface ISysConfig {
    //是否开启自动更新数据库
    default boolean enableAutoUpdateDb(){
        return true;
    }
    //获取系统名称
    String getSystemName();
    //获取平台logo
    String getSystemLogo();
    //获取当前系统的数据库类型
    String getDbType();
    //获取是否离线端初始化标识
    String getOfflineInit();
    //判断是否开启验证码
    boolean isOpenCaptcha();
    // 布局风格,0默认,1顶部菜单风格,2混合菜单风格
    String getLayoutStyle();
    /**
     * @Author liudk by 2022/12/5 下午4:02
     * @description：获取用户的待办任务信息，
     * 当前只是一个简化版本，适用于住建项目，待办任务就是运维流程管理里面的待办任务，
     * 未来应该考虑是多模块的
     * 由于目前未能适配所有项目，默认返回-1,表示不启动待办提醒，如果要提醒，请覆盖实现
     * @Param
     *
     * @Throws
     *
     * @Return
     */
    default int getTaskNum(){return -1;};
}
