package com.codecool.eyerelaxer.dao;

import androidx.annotation.Nullable;

import com.codecool.eyerelaxer.model.Card;
import com.codecool.eyerelaxer.model.ScheduleEntry;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class CardDaoImplementation implements CardDao {
    private static final String LOGCAT_TAG = "Card Dao Implementation";
    private final ArrayList<Card> cardsArrayList = new ArrayList<>();
    private static final CardDaoImplementation cardDao = new CardDaoImplementation();

    private CardDaoImplementation() {
        createWeekDayCards();
    }

    public static CardDaoImplementation getCardDao() {
        return cardDao;
    }

    private void createWeekDayCards() {
        DayOfWeek[] weekDays = DayOfWeek.values();
        for (int i = 0; i < weekDays.length; i++) {
            cardsArrayList.add(new Card(DayOfWeek.of(i+1).toString()));
        }
    }

    @Override
    public ArrayList<Card> getCards() {
        return cardsArrayList;
    }

    @Override
    public @Nullable
    Card getCardByIndex(int index) throws IndexOutOfBoundsException {
        return cardsArrayList.get(index);
    }

    @Override
    public void addCard(Card card) {
        cardsArrayList.add(card);
    }

    @Override
    public void deleteCard(int index) {
        cardsArrayList.remove(index);
    }

    @Override
    public void deleteCard(Card card) {
        cardsArrayList.remove(card);
    }

    @Override
    public void addScheduleToCard(int cardIndex, ScheduleEntry scheduleEntry)
            throws IndexOutOfBoundsException {
        Card card = cardsArrayList.get(cardIndex);
        card.addScheduleEntry(scheduleEntry);
    }

    @Override
    public int getCardsCount() {
        return cardsArrayList.size();
    }
}
