package com.example.pro.fra.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author Miracle
 * 对controller层日志切片处理
 */
@Aspect
@Component
public class LogAspect {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

//    @Pointcut("execution(public * com.hjkj.wechatserver.seal.controller.*.*(..))&& !execution(public * com.hjkj.wechatserver.seal.TokenController.verifyUserToken(..))")
    @Pointcut("execution(public * com.example.pro.vo.controller.*.*(..))")
    public void controllerAspect() {
    }

    @Pointcut("execution(public * java.lang.Throwable.printStackTrace())")
    public void throwableAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        LOGGER.info("========================================");
        LOGGER.info("请求类型 : " + request.getMethod() + "，\t请求URL : " + request.getRequestURL().toString());
        LOGGER.info("调用方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "controllerAspect()")
    public void doAfterReturning(Object ret) {
        if(ret==null){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            ret=attributes.getResponse();
        }
        LOGGER.info("请求返回值 : " + ret);
        LOGGER.info("========================================");
    }

}
