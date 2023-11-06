package com.tpls.common;

import com.tpls.constant.ResultEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private boolean success;
    private int  code;
    private  String msg;
    private T res;

    public Result<T> setSuc(T res){
        this.setSuccess(true);
        this.setCode(ResultEnum.SUCCESS.getCode());
        this.setMsg(ResultEnum.SUCCESS.getMsg());
        this.setRes(res);
        return this;

    }
    public Result setSuc(){
        // this.setSuccuss(true);
        // this.setCode(ResultEnum.SUCCESS.getCode());
        // this.setMsg(ResultEnum.SUCCESS.getMsg());
        // this.setRes(null);
        return setSuc(null);
    }

    public Result setFail(ResultEnum resultEnum){
        return setFail(resultEnum.getCode(),resultEnum.getMsg());
    }

    public Result setFail(String msg){
        this.setSuccess(false);
        this.setCode(-1);
        this.setMsg(msg);
        this.setRes(null);
        return this;
    }
    public Result setFail(int code, String msg){
        this.setSuccess(false);
        this.setCode(code);
        this.setMsg(msg);
        this.setRes(null);
        return this;
    }
}
