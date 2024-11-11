package com.mengchu.interceptor;

import com.mengchu.controller.noRepeatSubmit;
import com.mengchu.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 防止重复提交拦截器
 */
@Component
public class RepeatSubmitInterceptor implements HandlerInterceptor {
    static Map<String,Integer> map = new HashMap<>();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(handler);
        System.out.println(handler instanceof HandlerMethod);

        if (handler instanceof HandlerMethod handlerMethod){
            Method method = handlerMethod.getMethod();
            System.out.println(method);
            noRepeatSubmit annotation = method.getAnnotation(noRepeatSubmit.class);
            System.out.println(annotation);
            if (annotation != null){
                if (this.isRepeatSubmit(request)){
                    throw new BusinessException(0, "您已有任务正在处理中，请等待");
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean isRepeatSubmit(HttpServletRequest request) {
        //获取jwt
        String jwt = request.getHeader("token");
        System.out.println(map);
        if (map.containsKey(jwt)){
            return true;
        } else {
            map.put(jwt, 1);
            return false;
        }
    }
}