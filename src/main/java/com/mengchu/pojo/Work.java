package com.mengchu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    private Integer id;//作品id
    private String url;//模型存放的url
    private Integer likeCount;//点赞量
    private String title;//标题
    private String author;//作者昵称
    public void increase(){
        this.setLikeCount(this.getLikeCount()+1);
    }
}
