package com.example.pro.fra.aspect;

import com.alibaba.fastjson.JSONObject;
import com.example.pro.fra.bean.RetMsg;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ClassName: RequestAspect
 * @Description: 所有请求做切面
 * @Date: 2019/2/21 13:52
 */
@Order(1)
@Aspect
@Component
public class RequestAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Pointcut("(execution(public * com.example.pro.vo.controller.*.*(..)))")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String reqSeq="";
        try{
            JSONObject json= JSONObject.parseObject(joinPoint.getArgs()[0].toString());
            //请求序列号
            reqSeq=json.getJSONObject("header").getString("reqSeq");
            request.getSession().setAttribute("reqSeq",reqSeq);
        }catch (Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("reqSeq","");
        }
    }

    @AfterReturning(returning = "ret", pointcut = "controllerAspect()")
    public void doAfterReturning(Object ret) {
        if(ret instanceof RetMsg){
            setReqSeq((RetMsg)ret);
        }
    }

    public static RetMsg setReqSeq(RetMsg retMsg){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Object seq=request.getSession().getAttribute("reqSeq");
        if(seq!=null){
            seq=seq.toString();
        }else{
            seq="";
        }
        request.getSession().setAttribute("reqSeq","");
        retMsg.getHeader().setReqSeq(seq.toString());
        return retMsg;

    }
}
