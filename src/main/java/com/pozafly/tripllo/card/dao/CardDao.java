package com.pozafly.tripllo.card.dao;

import com.pozafly.tripllo.card.model.Card;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardDao {
    public Card readCard(Long cardId);
    public void createCard(Card card);
    public void updateCard(Card card);
    public void deleteCard(Long cardId);
}
