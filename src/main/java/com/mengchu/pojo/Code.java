package com.mengchu.pojo;

public class Code {
    //用户相关code
    public static final Integer USER_SAVE_OK = 20011;//添加成功
    public static final Integer USER_SAVE_ERR = 20010;//添加失败
    public static final Integer USER_DELETE_OK = 20021;//删除成功
    public static final Integer USER_DELETE_ERR = 20020;//删除失败
    public static final Integer USER_UPDATE_OK = 20031;//修改成功
    public static final Integer USER_UPDATE_ERR = 20030;//修改失败
    public static final Integer USER_SELECT_OK = 20041;//查询单个成功
    public static final Integer USER_SELECT_ERR = 20040;//查询单个失败
    public static final Integer Login_OK = 20001;//登陆成功
    public static final Integer NOT_LOGIN = 20000;//未登录
    public static final Integer LOGIN_EXPIRED = 202;//登录过期
    public static final Integer LOGIN_ERR = 201;//登陆失败
    public static final Integer JWT_ERR = 400;//jwt校验错误
    public static final Integer CHANGE_IMAGE_OK = 20091;//更换头像成功
    public static final Integer CHANGE_IMAGE_ERR = 20090;//更换头像失败


    //作品相关code
    public static final Integer WORK_SELECT_OK = 20051;//查询所有成功
    public static final Integer WORK_SELECT_ERR = 20050;//查询所有失败
    public static final Integer WORK_SAVE_OK = 20061;//作品发布成功
    public static final Integer WORK_SAVE_ERR = 20060;//作品发布失败
    public static final Integer WORK_MODELING_OK = 20071;//生成模型成功
    public static final Integer WORK_MODELING_ERR = 20070;//生成模型失败
    public static final Integer GET_MODEL_OK = 20081;//获取模型成功
    public static final Integer GET_MODEL_ERR = 20080;//模型未生成



    //异常code
    public static final Integer SYSTEM_ERR = 50001;
    public static final Integer SYSTEM_TIMEOUT_ERR = 50002;
    public static final Integer SYSTEM_UNKNOWN_ERR = 59999;
    public static final Integer BUSINESS_ERR = 60002;
}
