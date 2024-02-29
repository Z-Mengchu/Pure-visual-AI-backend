package com.mengchu.mapper;

import com.mengchu.pojo.Work;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WorkMapper {
    /**
     * 用于查询所有作品
     * @return
     */
    @Select("select * from tbl_work")
    List<Work> selectAll();

    /**
     * 查询数组中包含的作品id，主要用于查询用户的收藏列表
     * @param ids
     * @return
     */
    List<Work> selectByIds(List<Integer> ids);

    /**
     * 用于发布作品
     * @param work
     * @return
     */
    @Insert("insert into tbl_work (url,title,author) values (#{url}, #{title}, #{author})")
    int save(Work work);

    /**
     * 查询该模型是否已经存在
     * @param url：模型存放地址
     * @return
     */
    @Select("select * from tbl_work where url = #{url}")
    Work selectByUrl(String url);

    /**
     * 通过id查找作品
     * @param id
     * @return
     */
    @Select("select * from tbl_work where id = #{id}")
    Work selectById(Integer id);

    /**
     * 修改作品点赞量
     * @param work
     * @return
     */
    @Update("update tbl_work set like_count = #{likeCount} where id = #{id}")
    int updateLikeCount(Work work);
}
