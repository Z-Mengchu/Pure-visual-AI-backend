package com.mengchu.config;

import com.mengchu.exception.BusinessException;
import com.mengchu.exception.SystemException;
import com.mengchu.pojo.Code;
import com.mengchu.pojo.Message;
import com.mengchu.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ProjectExceptionAdvice {
    //系统异常
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        //记录日志
        log.error(ex.getMessage());
        log.error(Arrays.toString(ex.getStackTrace()));
        //发送消息给运维
        return new Result(ex.getCode(), ex.getMessage(), null);
    }

    //用户异常
    @ExceptionHandler(BusinessException.class)
    public Result doBusinessException(BusinessException ex){
        return new Result(ex.getCode(), ex.getMessage(), null);
    }

    //图片过大异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result doMaxUploadSizeExceededException(MaxUploadSizeExceededException ex){
        log.error(ex.getMessage());
        return new Result(Code.CHANGE_IMAGE_ERR, Message.CHANGE_IMAGE_ERR, null);
    }

    //其他异常
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        //记录日志
        log.error(ex.getMessage());
        log.error(Arrays.toString(ex.getStackTrace()));
        //发送消息给运维
        return new Result(Code.SYSTEM_UNKNOWN_ERR,"系统繁忙，请稍后再试！", null);
    }
}
