package com.example.pro.fra.configure;

import com.alibaba.fastjson.JSON;
import com.example.pro.fra.bean.RetMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class AuthCheckInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(AuthCheckInterceptor.class);


    /**
     * 白名单
     */
   private static final String[] IgnoreUrls = new String[] {
            "/example"
   };


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1、忽略验证的URL
        String url = request.getRequestURI();
        for(String ignoreUrl : IgnoreUrls){
            if(url.matches(ignoreUrl)){
                return true;
            }
        }

        // 2、查询验证注解 PreFlightHandler
        if (!(handler instanceof HandlerMethod)) {
            logger.info("handler instanceof " + handler.getClass());
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        return true;
    }



    private void output(RetMsg result, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

}
