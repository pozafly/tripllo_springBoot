package com.pozafly.tripllo.hashtag.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

public interface HashtagService {

    public ResponseEntity<Message> readBoardByHashtag(String name);

}
