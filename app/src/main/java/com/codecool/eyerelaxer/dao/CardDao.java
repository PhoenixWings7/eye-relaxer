package com.codecool.eyerelaxer.dao;

import androidx.annotation.Nullable;

import com.codecool.eyerelaxer.model.Card;
import com.codecool.eyerelaxer.model.ScheduleEntry;

import java.util.ArrayList;

public interface CardDao {
    ArrayList<Card> getCards();
    @Nullable Card getCardByIndex(int index);
    void addCard(Card card);
    void deleteCard(int index);
    void deleteCard(Card card);
    void addScheduleToCard(int cardIndex, ScheduleEntry scheduleEntry);
    int getCardsCount();
}
