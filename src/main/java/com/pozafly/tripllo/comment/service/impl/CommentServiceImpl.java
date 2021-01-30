package com.pozafly.tripllo.comment.service.impl;

import com.pozafly.tripllo.comment.dao.CommentDao;
import com.pozafly.tripllo.comment.model.Comment;
import com.pozafly.tripllo.comment.service.CommentService;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private CommentDao commentDao;

    @Override
    public ResponseEntity<Message> createComment(Map<String, Object> commentInfo) {
        if(!StringUtils.isEmpty(commentInfo.get("cardId")) || !StringUtils.isEmpty(commentInfo.get("userId"))) {
            commentDao.createComment(commentInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_COMMENT);
            message.setData(commentInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> readComment(Long cardId) {
        List<Comment> commentList = commentDao.readComment(cardId);

        if (!ObjectUtils.isEmpty(commentList)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_COMMENT);
            message.setData(commentList);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setData(null);
            message.setStatus(StatusEnum.NOT_FOUND);
            message.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> updateComment(Map<String, Object> commentInfo) {
        if(!StringUtils.isEmpty(commentInfo.get("commentId"))) {
            commentDao.updateComment(commentInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_COMMENT);
            message.setData(commentInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> deleteComment(Long commentId, String userId) {
        try{
            int commentGroupCount = commentDao.countCommentGroupByCommentId(commentId);
            // 코멘트 그룹이 2개 이상이면, '삭제된 메세지 입니다' 처리
            if (commentGroupCount > 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("userId", userId);
                map.put("commentId", commentId);
                map.put("comment", "삭제된 메세지 입니다.");
                map.put("deleteYn", "Y");

                commentDao.updateComment(map);
            } else {
                commentDao.deleteComment(commentId);
            }

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("commentId", commentId);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_COMMENT);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch (Exception e) {
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
