package com.mengchu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private HandlerInterceptor repeatSubmitInterceptor;

    @Qualifier("loginCheckInterceptor")
    @Autowired
    private HandlerInterceptor loginInterceptor;

    //设置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/users/**");

        registry.addInterceptor(repeatSubmitInterceptor)
                .addPathPatterns("/**");
    }

    //设置跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 所有的当前站点的请求地址，都支持跨域访问。
                .allowedOriginPatterns("*") // 所有的外部域都可以跨域访问。
                .allowCredentials(true)  // 是否允许发送Cookie信息
                .allowedMethods("GET", "POST", "DELETE", "PUT")  // 当前站点支持的跨域请求类型是什么
                .maxAge(3600);  // 预检请求的有效期，单位为秒。
    }
}
