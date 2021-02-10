package com.pozafly.tripllo.hashtag.dao;

import com.pozafly.tripllo.hashtag.model.Hashtag;
import com.pozafly.tripllo.hashtag.model.response.ResponseHashtagByBoard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HashtagDao {
    public Hashtag readHashTag(String name);
    public List<ResponseHashtagByBoard> readBoardByHashtag(Map<String, String> map);
    public Long createHashtag(Map<String, Object> hashInfo);
    public void deleteHashtag(Long id);
}
