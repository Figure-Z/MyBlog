package com.zsq.myblog.blog.admin.service.impl;

import com.zsq.myblog.blog.admin.dao.CommentDao;
import com.zsq.myblog.blog.admin.entity.Comment;
import com.zsq.myblog.blog.admin.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService
{

    @Autowired
    private CommentDao commentDao;

    //存放子类评论
    private List<Comment> commentTemp = new ArrayList<>();

    @Override
    @Transactional
    public int insertComment(Comment comment) {
        int comments = commentDao.insertComment(comment);
        return comments;
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        commentDao.deleteComment(id);
    }

    //获取评论列表
    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {

        //通过博客id找出最上层评论
        List<Comment> comments = commentDao.findByBlogIdAndParentId(blogId,Long.parseLong("-1"));

        for (Comment comment:comments) {
            Long id = comment.getId(); //获取最上层id
            String nicknameTemp = comment.getNickname();
            //获取子评论(id = 最上层评论id)
            List<Comment> childComments = commentDao.findByBlogIdParentId(blogId,id);
            //处理子评论
            combineChildren(blogId, childComments, nicknameTemp);
            comment.setReplyComments(commentTemp);
            commentTemp = new ArrayList<>();
        }

        return comments;
    }

    private void combineChildren(Long blogId, List<Comment> childComments, String nicknameTemp) {
        //判断是否有一级子评论
        if(childComments.size() > 0){
            //循环找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(nicknameTemp);
                commentTemp.add(childComment);
                Long childId = childComment.getId();
                //查询出子二级评论(和查询一级一样)
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(Long blogId, Long childId, String nicknameTemp) {
        //根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentDao.findByBlogIdAndReplayId(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(nicknameTemp);
                Long replayId = replayComment.getId();
                commentTemp.add(replayComment);
                recursively(blogId,replayId,parentNickname);//递归查询
            }
        }
    }

}
