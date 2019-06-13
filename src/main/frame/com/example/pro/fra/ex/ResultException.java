package com.example.pro.fra.ex;


/**
 * 结果异常，会被 ExceptionHandler 捕捉并返回
 */
public class ResultException extends RuntimeException {
    private ResultCode resultCode;
    private String exceptionMessage;
    private String errorMsg;

    public ResultException(ResultCode resultCode, String extraMsg) {
        super(resultCode.getDesc());
        this.exceptionMessage = extraMsg;
        this.errorMsg=extraMsg;
        this.resultCode = resultCode;
    }

    public ResultException(ResultCode resultCode) {
        super(resultCode.getDesc());
        this.exceptionMessage = resultCode.getDesc();
        this.errorMsg="";
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public String getErrorMsg(){
        return  errorMsg;
    }
}
