package com.zsq.myblog.blog.admin.service;

import com.zsq.myblog.blog.admin.entity.Comment;

import java.util.List;

public interface CommentService {

    int insertComment(Comment comment);

    void deleteComment(Long id);

    List<Comment> listCommentByBlogId(Long blogId);

}
