package com.zsq.myblog.blog.admin.dao;

import com.zsq.myblog.blog.admin.entity.Blog;
import com.zsq.myblog.blog.admin.entity.Type;
import com.zsq.myblog.showVo.*;
import com.zsq.myblog.utils.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.ws.BindingType;
import java.util.List;

@Mapper
public interface BlogDao {

    @Select("select b.id,b.title,b.update_time,b.recommend,b.published,b.type_id,t.id as tid,t.name from z_blog b left join"
            + " z_type t on b.type_id = t.id order by b.update_time desc")
    @Results(id = "blogType", value={
            @Result(property = "type.id",column = "tid"),
            @Result(property = "type.name",column = "name"),
    })
    List<A_blogs> getAllBlog();

    @Select("select * from z_blog where id=#{id}")
    Blog getBlogById(long id);

    @Insert("insert into z_blog"
            +"(id,title, content, first_picture, flag, views,description,"
            +"comment_count, appreciation, share_statement, commentabled, published, recommend,"
            +"type_id, user_id)"
            +"values"
            +"(#{id},#{title},#{content},#{firstPicture},#{flag},#{views},#{description},"
            +"#{commentCount},#{appreciation},#{shareStatement},#{commentabled},#{published},#{recommend},"
            +"#{typeId},#{userId})")
    int saveBlog(Blog blog);

    @Update("update z_blog set" +
            " flag=#{flag},title=#{title},content=#{content}," +
            " type_id=#{typeId},first_picture=#{firstPicture},description=#{description}," +
            " recommend=#{recommend},published=#{published},share_statement=#{shareStatement},appreciation=#{appreciation},commentabled=#{commentabled}" +
            " where id=#{id}")
    int updateBlog(ShowBlog showBlog);

    @Delete("delete from z_blog where id=#{id}")
    void deleteBlog(Long id);


//    @Select("<script>"
//            +"select z.id,z.title,z.update_time,z.recommend,z.type_id,t.id,t.name from z_blog z, z_type t"
//            +"<where>"
//            +"<if test='1==1'>"
//            +"z.type_id = t.id"
//            +"</if>"
//            +"<if test='typeId != null'>"
//            +" and z.type_id = ${typeId}"
//            +"</if>"
//            +"<if test='title != \" \" and title != null '>"
//            +" and z.title like '%${title}%'"
//            +"</if>"
//            +"</where>"
//            +"</script>")
//    List<Blog> searchByTitleOrType(BlogSearch blogSearch);

    /**
     * 首页博客页持久层
     * @return
     */

    @Select("select "
            +"b.id,b.title,b.first_picture, b.views, b.comment_count, b.description, b.update_time,"
            + " t.name,"
            + " u.nickname, u.avatar"
            + " from z_blog b , z_type t, z_user u"
            + " where b.type_id = t.id and u.id = b.user_id order by b.update_time desc")
    @Results(value = {
            @Result(column = "name",property = "typeName")
    })
    List<F_blogs> getFirstPageBlogs();


    @Select("select b.id, b.title, b.first_picture, b.views, b.comment_count, b.update_time, b.description, t.name, u.nickname, u.avatar "
            + " from z_blog b, z_type t, z_user u"
            + " where b.type_id = t.id and u.id = b.user_id "
            + " and b.title like concat('%',#{query},'%')")
    List<F_blogs> getSearchBlog(String query);

    @Select("select"
            + " b.id bid, b.first_picture, b.flag, b.title, b.content, b.views, b.comment_count, b.update_time, b.commentabled, b.share_statement, b.appreciation,"
            + " u.nickname,u.avatar,"
            + " t.name"
            + " from z_blog b, z_user u, z_type t"
            + " where b.user_id = u.id and b.type_id = t.id and b.id = #{id}")
    @Results(value = {
            @Result(column = "bid",property = "id"),
            @Result(column = "name",property = "typeName")
    })
    D_blog getDetailedBlog(Long id);

    @Update("update z_blog b set b.views = b.views+1 where b.id = #{id}")
    void updateView(Long id);

    @Select("select b.id, b.title, b.first_picture, b.views, b.update_time, b.description,"
            + " t.name,"
            + " u.nickname, u.avatar"
            + " from z_blog b, z_type t, z_user u"
            + " where b.type_id = t.id and u.id = b.user_id and b.type_id = #{typeId} order by b.update_time desc")
    @Results(value = {
            @Result(column = "name",property = "typeName")
    })
    List<F_blogs> getByTypeId(Long typeId);


    //根据博客id查询出评论数量
    @Update("update z_blog b set b.comment_count = " +
            "(select count(*) from z_comment c where c.blog_id = #{id} and b.id)" +
            "where b.id = #{id}")
    int getCommentCountById(Long id);

    /**
     * 下面为博客信息统计
     * @return
     */
    @Select("select count(*) from z_blog")
    Integer getBlogTotal();

    @Select("select sum(views) from z_blog")
    Integer getBlogViewTotal();

}
