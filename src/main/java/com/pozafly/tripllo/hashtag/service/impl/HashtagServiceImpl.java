package com.pozafly.tripllo.hashtag.service.impl;

import com.pozafly.tripllo.common.domain.network.Message;
import com.pozafly.tripllo.hashtag.service.HashtagService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HashtagServiceImpl implements HashtagService {

    @Override
    public ResponseEntity<Message> readBoardByHashtag(String name) {
        return null;
    }
}
