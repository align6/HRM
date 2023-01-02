package com.ihrm.common.exception;

import com.ihrm.common.entity.ResultCode;
import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class CommonExceptionHandler extends Exception{

    private ResultCode resultCode;

    public CommonExceptionHandler(ResultCode resultCode){
        this.resultCode = resultCode;
    }
}
