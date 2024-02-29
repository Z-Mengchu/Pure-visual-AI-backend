package com.mengchu.mapper;

import com.mengchu.pojo.User;
import com.mengchu.pojo.Work;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 注册新用户
     * @param user：新用户，传递username和password
     */
    @Insert("insert into tbl_user values (null, #{username}, #{password}, #{image}, #{nickname}, #{description})")
    int save(User user);

    /**
     * 注销用户
     * @param id：需要注销的用户的id
     */
    @Delete("delete from tbl_user where id = #{id}")
    int delete(Integer id);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Update("update tbl_user set username = #{username}, image = #{image}, nickname = #{nickname}, " +
            "password = #{password}, description = #{description} where id = #{id}")
    int update(User user);

    /**
     * 修改头像
     * @param id
     * @param image
     * @return
     */
    @Update("update tbl_user set image = #{image} where id = #{id}")
    void updateImage(Integer id, String image);

    /**
     * 查询用户信息
     * @param id：用户id
     */
    @Select("select * from tbl_user where id = #{id}")
    User selectById(Integer id);

    /**
     * 查询用户的收藏列表
     * @param uid：用户id
     * @return : 用户的收藏列表的id
     */
    @Select("select pid from tbl_collection where uid = #{uid}")
    List<Integer> selectCollection(Integer uid);

    /**
     * 通过用户名查找信息
     * @param username：用户名
     * @return
     */
    @Select("select * from tbl_user where username = #{username}")
    User selectByUsername(String username);

    @Select("select * from tbl_user where username = #{username} and password = #{password}")
    User selectByUsernameAndPassword(User user);
}
