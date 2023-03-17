package com.hcxinan.core.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message<T> implements Serializable {
    private static final long serialVersionUID = 5539037796986448296L;

    private ResultCode code;

    private T data;//绑定的数据

    public Message(){}

    public Message(ResultCode code) {
        this.code = code;
    }

    public Message(ResultCode code, T data) {
        this.code = code;
        this.data = data;
    }

    public static Message success(){
        return new Message(ResultCode.SUCCESS);
    }

    public static <T> Message success(T data){
        return new Message(ResultCode.SUCCESS,data);
    }

    public static <T> Message success(T data,String message){
        return new Message(ResultCode.SUCCESS.setMessage(message),data);
    }

    public static Message fail(){
        return new Message(ResultCode.FAIL);
    }

    public static Message error(){
        return new Message(ResultCode.SERVER_ERROR);
    }

    public Message addMessage(String addedMessage){
        if(code.message==null){
            code.message=addedMessage;
        }else{
            code.message+=addedMessage;
        }
        return this;
    }
}
