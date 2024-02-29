package com.mengchu.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mengchu.pojo.Code;
import com.mengchu.pojo.Message;
import com.mengchu.pojo.Result;
import com.mengchu.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //不是登录页面，进行jwt校验
        //1.获取jwt令牌
        String jwt = request.getHeader("token");
        if (jwt == null) {
            //2.jwt为空，未登录
            Result error = new Result(Code.NOT_LOGIN, Message.NOT_LOGIN, null);
            String jsonString = JSONObject.toJSONString(error);
            response.getWriter().write(jsonString);
            log.info("未登录，NOT_LOGIN");
            return false;
        }
        try {
            //3.1校验成功，放行
            Claims claims = JwtUtil.parseJwt(jwt);
            log.info(claims.toString());
        } catch (SignatureException e) {
            //解析jwt错误，记录日志
            log.error("jwt解析错误，{}", jwt);
            e.printStackTrace();
            //3.2校验失败，返回错误信息
            Result error = new Result(Code.JWT_ERR, Message.JWT_ERR, null);
            String jsonString = JSONObject.toJSONString(error);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonString);
            return false;
        } catch (ExpiredJwtException e){
            //jwt过期，记录日志
            log.error("jwt过期，{}", jwt);
            e.printStackTrace();
            //3.2校验失败，返回错误信息
            Result error = new Result(Code.LOGIN_EXPIRED, Message.LOGIN_EXPIRED, null);
            String jsonString = JSONObject.toJSONString(error);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonString);
            return false;
        }
        log.info("令牌合法，放行");
        return true;
    }
}
