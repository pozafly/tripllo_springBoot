package com.pozafly.tripllo.comment.dao;

import com.pozafly.tripllo.comment.model.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentDao {
    public List<Comment> readComment(Long cardId);
    public void createComment(Map<String, Object> commentInfo);
    public void updateComment(Map<String, Object> commentInfo);
    public void deleteComment(Long commentId);
}
