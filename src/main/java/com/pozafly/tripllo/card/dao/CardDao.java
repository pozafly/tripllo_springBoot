package com.pozafly.tripllo.card.dao;

import com.pozafly.tripllo.card.model.Card;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface CardDao {
    public Card readCard(Long cardId);
    public void createCard(Map<String, Object> cardInfo);
    public void updateCard(Map<String, Object> cardInfo);
    public void deleteCard(Long cardId);
}
