package com.hcxinan.core.util;

import java.io.Serializable;

/**
 * @author liudk
 * @Description: 控制层返回前端的json格式规范
 * @date 21-7-10 下午6:04
 */
public class JsonResult<T> implements Serializable {
    /**
     * 成功
     */
    public static final int SUCCESS = 0;
    /**
     * 失败
     */
    public static final int ERROR = 500;

    private int code;

    private String msg;

    private T data;

    public static <T> JsonResult<T> success() {
        return jsonResult(null, SUCCESS, "操作成功");
    }

    public static <T> JsonResult<T> success(T data) {
        return jsonResult(data, SUCCESS, "操作成功");
    }

    public static <T> JsonResult<T> success(T data, String msg) {
        return jsonResult(data, SUCCESS, msg);
    }

    public static <T> JsonResult<T> error() {
        return jsonResult(null, ERROR, "操作失败");
    }

    public static <T> JsonResult<T> error(String msg) {
        return jsonResult(null, ERROR, msg);
    }

    public static <T> JsonResult<T> error(T data) {
        return jsonResult(data, ERROR, "操作失败");
    }

    public static <T> JsonResult<T> error(T data, String msg) {
        return jsonResult(data, ERROR, msg);
    }

    public static <T> JsonResult<T> error(int code, String msg) {
        return jsonResult(null, code, msg);
    }

    private static <T> JsonResult<T> jsonResult(T data, int code, String msg) {
        JsonResult<T> result = new JsonResult<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
