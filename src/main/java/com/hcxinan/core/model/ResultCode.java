package com.hcxinan.core.model;

/**
 * @Title: 操作码
 * @Author: Fly
 * @Date: 2020/7/24 - 9:04
 * @Description:
 */
public enum ResultCode {
    SUCCESS(true,200,"操作成功！"),

    //---系统错误返回码-----
    FAIL(false,401,"操作失败"),
    UNAUTHENTICATED(false,10002,"您还未登录"),
    UNAUTHORISE(false,10003,"权限不足"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),

    NULL_POINTER(false,40010,"参数空指针异常！"),
    ILLEGAL_ARGUMENT(false,40011,"参数非法异常！");


    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResultCode setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
