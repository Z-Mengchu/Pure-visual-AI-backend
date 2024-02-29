package com.mengchu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;//用户id
    private String username;//用户名
    private String password;//密码
    private String image;//头像存放的url
    private String nickname;//昵称
    private String description;//简介
    private List<Work> collection;//收藏

}
