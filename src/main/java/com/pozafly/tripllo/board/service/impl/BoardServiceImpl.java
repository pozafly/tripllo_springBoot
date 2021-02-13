package com.pozafly.tripllo.board.service.impl;

import com.google.gson.Gson;
import com.pozafly.tripllo.board.dao.BoardDao;
import com.pozafly.tripllo.board.model.Board;
import com.pozafly.tripllo.board.model.responseBoardDetail.BoardResultMap;
import com.pozafly.tripllo.board.service.BoardService;
import com.pozafly.tripllo.boardHasHashtag.dao.BoardHasHashtagDao;
import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.common.domain.network.ResponseMessage;
import com.pozafly.tripllo.common.domain.network.StatusEnum;
import com.pozafly.tripllo.hashtag.dao.HashtagDao;
import com.pozafly.tripllo.hashtag.model.Hashtag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.*;

@Service
public class BoardServiceImpl implements BoardService {

    Message message = new Message();
    HttpHeaders headers = new HttpHeaders();

    @Autowired
    private BoardDao boardDao;
    @Autowired
    private HashtagDao hashtagDao;
    @Autowired
    private BoardHasHashtagDao boardHasHashtagDao;

    @Override
    public ResponseEntity<Message> readBoardOne(Long boardId) {
        Board item = boardDao.readBoardOne(boardId);

        if(!ObjectUtils.isEmpty(item)) {

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(item);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Message> readPersonalBoardList(Map<String, String> boardInfo) {
        List<Board> board = boardDao.readPersonalBoardList(boardInfo);
        if(!ObjectUtils.isEmpty(board)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(board);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(null);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> rerenderBoard(Map<String, Object> boardInfo) {
        List<Board> board = boardDao.rerenderBoard(boardInfo);
        if(!ObjectUtils.isEmpty(board)) {

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(board);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(null);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> readRecentBoardList(Map<String, Object> recentInfo) {

        List<Board> recentBoard = boardDao.readRecentBoards(recentInfo);
        if(!ObjectUtils.isEmpty(recentBoard)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(recentBoard);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(null);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> readInvitedBoardList(Map<String, Object> invitedInfo) {

        List<Board> invitedBoard = boardDao.readInvitedBoards(invitedInfo);
        if(!ObjectUtils.isEmpty(invitedBoard)) {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(invitedBoard);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD);
            message.setData(null);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<Message> readBoardDetail(Long boardId) {
        BoardResultMap item = boardDao.readBoardDetail(boardId);
System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
System.out.println(item);
        System.out.println(item.toString());
        if(!ObjectUtils.isEmpty(item)) {

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.READ_BOARD_DETAIL);
            message.setData(item);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Message> createBoard(Map<String, Object> boardInfo) {
        if(!StringUtils.isEmpty(boardInfo.get("title"))) {
            boardDao.createBoard(boardInfo);
            Long boardId = (Long) boardInfo.get("id");
            boardInfo.put("boardId", boardId);

            String hash = (String)boardInfo.get("hashtag");
            Gson gson = new Gson();
            String[] array = gson.fromJson(hash, String[].class);
            if(!StringUtils.isEmpty(array)) {
                List<String> hashtagList = Arrays.asList(array);

                for(String hashtag : hashtagList) {
                    Long hashtagId = null;

                    // 해시태그가 없다면 새로 만들어준다.
                    Hashtag tag = hashtagDao.readHashTag(hashtag);

                    if(ObjectUtils.isEmpty(tag)) {
                        Map<String, Object> hashtagInfo = new HashMap<>();
                        hashtagInfo.put("name", hashtag);
                        hashtagInfo.put("id", null);

                        hashtagDao.createHashtag(hashtagInfo);

                        hashtagId = (Long)hashtagInfo.get("id");
                    } else {
                        hashtagId = tag.getId();
                    }

                    // 무조건 board_has_hashtag에 insert 함.
                    Map<String, Long> info = new HashMap<>();
                    info.put("boardId", (Long)boardInfo.get("id"));
                    info.put("hashtagId", hashtagId);
                    boardHasHashtagDao.createBoardHasHashtag(info);
                }
            }

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.CREATED_BOARD);
            message.setData(boardInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } else {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Message> updateBoard(Map<String, Object> boardInfo) {
        try{
            String hash = (String)boardInfo.get("hashtag");

            Gson gson = new Gson();
            String[] newArray = gson.fromJson(hash, String[].class);

            if(!StringUtils.isEmpty(newArray)) {
                Board board = boardDao.readBoardOne((Long)boardInfo.get("boardId"));
                String[] preArray = gson.fromJson(board.getHashtag(), String[].class);

                // 기존의 hashtag array가 존재해야 진행 가능.
                if(!ObjectUtils.isEmpty(preArray)) {
                    List<String> preHashList = new ArrayList<>(Arrays.asList(preArray));
                    List<String> newHashList = new ArrayList<>(Arrays.asList(newArray));

                    // 1. 해시태그가 기존에 있던 것에서 삭제 되었다.
                    if(preHashList.size() > newHashList.size()) {
                        // https://www.daleseo.com/how-to-remove-from-list-in-java/
                        newHashList.forEach(el -> {
                            boolean a = preHashList.removeIf(pre -> pre.equals(el));
                        });

                        Hashtag hashtag = hashtagDao.readHashTag(preHashList.get(0));

                        Map<String, Long> map = new HashMap<>();
                        map.put("boardId", (Long)boardInfo.get("boardId"));
                        map.put("hashtagId", hashtag.getId());

                        boardHasHashtagDao.deleteBoardHasHashtag(map);
                    }
                    // 2. 해시태그가 기존에 있던 것에서 추가 되었다.
                    else if (preHashList.size() < newHashList.size()) {
                        preHashList.forEach(el -> {
                            newHashList.removeIf(list -> list.equals(el));
                        });
                        Long hashtagId = null;

                        Hashtag hashtag = hashtagDao.readHashTag(newHashList.get(0));
                        // 해시태그가 없다면 새로 만들어준다.
                        if(ObjectUtils.isEmpty(hashtag)) {
                            Map<String, Object> hashtagInfo = new HashMap<>();
                            hashtagInfo.put("name", newHashList.get(0));
                            hashtagInfo.put("id", null);

                            hashtagDao.createHashtag(hashtagInfo);

                            hashtagId = (Long)hashtagInfo.get("id");
                        } else {
                            hashtagId = hashtag.getId();
                        }
                        // 무조건 board_has_hashtag에 insert 함.
                        Map<String, Long> info = new HashMap<>();
                        info.put("boardId", board.getId());
                        info.put("hashtagId", hashtagId);
                        boardHasHashtagDao.createBoardHasHashtag(info);
                    }
                } else {
                    // 기존의 hashtag array가 존재하지 않으니 새로 만들어준다.
                    for(String hashtag : newArray) {
                        Long hashtagId = null;

                        // 해시태그가 없다면 새로 만들어준다.
                        Hashtag tag = hashtagDao.readHashTag(hashtag);

                        if(ObjectUtils.isEmpty(tag)) {
                            Map<String, Object> hashtagInfo = new HashMap<>();
                            hashtagInfo.put("name", hashtag);
                            hashtagInfo.put("id", null);

                            hashtagDao.createHashtag(hashtagInfo);

                            hashtagId = (Long)hashtagInfo.get("id");
                        } else {
                            hashtagId = tag.getId();
                        }

                        // 무조건 board_has_hashtag에 insert 함.
                        Map<String, Long> info = new HashMap<>();
                        info.put("boardId", board.getId());
                        info.put("hashtagId", hashtagId);
                        boardHasHashtagDao.createBoardHasHashtag(info);
                    }
                }

            }

            boardDao.updateBoard(boardInfo);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.UPDATE_BOARD);
            message.setData(boardInfo);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch(Exception e) {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Message> deleteBoard(Long boardId) {
        try {
            boardDao.deleteBoard(boardId);

            Map<String, Long> rtnMap = new HashMap<>();
            rtnMap.put("boardId", boardId);

            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            message.setStatus(StatusEnum.OK);
            message.setMessage(ResponseMessage.DELETE_BOARD);
            message.setData(rtnMap);

            return new ResponseEntity<>(message, headers, HttpStatus.OK);
        } catch(Exception e) {
            message.setData(null);
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(ResponseMessage.NOT_FOUND_BOARD);
            return new ResponseEntity<>(message, headers, HttpStatus.NOT_FOUND);
        }
    }
}
