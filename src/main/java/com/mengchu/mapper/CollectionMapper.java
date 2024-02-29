package com.mengchu.mapper;

import com.mengchu.pojo.Collection;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface CollectionMapper {
    @Insert("insert into tbl_collection values (#{uid}, #{pid})")
    int save(Integer uid, Integer pid);
    @Select("select * from tbl_collection where uid = #{uid} and pid = #{pid}")
    Collection selectByUidAndPid(Integer uid, Integer pid);
}
