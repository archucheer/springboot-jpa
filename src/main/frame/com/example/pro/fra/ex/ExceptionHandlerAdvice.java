package com.example.pro.fra.ex;

import com.example.pro.fra.aspect.RequestAspect;
import com.example.pro.fra.bean.RetMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 抛出到controller的未处理异常统一捕获处理
 */
@ControllerAdvice
@ResponseBody
public class ExceptionHandlerAdvice {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /**
     * 处理参数效验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RetMsg handleException(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage(), e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMesssage = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMesssage.append(fieldError.getDefaultMessage()).append(",");
        }
        RetMsg retMsg = RetMsg.newInstance();
        retMsg= RequestAspect.setReqSeq(retMsg);
        retMsg.setRespCodeAndDesc(ResultCode.REQREQUEST_PARAM_MISS, errorMesssage.toString());
        return retMsg;
    }

    /**
     * 处理参数效验异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public RetMsg handleException(IllegalArgumentException e) {
        LOGGER.error(e.getMessage(), e);
        RetMsg retMsg = RetMsg.newInstance();
        retMsg=RequestAspect.setReqSeq(retMsg);
        retMsg.setRespCodeAndDesc(ResultCode.REQREQUEST_PARAM_ERROR,  e.getMessage());
        return retMsg;
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ResultException.class)
    public RetMsg handleResultException(ResultException resultException) {
        LOGGER.error(resultException.getExceptionMessage(), resultException);
        RetMsg retMsg = RetMsg.newInstance();
        retMsg = RequestAspect.setReqSeq(retMsg);
        retMsg.setException(resultException);
        return retMsg;
    }

    @ExceptionHandler(Exception.class)
    public RetMsg handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        RetMsg retMsg = RetMsg.newInstance();
        retMsg=RequestAspect.setReqSeq(retMsg);
        retMsg.setRespCodeAndDesc(ResultCode.SYSTEM_ERROR, "系统发生异常：[联系开发人员排查]");
        retMsg.putContent("msg", e.getMessage());
        return retMsg;
    }

}
