package com.pozafly.tripllo.pushMessage.dao;

import com.pozafly.tripllo.pushMessage.model.PushMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PushMessageDao {
    public List<PushMessage> readPushMessage(String to);
    public void createPushMessage(Map<String, Object> pushMessageInfo);
    public void updatePushMessage(Map<String, Object> pushMessageInfo);
    public void deletePushMessage(Long id);
}
