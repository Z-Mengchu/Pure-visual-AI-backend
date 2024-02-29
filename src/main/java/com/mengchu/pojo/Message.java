package com.mengchu.pojo;

public class Message {
    //用户管理提示message
    public static final String USER_SAVE_OK = "注册成功";
    public static final String USER_SAVE_ERR = "用户名已存在或密码为空";
    public static final String USER_DELETE_OK = "注销成功";
    public static final String USER_DELETE_ERR = "该用户不存在";
    public static final String USER_UPDATE_OK = "编辑成功";
    public static final String USER_UPDATE_ERR = "该用户名已存在";
    public static final String USER_SELECT_OK = "查询成功";
    public static final String USER_SELECT_ERR = "查询失败";
    public static final String CHANGE_IMAGE_OK = "上传成功";
    public static final String CHANGE_IMAGE_ERR = "图片过大";

    //登陆相关message
    public static final String LOGIN_OK = "登陆成功";
    public static final String LOGIN_ERR = "用户名或密码错误";
    public static final String NOT_LOGIN = "NOT_LOGIN";
    public static final String LOGIN_EXPIRED = "登陆已过期";
    public static final String JWT_ERR = "别乱改我发的JWT！";

    //作品相关message
    public static final String WORK_SELECT_OK = "查询成功";
    public static final String WORK_SELECT_ERR = "查询失败";
    public static final String WORK_SAVE_OK = "发布成功";
    public static final String WORK_SAVE_ERR = "该作品已存在";
    public static final String WORK_MODELING_OK = "提交成功";
    public static final String WORK_MODELING_ERR = "提交失败";
    public static final String GET_MODEL_OK = "任务已完成";
    public static final String GET_MODEL_ERR = "模型正在生成中……";

}
