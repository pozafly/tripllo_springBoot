package com.pozafly.tripllo.hashtag.service;

import com.pozafly.tripllo.common.domain.network.Message;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface HashtagService {

    public ResponseEntity<Message> readBoardByHashtag(Map<String, String> info);

}
