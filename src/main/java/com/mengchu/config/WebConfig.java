package com.mengchu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private HandlerInterceptor interceptor;

    //设置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /*List<String> patterns = new ArrayList<>();
        Collections.addAll(patterns, "/users/**", "/works/**", "/upload", "/likes/**", "/collect");*/
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**");
    }
    /*//添加静态资源访问目录
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //访问路径
        registry.addResourceHandler("/images/**")
                //文件资源目录
                .addResourceLocations("file:/" + imageUrl);
    }*/

    //设置跨域请求
    /*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 所有的当前站点的请求地址，都支持跨域访问。
                .allowedOrigins("*")  // 所有的外部域都可以跨域访问。如果是localhost形式的域名,需要设置为"localhost"
                .allowCredentials(true)  // 是否支持跨域用户凭证
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")  // 当前站点支持的跨域请求类型是什么
                .maxAge(3600);  // 预检请求的有效期，单位为秒。
    }*/
}
