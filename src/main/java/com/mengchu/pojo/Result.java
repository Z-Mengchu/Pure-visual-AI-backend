package com.mengchu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;//状态码
    private String msg;//提示信息
    private Object data;//返回数据

    public static Result success(Object data) {
        return new Result(1,"success", data);
    }
    public static Result success(){
        return new Result(1, "success",null);
    }
    public static Result error(){
        return new Result(0, "error",null);
    }
}
