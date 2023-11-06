package com.tpls.constant;

import lombok.Getter;


@Getter
public enum ResultEnum {
    SUCCESS(200,"请求成功"),
    FAIL(-1,"失败"),

    ;

    private int code;
    private String msg;

    //    enum构造方法不能是public的
    private ResultEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
