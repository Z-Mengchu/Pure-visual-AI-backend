package com.mengchu.controller;

import java.lang.annotation.*;

/**
 * 自定义注解防止重复提交
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface noRepeatSubmit {
}
