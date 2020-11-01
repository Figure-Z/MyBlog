package com.zsq.myblog.blog.admin.dao;

import com.zsq.myblog.blog.admin.entity.Blog;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.utils.Search;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TypeDao {

    @Insert("insert into z_type (name) values (#{name})")
    int saveType(Type type);

    @Select("select * from z_type where id=#{id}")
    Type getType(Long id);

    @Select("select * from z_type")
    List<Type> getAllType();

    //select t.id tid,t.name, b.id bid,b.title,b.type_id from z_type t,z_blog b where t.id = b.type_id
    @Select("select t.id tid,t.name from z_type t")
    @Results(value = {
            @Result(column = "tid",property = "id"),
            @Result(column = "tid",property = "blogs",javaType = List.class,
            many = @Many(select = "com.zsq.myblog.blog.admin.dao.TypeDao.getNumByType"))
    })
    List<Type>getAllTypeAndBlog();

    @Select("select * from z_type where name=#{name}")
    Type getTypeByName(String name);

    @Select("select * from z_blog b where b.type_id = #{id}")
    int getNumByType(Long id);

    @Update("update z_type set name=#{name} where id = #{id}")
    int updateType(Type type);

    @Delete("delete from z_type where id=#{id}")
    void deleteType(Long id);
}
