package com.zsq.myblog.blog.admin.dao;

import com.zsq.myblog.blog.admin.entity.Comment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 评论只需要查询，新增，和删除
 */

@Mapper
public interface CommentDao {

    @Select("select * from z_comment where blog_id = #{blogId} and parent_comment_id = #{parentId} order by create_time desc")
    List<Comment> findByBlogIdAndParentId(Long blogId,Long parentId);

    //一级回复(id为最上级评论id)
    @Select("select * from z_comment where blog_id = #{blogId} and parent_comment_id = #{id} order by create_time desc")
    List<Comment> findByBlogIdParentId(Long blogId, Long id);

    //二级回复(childId是一级回复的id)
    @Select("select * from z_comment where blog_id = #{blogId} and parent_comment_id = #{childId} order by create_time desc")
    List<Comment> findByBlogIdAndReplayId(Long blogId, Long childId);

    @Insert("insert into z_comment "
            + " (nickname,email,content,avatar,blog_id,parent_comment_id,admin_comment)"
            + " values (#{nickname}, #{email}, #{content}, #{avatar}, #{blogId}, #{parentCommentId}, #{adminComment})")
    int insertComment(Comment comment);

    @Delete("delete from z_comment where id = {id}")
    void deleteComment(Long id);
}
